package net.diabolo.diabolomod.entity.custom.topaz_golem;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class TopazGolemEntity extends PathfinderMob {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(TopazGolemEntity.class, EntityDataSerializers.INT);

    private int attackAnimationTick;
    private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "speed_variant_boost");
    private static final Identifier JUMP_MODIFIER_ID = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "speed_variant_boost");
    private static final Identifier STEP_MODIFIER_ID = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "speed_variant_boost");

    public TopazGolemEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 25.0)
                .add(Attributes.STEP_HEIGHT, 1.0)
                .add(Attributes.JUMP_STRENGTH, 1.0);
    }

    // 3. L'IA de combat et de marche
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        // Attaque tous les monstres hostiles
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PathfinderMob.class, 5, false, false, (entity, level) -> entity instanceof Enemy));
    }

    // 4. Ta réparation au Topaze
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.is(ModItems.TOPAZ)) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(25.0F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, f1);
                itemstack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
        }
    }

    // 5. Gestion de l'animation d'attaque (Le golem lève les bras)
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        this.attackAnimationTick = 10;
        level.broadcastEntityEvent(this, (byte)4);
        return super.doHurtTarget(level, target);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackAnimationTick = 10;
        } else {
            super.handleEntityEvent(id);
        }
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            this.attackAnimationTick--;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public TopazGolemVariant getVariant() {
        return TopazGolemVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(TopazGolemVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
        this.updateForVariant(variant);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        int variantId = input.getIntOr("Variant", 0);
        this.entityData.set(VARIANT, variantId);

        this.updateForVariant(TopazGolemVariant.byId(variantId));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
        TopazGolemVariant variant = Util.getRandom(TopazGolemVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    private void updateForVariant(TopazGolemVariant variant) {
        AttributeInstance speedAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance stepAttribute = this.getAttribute(Attributes.STEP_HEIGHT);
        AttributeInstance jumpAttribute = this.getAttribute(Attributes.JUMP_STRENGTH);

        if (speedAttribute == null || stepAttribute == null || jumpAttribute == null) return;

        speedAttribute.removeModifier(SPEED_MODIFIER_ID);
        stepAttribute.removeModifier(STEP_MODIFIER_ID);
        jumpAttribute.removeModifier(JUMP_MODIFIER_ID);

        if (variant == TopazGolemVariant.BASIC_SPEED ||
                variant == TopazGolemVariant.BLASTER_SPEED ||
                variant == TopazGolemVariant.MINER_SPEED) {

            AttributeModifier speedBoost = new AttributeModifier(
                    SPEED_MODIFIER_ID,
                    0.5D, // +50% de vitesse
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );

            AttributeModifier stepBoost = new AttributeModifier(
                    STEP_MODIFIER_ID,
                    -0.4D, // 1.0 (base) - 0.4 = 0.6 (Ne monte plus sur les blocs)
                    AttributeModifier.Operation.ADD_VALUE
            );

            AttributeModifier jumpBoost = new AttributeModifier(
                    JUMP_MODIFIER_ID,
                    -1.0D, // -100% de saut. (Multiplie la base par -1 pour la tomber à 0)
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
            speedAttribute.addPermanentModifier(speedBoost);
            stepAttribute.addPermanentModifier(stepBoost);
            jumpAttribute.addPermanentModifier(jumpBoost);
        }
    }

    public enum Crackiness {
        NONE, LOW, MEDIUM, HIGH
    }

    public Crackiness getCrackiness() {
        if (this.isDeadOrDying()) {
            return Crackiness.HIGH;
        }
        float maxHealth = this.getMaxHealth();
        if (maxHealth <= 0.0F) {
            return Crackiness.NONE;
        }
        float currentHealth = this.getHealth();
        // On calcule le ratio et on le bloque strictement entre 0.0 (0%) et 1.0 (100%)
        float ratio = Mth.clamp(currentHealth / maxHealth, 0.0F, 1.0F);
        if (ratio < 0.25F) {
            return Crackiness.HIGH;    // Moins de 25% de vie
        } else if (ratio < 0.50F) {
            return Crackiness.MEDIUM;  // Entre 25% et 50% de vie
        } else if (ratio < 0.75F) {
            return Crackiness.LOW;     // Entre 50% et 75% de vie
        } else {
            return Crackiness.NONE;    // Plus de 75% de vie
        }
    }
}

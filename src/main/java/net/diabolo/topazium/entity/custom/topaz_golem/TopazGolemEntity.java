package net.diabolo.topazium.entity.custom.topaz_golem;

import net.diabolo.topazium.Topazium;
import net.diabolo.topazium.item.ModItems;
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
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import javax.annotation.Nullable;

import static net.diabolo.topazium.entity.custom.topaz_golem.TopazGolemVariant.isSpeedVariant;

public class TopazGolemEntity extends PathfinderMob {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(TopazGolemEntity.class, EntityDataSerializers.INT);
    private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(Topazium.MODID, "speed_variant_boost");
    private static final Identifier JUMP_MODIFIER_ID = Identifier.fromNamespaceAndPath(Topazium.MODID, "jump_variant_nerf");
    private static final Identifier STEP_MODIFIER_ID = Identifier.fromNamespaceAndPath(Topazium.MODID, "step_variant_nerf");
    private int attackAnimationTick;
    private Vec3 lastPhysicalPos = null;
    private int physicalStuckTicks = 0;

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
                .add(Attributes.JUMP_STRENGTH, 0.42);
    }

    // 3. L'IA de combat et de marche
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SpeedGolemMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(
                this, PathfinderMob.class, 5, false, false,
                (entity, level) -> entity instanceof Enemy
        ));
    }

    //réparation au Topaze
    @Override
    protected @NonNull InteractionResult mobInteract(Player player, @NonNull InteractionHand hand) {
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

    // Gestion de l'animation d'attaque (Le golem lève les bras)
    @Override
    public boolean doHurtTarget(ServerLevel level, @NonNull Entity target) {
        this.attackAnimationTick = 10;
        level.broadcastEntityEvent(this, (byte) 4);
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
    public void jumpFromGround() {
        if (isSpeedVariant(this.getVariant())) {
            return; // Aucune impulsion verticale
        }
        super.jumpFromGround();
    }

    @Override
    public void aiStep() {
        if (isSpeedVariant(this.getVariant())) {
            this.setJumping(false);
        }
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            this.attackAnimationTick--;
        }
        if (!this.level().isClientSide() && isSpeedVariant(this.getVariant())) {
            this.handlePhysicalUnstuck();
        }
    }

    private void handlePhysicalUnstuck() {
        // On n'agit que si le golem suit un chemin actif
        if (this.getNavigation().isDone() || this.getNavigation().getPath() == null) {
            this.physicalStuckTicks = 0;
            this.lastPhysicalPos = this.position();
            return;
        }

        Vec3 current = this.position();
        if (this.lastPhysicalPos != null && current.distanceToSqr(this.lastPhysicalPos) < 0.001D) {
            // Quasi immobile (< 0.032 bloc/tick) malgré un chemin valide
            this.physicalStuckTicks++;

            if (this.physicalStuckTicks >= 8) { // 0.4s → réaction rapide
                var path = this.getNavigation().getPath();

                // Si le chemin existe et n'est pas fini
                if (path != null && !path.isDone()) {
                    // On récupère la position visée par le prochain nœud du chemin
                    Vec3 nextNodePos = path.getNextNode().asVec3();

                    // On calcule la direction vers ce nœud
                    Vec3 dirToNextNode = nextNodePos.subtract(this.position());

                    // Si la direction est valide
                    if (dirToNextNode.lengthSqr() > 1.0E-6) {
                        dirToNextNode = dirToNextNode.normalize();

                        // On calcule un vecteur perpendiculaire (90 degrés)
                        // pour "glisser" le long du mur qui bloque
                        Vec3 sideSlip = new Vec3(-dirToNextNode.z, 0, dirToNextNode.x);

                        // On choisit un côté (gauche ou droite)
                        double sign = this.random.nextBoolean() ? 1.0 : -1.0;

                        // On applique la force latérale
                        this.setDeltaMovement(
                                this.getDeltaMovement().x + (sideSlip.x * 0.18 * sign),
                                this.getDeltaMovement().y,
                                this.getDeltaMovement().z + (sideSlip.z * 0.18 * sign)
                        );
                    }
                }

                // Arrêt du chemin : le goal le recalculera proprement depuis la nouvelle position débloquée
                this.getNavigation().stop();
                this.physicalStuckTicks = 0;
            }
        } else {
            this.physicalStuckTicks = 0;
        }
        this.lastPhysicalPos = current;
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
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
    public void setJumping(boolean jumping) {
        if (isSpeedVariant(this.getVariant())) {
            super.setJumping(false);
            return;
        }
        super.setJumping(jumping);
    }

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        int variantId = input.getIntOr("Variant", 0);
        this.entityData.set(VARIANT, variantId);

        this.updateForVariant(TopazGolemVariant.byId(variantId));
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor level, @NonNull DifficultyInstance difficulty,
                                        @NonNull EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
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

        if (isSpeedVariant(variant)) {

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
            return Crackiness.HIGH; // Moins de 25% de vie
        } else if (ratio < 0.50F) {
            return Crackiness.MEDIUM; // Entre 25% et 50% de vie
        } else if (ratio < 0.75F) {
            return Crackiness.LOW; // Entre 50% et 75% de vie
        } else {
            return Crackiness.NONE; // Plus de 75% de vie
        }
    }

    @Override
    protected @NonNull PathNavigation createNavigation(@NonNull Level level) {
        GroundPathNavigation navigation = new GroundPathNavigation(this, level) {
            @Override
            protected @NonNull PathFinder createPathFinder(int maxVisitedNodes) {
                this.nodeEvaluator = new TopazGolemNodeEvaluator();
                this.nodeEvaluator.setCanPassDoors(true);
                return new PathFinder(this.nodeEvaluator, 1000);
            }
        };
        // CRUCIAL : Interdit de passer en diagonale si un bloc gêne
        navigation.setCanWalkOverFences(false);
        return navigation;
    }

    @Override
    public boolean isWithinMeleeAttackRange(@NonNull LivingEntity target) {
        if (!this.getSensing().hasLineOfSight(target)) {
            return false;
        }
        double distanceToTarget = this.distanceToSqr(target);

        // Formule de portée améliorée :
        // La largeur du golem * 2 + la largeur de la cible, PLUS un bonus de portée.
        // Un bonus de 4.0D (qui correspond à +2.0 blocs de portée au carré) permet au golem
        // de frapper un zombie de l'autre côté d'un muret sans se coincer le ventre !
        double reachSqr = (double) (this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + target.getBbWidth()) + 4.0D;

        return distanceToTarget <= reachSqr;
    }

    public enum Crackiness {
        NONE, LOW, MEDIUM, HIGH
    }
}
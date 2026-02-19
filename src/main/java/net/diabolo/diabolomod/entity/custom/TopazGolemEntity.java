package net.diabolo.diabolomod.entity.custom;

import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class TopazGolemEntity extends IronGolem {
    private static final int TOPAZ_HEAL_AMOUNT = 25;

    public TopazGolemEntity(EntityType<? extends IronGolem> p_478838_, Level p_479073_) {
        super(p_478838_, p_479073_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 25.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

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
}

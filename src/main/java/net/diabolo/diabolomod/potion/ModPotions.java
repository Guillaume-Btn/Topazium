package net.diabolo.diabolomod.potion;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, DiaboloMod.MODID);

//    public static final Holder<Potion> COBALT_SOLUTION = POTIONS.register("cobalt_solution",
//            () -> new Potion("cobalt_solution", new MobEffectInstance(MobEffects.NAUSEA, 1200, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}

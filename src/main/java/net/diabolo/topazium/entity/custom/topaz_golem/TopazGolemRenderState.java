package net.diabolo.topazium.entity.custom.topaz_golem;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class TopazGolemRenderState extends LivingEntityRenderState {
    public TopazGolemVariant variant = TopazGolemVariant.BASIC_BASIC;
    public float attackTicksRemaining;
    public TopazGolemEntity.Crackiness crackiness = TopazGolemEntity.Crackiness.NONE;
}

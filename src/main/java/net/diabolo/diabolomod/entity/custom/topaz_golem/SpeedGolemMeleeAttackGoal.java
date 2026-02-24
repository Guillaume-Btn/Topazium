package net.diabolo.diabolomod.entity.custom.topaz_golem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;

import static net.diabolo.diabolomod.entity.custom.topaz_golem.TopazGolemVariant.isSpeedVariant;

public class SpeedGolemMeleeAttackGoal extends MeleeAttackGoal {

    private final TopazGolemEntity golem;
    private Vec3 stuckWindowStart = null;
    private int stuckTicks = 0;

    // En 80 ticks (4s), le golem doit progresser d'au moins 2 blocs (4 = 2²)
    private static final int STUCK_WINDOW = 80;
    private static final double MIN_PROGRESS_SQ = 4.0D;

    public SpeedGolemMeleeAttackGoal(TopazGolemEntity golem, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(golem, speedModifier, followTargetEvenIfNotSeen);
        this.golem = golem;
    }

    @Override
    public void start() {
        super.start();
        resetStuck();
    }

    @Override
    public void tick() {
        super.tick();
        if (!isSpeedVariant(this.golem.getVariant())) return;

        LivingEntity target = this.golem.getTarget();
        if (target == null) { resetStuck(); return; }
        if (this.golem.isWithinMeleeAttackRange(target)) { resetStuck(); return; }

        // Mémoriser la position de début de fenêtre
        if (this.stuckWindowStart == null) {
            this.stuckWindowStart = this.golem.position();
        }
        this.stuckTicks++;

        if (this.stuckTicks >= STUCK_WINDOW) {
            double progress = this.golem.position().distanceToSqr(this.stuckWindowStart);
            if (progress < MIN_PROGRESS_SQ) {
                // Moins de 2 blocs de progression nette en 4s → cible inaccessible
                this.golem.setTarget(null);
            }
            resetStuck(); // Nouvelle fenêtre dans tous les cas
        }
    }

    @Override
    public void stop() {
        super.stop();
        resetStuck();
    }

    private void resetStuck() {
        this.stuckTicks = 0;
        this.stuckWindowStart = null;
    }
}

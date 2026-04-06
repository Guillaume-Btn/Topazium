package net.diabolo.topazium.entity.custom.topaz_golem;

import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.jspecify.annotations.NonNull;

import static net.diabolo.topazium.entity.custom.topaz_golem.TopazGolemVariant.isSpeedVariant;

public class TopazGolemNodeEvaluator extends WalkNodeEvaluator {

    @Override
    protected Node findAcceptedNode(int x, int y, int z, int verticalDeltaLimit,
                                    double nodeFloorLevel, @NonNull Direction direction, @NonNull PathType pathType) {

        Node node = super.findAcceptedNode(x, y, z, verticalDeltaLimit, nodeFloorLevel, direction, pathType);
        if (node == null) return null;
        if (this.mob instanceof TopazGolemEntity golem && isSpeedVariant(golem.getVariant())) {
            double targetFloor = this.getFloorLevel(new BlockPos(x, y, z));

            if (targetFloor > nodeFloorLevel + golem.maxUpStep()) {
                return null;
            }
        }

        return node;
    }
}

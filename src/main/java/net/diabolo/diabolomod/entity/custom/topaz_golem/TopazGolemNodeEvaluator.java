package net.diabolo.diabolomod.entity.custom.topaz_golem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import static net.diabolo.diabolomod.entity.custom.topaz_golem.TopazGolemVariant.isSpeedVariant;

public class TopazGolemNodeEvaluator extends WalkNodeEvaluator {

    @Override
    protected Node findAcceptedNode(int x, int y, int z, int verticalDeltaLimit,
                                    double nodeFloorLevel, Direction direction, PathType pathType) {

        // On laisse Vanilla vérifier les dangers évidents (murs de 2 blocs, lave, etc.)
        Node node = super.findAcceptedNode(x, y, z, verticalDeltaLimit, nodeFloorLevel, direction, pathType);

        // Si Vanilla a déjà refusé
        if (node == null) return null;

        if (this.mob instanceof TopazGolemEntity golem && isSpeedVariant(golem.getVariant())) {

            // Vérification du centre
            double targetFloor = this.getFloorLevel(new BlockPos(x, y, z));
            if (targetFloor > nodeFloorLevel + golem.maxUpStep()) {
                return null;
            }

            // On s'assure qu'aucun bloc sur les bords ne dépasse la hauteur de maxUpStep
            // Cela empêche le chemin de raser les murs de 1 bloc
            double halfWidth = golem.getBbWidth() / 2.0D;

            // Calcul de la zone occupée par le golem sur cette case
            int minX = Mth.floor(x + 0.5D - halfWidth);
            int maxX = Mth.floor(x + 0.5D + halfWidth);
            int minZ = Mth.floor(z + 0.5D - halfWidth);
            int maxZ = Mth.floor(z + 0.5D + halfWidth);

            for (int dx = minX; dx <= maxX; dx++) {
                for (int dz = minZ; dz <= maxZ; dz++) {
                    // On ignore le centre car on vient de le vérifier
                    if (dx == x && dz == z) continue;

                    double sideFloor = this.getFloorLevel(new BlockPos(dx, y, dz));

                    // Si un bloc sur le bord est plus haut que ce que le golem peut enjamber
                    if (sideFloor > nodeFloorLevel + golem.maxUpStep()) {
                        return null; // On rejette ce nœud
                    }
                }
            }
        }

        return node;
    }
}

package net.diabolo.diabolomod.entity.custom.topaz_golem;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class TopazGolemModel extends EntityModel<TopazGolemRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "topaz_golem"), "main");

    private final ModelPart relieroue;
    private final ModelPart body;
    private final ModelPart Head;
    private final ModelPart arm0;
    private final ModelPart arm1;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart wheel1;
    private final ModelPart wheel2;

    public TopazGolemModel(ModelPart root) {
        super(root);
        this.relieroue = root.getChild("relieroue");
        this.body = root.getChild("body");
        this.Head = this.body.getChild("Head");
        this.arm0 = this.body.getChild("arm0");
        this.arm1 = this.body.getChild("arm1");
        this.leg0 = this.body.getChild("leg0");
        this.leg1 = this.body.getChild("leg1");
        this.wheel1 = root.getChild("wheel1");
        this.wheel2 = root.getChild("wheel2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition relieroue = partdefinition.addOrReplaceChild("relieroue", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(38, 23).addBox(15.0F, -2.0F, -1.0F, 1.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(80, 97).addBox(0.0F, -8.0F, 7.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(86, 97).addBox(15.0F, -8.0F, 7.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 0).addBox(0.0F, -10.0F, 5.5F, 16.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 18.0F, -8.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.1429F, -9.5F, -5.6429F, 18.0F, 12.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(58, 7).addBox(-4.6429F, 2.5F, -2.6429F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.1429F, 0.5F, -0.3571F));

        PartDefinition Head = body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(40, 43).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1429F, -7.5F, -1.6429F));

        PartDefinition arm0 = body.addOrReplaceChild("arm0", CubeListBuilder.create().texOffs(0, 43).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1429F, -7.5F, 0.3571F));

        PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(20, 43).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1429F, -7.5F, 0.3571F));

        PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(40, 61).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.1429F, 10.5F, 0.3571F));

        PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(62, 61).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.8571F, 10.5F, 0.3571F));

        PartDefinition wheel1 = partdefinition.addOrReplaceChild("wheel1", CubeListBuilder.create().texOffs(72, 43).addBox(-7.0F, -7.0F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 18).addBox(-7.0F, 6.0F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(84, 80).addBox(-7.0F, 4.0F, -5.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 12).addBox(-7.0F, -5.0F, -5.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 14).addBox(-7.0F, 4.0F, 4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 10).addBox(-7.0F, -5.0F, 4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 58).addBox(-7.0F, 5.0F, -4.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 36).addBox(-7.0F, -6.0F, -4.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 33).addBox(-7.0F, -6.0F, 2.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 39).addBox(-7.0F, 5.0F, 2.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 23).addBox(-7.0F, -2.0F, -7.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 28).addBox(-7.0F, -2.0F, 6.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 74).addBox(-7.0F, -4.0F, 5.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 85).addBox(-7.0F, 2.0F, 5.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 77).addBox(-7.0F, -4.0F, -6.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 71).addBox(-7.0F, 2.0F, -6.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(92, 94).addBox(-2.0F, 5.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 95).addBox(-2.0F, -6.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 82).addBox(-2.0F, -1.0F, 5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(92, 97).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 90).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 79).addBox(-1.0F, 5.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(94, 21).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 98).addBox(-1.0F, 1.0F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 98).addBox(-1.0F, 1.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 98).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 98).addBox(-1.0F, -6.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 98).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 0).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 92).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 92).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 90).addBox(-7.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(84, 90).addBox(1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -8.0F));

        PartDefinition wheel2 = partdefinition.addOrReplaceChild("wheel2", CubeListBuilder.create().texOffs(72, 48).addBox(-7.0F, -7.0F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(72, 53).addBox(-7.0F, 6.0F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(88, 16).addBox(-7.0F, 4.0F, -5.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 88).addBox(-7.0F, -5.0F, -5.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 88).addBox(-7.0F, 4.0F, 4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 90).addBox(-7.0F, -5.0F, 4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 79).addBox(-7.0F, 5.0F, -4.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 82).addBox(-7.0F, -6.0F, -4.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 82).addBox(-7.0F, -6.0F, 2.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 82).addBox(-7.0F, 5.0F, 2.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(84, 61).addBox(-7.0F, -2.0F, -7.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 66).addBox(-7.0F, -2.0F, 6.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 85).addBox(-7.0F, -4.0F, 5.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 85).addBox(-7.0F, 2.0F, 5.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 88).addBox(-7.0F, -4.0F, -6.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 7).addBox(-7.0F, 2.0F, -6.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(94, 18).addBox(-2.0F, 5.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(80, 94).addBox(-2.0F, -6.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 98).addBox(-2.0F, -1.0F, 5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 98).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 91).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 2).addBox(-1.0F, 5.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 4).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 21).addBox(-1.0F, 1.0F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 100).addBox(-1.0F, 1.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 100).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 100).addBox(-1.0F, -6.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 100).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 100).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 92).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 94).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 85).addBox(-7.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 91).addBox(1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(TopazGolemRenderState state) {
        super.setupAnim(state);
        float attackTicks = state.attackTicksRemaining;
        float walkSpeed = state.walkAnimationSpeed;
        float walkPos = state.walkAnimationPos;


        // 1. Animation des bras (Attaque ou Marche)
        if (attackTicks > 0.0F) {
            // Mouvement brusque quand il frappe
            this.arm0.xRot = -2.0F + 1.5F * Mth.triangleWave(attackTicks, 10.0F);
            this.arm1.xRot = -2.0F + 1.5F * Mth.triangleWave(attackTicks, 10.0F);
        } else {
            // S'il offre une fleur
            // Balancement normal des bras en marchant
            if (isSpeedVariant(state.variant)) {
                // Multiplier par walkSpeed permet d'avoir 0 (bras baissés) à l'arrêt,
                // et 1.0F (bras en arrière) quand il roule à pleine vitesse !
                this.arm0.xRot = 0.5F * walkSpeed;
                this.arm1.xRot = 0.5F * walkSpeed;
            } else {
                this.arm0.xRot = (-0.2F + 1.5F * Mth.triangleWave(walkPos, 13.0F)) * walkSpeed;
                this.arm1.xRot = (-0.2F - 1.5F * Mth.triangleWave(walkPos, 13.0F)) * walkSpeed;
            }
        }

        // 2. Animation de la tête (Regarder autour)
        this.Head.yRot = state.yRot * ((float) Math.PI / 180F);
        this.Head.xRot = state.xRot * ((float) Math.PI / 180F);

        // 3. Animation des jambes (Marche)
        if (isSpeedVariant(state.variant)) {
            this.leg0.visible = false;
            this.leg1.visible = false;
            this.wheel1.visible = true;
            this.wheel2.visible = true;
            this.relieroue.visible = true;
            float rotationSpeed = 0.5F; // Ajuste ce chiffre pour tourner plus ou moins vite
            this.wheel1.xRot = state.walkAnimationPos * rotationSpeed;
            this.wheel2.xRot = state.walkAnimationPos * rotationSpeed;
        } else {
            this.leg0.visible = true;
            this.leg1.visible = true;
            this.wheel1.visible = false;
            this.wheel2.visible = false;
            this.relieroue.visible = false;
            this.leg0.xRot = -1.5F * Mth.triangleWave(walkPos, 13.0F) * walkSpeed;
            this.leg1.xRot = 1.5F * Mth.triangleWave(walkPos, 13.0F) * walkSpeed;
            this.leg0.yRot = 0.0F;
            this.leg1.yRot = 0.0F;
        }
    }

    public ModelPart getFlowerHoldingArm() {
        return this.arm0;
    }

    private boolean isSpeedVariant(TopazGolemVariant v) {
        return (v == TopazGolemVariant.BASIC_SPEED ||
                v == TopazGolemVariant.BLASTER_SPEED ||
                v == TopazGolemVariant.MINER_SPEED);
    }

}

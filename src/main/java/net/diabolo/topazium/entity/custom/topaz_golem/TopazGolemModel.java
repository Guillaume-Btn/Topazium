package net.diabolo.topazium.entity.custom.topaz_golem;

import net.diabolo.topazium.Topazium;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import static net.diabolo.topazium.entity.custom.topaz_golem.TopazGolemVariant.isSpeedVariant;

public class TopazGolemModel extends EntityModel<TopazGolemRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(Topazium.MODID, "topaz_golem"), "main");

    private final ModelPart body;
    private final ModelPart Head;
    private final ModelPart arm0;
    private final ModelPart arm1;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart wheel1;
    private final ModelPart exterieur;
    private final ModelPart wheel2;
    private final ModelPart exterieur2;
    private final ModelPart relieroue;

    public TopazGolemModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.Head = this.body.getChild("Head");
        this.arm0 = this.body.getChild("arm0");
        this.arm1 = this.body.getChild("arm1");
        this.leg0 = this.body.getChild("leg0");
        this.leg1 = this.body.getChild("leg1");
        this.wheel1 = root.getChild("wheel1");
        this.exterieur = this.wheel1.getChild("exterieur");
        this.wheel2 = root.getChild("wheel2");
        this.exterieur2 = this.wheel2.getChild("exterieur2");
        this.relieroue = root.getChild("relieroue");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.1429F, -9.5F, -5.6429F, 18.0F, 12.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(86, 62).addBox(-4.6429F, 2.5F, -2.6429F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.1429F, 0.5F, -0.3571F));

        PartDefinition Head = body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(84, 74).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1429F, -7.5F, -1.6429F));

        PartDefinition arm0 = body.addOrReplaceChild("arm0", CubeListBuilder.create().texOffs(0, 56).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1429F, -7.5F, 0.3571F));

        PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(20, 56).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1429F, -7.5F, 0.3571F));

        PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(86, 41).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.1429F, 10.5F, 0.3571F));

        PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 92).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.8571F, 10.5F, 0.3571F));

        PartDefinition wheel1 = partdefinition.addOrReplaceChild("wheel1", CubeListBuilder.create().texOffs(108, 59).addBox(-2.0F, 3.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 112).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 112).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(112, 112).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 56).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 72).addBox(-1.0F, 3.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 115).addBox(-1.0F, 3.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(58, 115).addBox(-1.0F, 1.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 115).addBox(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 115).addBox(-1.0F, -2.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 115).addBox(-1.0F, -4.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(82, 115).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 115).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 113).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 113).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(108, 51).addBox(-7.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(108, 55).addBox(1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -8.0F));

        PartDefinition exterieur = wheel1.addOrReplaceChild("exterieur", CubeListBuilder.create().texOffs(96, 32).addBox(-7.0F, 8.0F, -3.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(58, 99).addBox(-7.0F, 1.0F, -3.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 15).addBox(-7.0F, 1.0F, 2.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 41).addBox(-7.0F, 8.0F, 2.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(22, 92).addBox(-7.0F, 3.0F, -7.0F, 14.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 92).addBox(-7.0F, 3.0F, 4.0F, 14.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(102, 0).addBox(-7.0F, 1.0F, 3.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(102, 5).addBox(-7.0F, 7.0F, 3.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(102, 10).addBox(-7.0F, 1.0F, -6.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 102).addBox(-7.0F, 7.0F, -6.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 18).addBox(-7.0F, -2.0F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 9).addBox(-7.0F, -1.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition cube_r1 = exterieur.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-7.0F, 0.5F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(90, 92).addBox(-7.0F, -1.5F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 0).addBox(-7.0F, -0.5F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.5F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition wheel2 = partdefinition.addOrReplaceChild("wheel2", CubeListBuilder.create().texOffs(66, 112).addBox(-2.0F, 3.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 112).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 113).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 113).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 64).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(108, 115).addBox(-1.0F, 3.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(114, 115).addBox(-1.0F, 3.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 116).addBox(-1.0F, 1.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 116).addBox(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 116).addBox(-1.0F, -2.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(116, 62).addBox(-1.0F, -4.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(116, 64).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(116, 66).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 113).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 113).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 109).addBox(-7.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(38, 109).addBox(1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 7.0F));

        PartDefinition exterieur2 = wheel2.addOrReplaceChild("exterieur2", CubeListBuilder.create().texOffs(108, 43).addBox(-7.0F, 8.0F, -3.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 45).addBox(-7.0F, 1.0F, -3.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 47).addBox(-7.0F, 1.0F, 2.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 49).addBox(-7.0F, 8.0F, 2.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(96, 18).addBox(-7.0F, 3.0F, -7.0F, 14.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(96, 25).addBox(-7.0F, 3.0F, 4.0F, 14.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(92, 102).addBox(-7.0F, 1.0F, 3.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 104).addBox(-7.0F, 7.0F, 3.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 107).addBox(-7.0F, 1.0F, -6.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(90, 107).addBox(-7.0F, 7.0F, -6.0F, 14.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(90, 97).addBox(-7.0F, -2.0F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 83).addBox(-7.0F, -1.0F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition cube_r2 = exterieur2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 23).addBox(-7.0F, 0.5F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(22, 99).addBox(-7.0F, -1.5F, -2.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 74).addBox(-7.0F, -0.5F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.5F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition relieroue = partdefinition.addOrReplaceChild("relieroue", CubeListBuilder.create().texOffs(48, 34).addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(48, 54).addBox(15.0F, -2.0F, -1.0F, 1.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(90, 112).addBox(0.0F, -8.0F, 7.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 112).addBox(15.0F, -8.0F, 7.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(86, 34).addBox(0.0F, -10.0F, 5.5F, 16.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 18.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
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

}

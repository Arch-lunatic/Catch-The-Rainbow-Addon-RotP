package com.archlunatic.rotp_ctr.client.render.entity.model.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransition;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransitionMultiple;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;

import net.minecraft.client.renderer.model.ModelRenderer;
// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class CatchTheRainbowModel extends HumanoidStandModel<CatchTheRainbowEntity> {

	public CatchTheRainbowModel() {
		super();
		
		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;
		
		
		head.texOffs(96, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, -0.1F, false);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		
		torso.texOffs(0, 64).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		torso.texOffs(20, 64).addBox(-3.5F, 1.1F, -2.0F, 7.0F, 3.0F, 1.0F, 0.4F, false);
		torso.texOffs(24, 73).addBox(-2.5F, 4.0F, -2.3F, 5.0F, 6.0F, 1.0F, 0.0F, false);

	}
/*
	@Override
	protected RotationAngle[][] initSummonPoseRotations() {
		return new RotationAngle[][] {
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 0, -15F, 0),
						RotationAngle.fromDegrees(body, -10F, -10F, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 10F, 0, -2.5F),
						RotationAngle.fromDegrees(leftForeArm, -15F, 0, 2.5F),
						RotationAngle.fromDegrees(rightArm, 10F, 0, 2.5F),
						RotationAngle.fromDegrees(rightForeArm, -2.5F, 0, 2.5F),
						RotationAngle.fromDegrees(leftLeg, 7.5F, 0, -5F),
						RotationAngle.fromDegrees(leftLowerLeg, 2.5F, 0, 5F),
						RotationAngle.fromDegrees(rightLeg, 5F, 0, 5F),
						RotationAngle.fromDegrees(rightLowerLeg, 2.5F, 0, -5)
				},
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 0, 0, 0),
						RotationAngle.fromDegrees(body, 5F, -20F, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 0, 0, 0),
						RotationAngle.fromDegrees(leftForeArm, -7.5F, 0, 0),
						RotationAngle.fromDegrees(rightArm, 0, -50F, 20F),
						RotationAngle.fromDegrees(rightForeArm, -40F, 0, 0),
						RotationAngle.fromDegrees(leftLeg, -15F, -15F, 0),
						RotationAngle.fromDegrees(leftLowerLeg, 10F, 0, 0),
						RotationAngle.fromDegrees(rightLeg, -7.5F, 15F, 0),
						RotationAngle.fromDegrees(rightLowerLeg, 2.5F, 0, 0)
				}
		};
	}

	@Override
	protected void initActionPoses() {
        actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<CatchTheRainbowEntity>()
                .addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPose<>(new RotationAngle[] {
                        new RotationAngle(body, 0.0F, -0.48F, 0.0F),
                        new RotationAngle(leftArm, 0.0F, 0.0F, 0.0F),
                        new RotationAngle(leftForeArm, 0.0F, 0.0F, 0.0F),
                        new RotationAngle(rightArm, -1.0908F, 0.0F, 1.5708F), 
                        new RotationAngle(rightForeArm, 0.0F, 0.0F, 0.0F)
                }))
                .build(idlePose));

		super.initActionPoses();
	}

	@Override
	protected ModelPose<CatchTheRainbowEntity> initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, -5F, 30F, 0.0F),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, 12.5F, -30F, -15F),
				RotationAngle.fromDegrees(leftForeArm, -12.5F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 10F, 30F, 15F),
				RotationAngle.fromDegrees(rightForeArm, -15F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftLeg, 20F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftLowerLeg, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLowerLeg, 5F, 0.0F, 0.0F)
		});
	}

	@Override
	protected ModelPose<CatchTheRainbowEntity> initIdlePose2Loop() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(leftArm, 7.5F, -30F, -15F),
				RotationAngle.fromDegrees(leftForeArm, -17.5F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 12.5F, 30F, 15F),
				RotationAngle.fromDegrees(rightForeArm, -17.5F, 0.0F, 0.0F)
		});
	}
*/
}
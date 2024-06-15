package com.archlunatic.rotp_ctr.client.render.entity.model.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CatchTheRainbowModel extends HumanoidStandModel<CatchTheRainbowEntity> {

	final ModelRenderer spine_r1;

	public CatchTheRainbowModel() {
		super();
		
		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;

		spine_r1 = new ModelRenderer(this);
		head.addChild(spine_r1);
		setRotationAngle(spine_r1, 0.0F, -0.5672F, 0.0F);
		spine_r1.texOffs(59, 8).addBox(-2.0F, 4.0F, -2.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
		spine_r1.texOffs(59, 8).addBox(-3.0F, 3.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
		spine_r1.texOffs(59, 8).addBox(2.0F, 2.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
	}
	
}
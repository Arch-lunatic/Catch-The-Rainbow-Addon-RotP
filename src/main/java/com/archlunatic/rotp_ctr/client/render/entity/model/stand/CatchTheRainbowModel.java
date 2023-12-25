package com.archlunatic.rotp_ctr.client.render.entity.model.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;

public class CatchTheRainbowModel extends HumanoidStandModel<CatchTheRainbowEntity> {

	public CatchTheRainbowModel() {
		super();
		
		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;
		
		head.texOffs(96, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, -0.1F, false);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
	}
}
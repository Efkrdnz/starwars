
package net.efkrdnz.starwarsverse.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.efkrdnz.starwarsverse.entity.StormTrooperEntity;

public class StormTrooperRenderer extends HumanoidMobRenderer<StormTrooperEntity, HumanoidModel<StormTrooperEntity>> {
	public StormTrooperRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(StormTrooperEntity entity) {
		return ResourceLocation.parse("starwarsverse:textures/entities/d36a28fbb9e85612.png");
	}
}


package net.efkrdnz.starwarsverse.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.efkrdnz.starwarsverse.entity.AttackHitboxEntity;
import net.efkrdnz.starwarsverse.client.model.Modelattack;

public class AttackHitboxRenderer extends MobRenderer<AttackHitboxEntity, Modelattack<AttackHitboxEntity>> {
	public AttackHitboxRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelattack(context.bakeLayer(Modelattack.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(AttackHitboxEntity entity) {
		return ResourceLocation.parse("starwarsverse:textures/entities/inv.png");
	}
}

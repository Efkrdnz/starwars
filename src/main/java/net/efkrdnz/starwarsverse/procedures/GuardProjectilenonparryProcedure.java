package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.api.AnimUtils;

@EventBusSubscriber
public class GuardProjectilenonparryProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		double rand = 0;
		if (entity.isShiftKeyDown() && (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:lightsaber")))
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("enabled")) {
			if (sourceentity instanceof Projectile || immediatesourceentity instanceof Projectile) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				rand = Mth.nextInt(RandomSource.create(), 1, 3);
				if (rand == 1) {
					playBlockAnimation(world, entity, "block");
				} else if (rand == 2) {
					playBlockAnimation(world, entity, "block2");
				} else {
					playBlockAnimation(world, entity, "block3");
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_block_blaster")), SoundSource.PLAYERS, (float) 0.5,
								1);
					} else {
						_level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_block_blaster")), SoundSource.PLAYERS, (float) 0.4, 1, false);
					}
				}
			}
		}
	}

	// helper method to safely play animations
	private static void playBlockAnimation(LevelAccessor world, Entity entity, String animationName) {
		AnimUtils.disableFirstPersonAnim = true;
		if (world.isClientSide()) {
			// check if animation exists before playing on client
			if (PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("starwarsverse", animationName)) != null) {
				SetupAnimationsProcedure.setAnimationClientside((Player) entity, animationName, false);
			}
		}
		if (!world.isClientSide()) {
			if (entity instanceof Player) {
				// always send the network message from server - the client will handle the check
				PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage(animationName, entity.getId(), false));
			}
		}
	}
}

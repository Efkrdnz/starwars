package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import javax.annotation.Nullable;

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
		if (entity.isShiftKeyDown()) {
			if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard > 0) {
				if (sourceentity instanceof Projectile || immediatesourceentity instanceof Projectile) {
					if (event instanceof ICancellableEvent _cancellable) {
						_cancellable.setCanceled(true);
					}
					rand = Mth.nextInt(RandomSource.create(), 1, 3);
					if (rand == 1) {
						AnimUtils.disableFirstPersonAnim = true;
						if (world.isClientSide()) {
							SetupAnimationsProcedure.setAnimationClientside((Player) entity, "block1", true);
						}
						if (!world.isClientSide()) {
							if (entity instanceof Player)
								PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("block1", entity.getId(), true));
						}
					} else if (rand == 2) {
						AnimUtils.disableFirstPersonAnim = true;
						if (world.isClientSide()) {
							SetupAnimationsProcedure.setAnimationClientside((Player) entity, "block2", true);
						}
						if (!world.isClientSide()) {
							if (entity instanceof Player)
								PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("block2", entity.getId(), true));
						}
					} else {
						AnimUtils.disableFirstPersonAnim = true;
						if (world.isClientSide()) {
							SetupAnimationsProcedure.setAnimationClientside((Player) entity, "block3", true);
						}
						if (!world.isClientSide()) {
							if (entity instanceof Player)
								PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("block3", entity.getId(), true));
						}
					}
				}
			}
		}
	}
}

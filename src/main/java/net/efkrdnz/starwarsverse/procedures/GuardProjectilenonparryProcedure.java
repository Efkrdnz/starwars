package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
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

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.init.StarwarsverseModEntities;
import net.efkrdnz.starwarsverse.entity.LaserEntity;

import javax.annotation.Nullable;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.api.AnimUtils;

@EventBusSubscriber
public class GuardProjectilenonparryProcedure {
	private static final double MIN_GUARD_REQUIRED = 5.0;
	private static final double MAX_GUARD_REQUIRED = 60.0;
	private static final double GUARD_COST_PER_BLOCK = 5.0;
	private static final double MAX_BLOCK_ANGLE = 90.0;
	private static final double LASER_REFLECTION_INACCURACY = 8.0;

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
		// check if player is holding enabled lightsaber and crouching
		if (!entity.isShiftKeyDown() || !hasEnabledLightsaber(entity)) {
			return;
		}
		// check if damage source is projectile
		if (!(sourceentity instanceof Projectile || immediatesourceentity instanceof Projectile)) {
			return;
		}
		// get the actual projectile entity
		Projectile projectile = getProjectileEntity(sourceentity, immediatesourceentity);
		if (projectile == null) {
			return;
		}
		// check guard points
		StarwarsverseModVariables.PlayerVariables playerVars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (playerVars.guard < MIN_GUARD_REQUIRED) {
			return;
		}
		if (playerVars.guard >= MAX_GUARD_REQUIRED) {
			return;
		}
		// check if player is facing the projectile direction
		if (!isFacingProjectile(entity, projectile)) {
			return;
		}
		// successful block - cancel damage
		if (event instanceof ICancellableEvent cancellable) {
			cancellable.setCanceled(true);
		}
		// handle laserbeam reflection vs other projectile blocking
		if (projectile instanceof LaserEntity) {
			// reflect laserbeam with inaccuracy
			reflectLaserbeam(world, entity, projectile);
		} else {
			// other projectiles just disappear
			projectile.discard();
		}
		// consume guard points and play effects
		consumeGuardPoints(entity, GUARD_COST_PER_BLOCK);
		playRandomBlockAnimation(world, entity);
		playBlockSound(world, entity);
	}

	// get the projectile entity from damage source
	private static Projectile getProjectileEntity(Entity sourceentity, Entity immediatesourceentity) {
		if (sourceentity instanceof Projectile projectile) {
			return projectile;
		}
		if (immediatesourceentity instanceof Projectile projectile) {
			return projectile;
		}
		return null;
	}

	// check if player has enabled lightsaber
	private static boolean hasEnabledLightsaber(Entity entity) {
		ItemStack mainHand = (entity instanceof LivingEntity living) ? living.getMainHandItem() : ItemStack.EMPTY;
		return mainHand.is(ItemTags.create(ResourceLocation.parse("minecraft:lightsaber"))) && mainHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("enabled");
	}

	// check if player is facing the projectile within blocking angle
	private static boolean isFacingProjectile(Entity player, Entity projectile) {
		Vec3 playerLookDirection = player.getLookAngle().normalize();
		Vec3 toProjectile = projectile.position().subtract(player.position()).normalize();
		double dotProduct = playerLookDirection.dot(toProjectile);
		double angle = Math.acos(Mth.clamp(dotProduct, -1.0, 1.0)) * (180.0 / Math.PI);
		return angle <= MAX_BLOCK_ANGLE;
	}

	// consume guard points with bounds checking
	private static void consumeGuardPoints(Entity entity, double cost) {
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		vars.guard = Math.max(0, vars.guard - cost);
		vars.syncPlayerVariables(entity);
	}

	// reflect laserbeam with inaccuracy
	private static void reflectLaserbeam(LevelAccessor world, Entity player, Projectile originalLaser) {
		if (!(world instanceof Level level) || !(player instanceof LivingEntity livingPlayer)) {
			originalLaser.discard();
			return;
		}
		// get player's look direction with added inaccuracy
		Vec3 lookDirection = player.getLookAngle().normalize();
		RandomSource random = level.getRandom();
		// add some inaccuracy to reflection
		double spreadX = (random.nextGaussian() * LASER_REFLECTION_INACCURACY) / 100.0;
		double spreadY = (random.nextGaussian() * LASER_REFLECTION_INACCURACY) / 100.0;
		double spreadZ = (random.nextGaussian() * LASER_REFLECTION_INACCURACY) / 100.0;
		Vec3 inaccurateDirection = lookDirection.add(spreadX, spreadY, spreadZ).normalize();
		Vec3 startPos = player.getEyePosition().add(inaccurateDirection.scale(0.5));
		// create new laser projectile
		LaserEntity newLaser = new LaserEntity(StarwarsverseModEntities.LASER.get(), livingPlayer, level, null);
		newLaser.setPos(startPos.x, startPos.y, startPos.z);
		newLaser.setOwner(player);
		// set velocity with slight speed boost
		Vec3 velocity = inaccurateDirection.scale(3.2);
		newLaser.setDeltaMovement(velocity);
		newLaser.setSilent(true);
		newLaser.setBaseDamage(5);
		newLaser.setCritArrow(false);
		// spawn the reflected laser
		level.addFreshEntity(newLaser);
		// remove original laser
		originalLaser.discard();
	}

	// play random block animation
	private static void playRandomBlockAnimation(LevelAccessor world, Entity entity) {
		double rand = Mth.nextInt(RandomSource.create(), 1, 3);
		String animationName;
		if (rand == 1) {
			animationName = "block";
		} else if (rand == 2) {
			animationName = "block2";
		} else {
			animationName = "block3";
		}
		playBlockAnimation(world, entity, animationName);
	}

	// helper method to safely play animations
	private static void playBlockAnimation(LevelAccessor world, Entity entity, String animationName) {
		AnimUtils.disableFirstPersonAnim = true;
		if (world.isClientSide()) {
			if (PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("starwarsverse", animationName)) != null) {
				SetupAnimationsProcedure.setAnimationClientside((Player) entity, animationName, false);
			}
		}
		if (!world.isClientSide()) {
			if (entity instanceof Player) {
				PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage(animationName, entity.getId(), false));
			}
		}
	}

	// play block sound effect
	private static void playBlockSound(LevelAccessor world, Entity entity) {
		if (world instanceof Level level) {
			if (!level.isClientSide()) {
				level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_block_blaster")), SoundSource.PLAYERS, 0.5f, 1);
			} else {
				level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_block_blaster")), SoundSource.PLAYERS, 0.4f, 1, false);
			}
		}
	}
}

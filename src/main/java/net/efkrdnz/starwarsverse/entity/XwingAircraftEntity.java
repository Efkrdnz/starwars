
package net.efkrdnz.starwarsverse.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.procedures.XwingAircraftOnEntityTickUpdateProcedure;
import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class XwingAircraftEntity extends PathfinderMob implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(XwingAircraftEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(XwingAircraftEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(XwingAircraftEntity.class, EntityDataSerializers.STRING);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public XwingAircraftEntity(EntityType<XwingAircraftEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "x_wing_texture");
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float f) {
		return super.getPassengerAttachmentPoint(entity, dimensions, f).add(0, 1f, 0);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		super.mobInteract(sourceentity, hand);
		sourceentity.startRiding(this);
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		XwingAircraftOnEntityTickUpdateProcedure.execute(this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		this.setNoGravity(true);
		// Enhanced flight controls - call our custom method
		handleDirectFlightControls();
	}

	// Add this new method to XwingAircraftEntity.java
	private void handleDirectFlightControls() {
		Entity driver = this.getControllingPassenger();
		if (driver == null) {
			// No pilot - gentle descent and auto-level
			Vec3 currentVel = this.getDeltaMovement();
			double drag = 0.85;
			double gravityEffect = -0.05;
			this.setDeltaMovement(new Vec3(currentVel.x * drag, Math.max(currentVel.y + gravityEffect, -0.5), currentVel.z * drag));
			// Auto-level
			float currentPitch = this.getXRot();
			if (Math.abs(currentPitch) > 1.0f) {
				this.setXRot(currentPitch * 0.95f);
			}
			this.setAnimation("idle_1");
			return;
		}
		this.setAnimation("idle_2");
		// Get control inputs directly from player variables
		if (!(driver instanceof Player player))
			return;
		StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		boolean forward = vars.ship_f;
		boolean backward = vars.ship_b;
		boolean left = vars.ship_l;
		boolean right = vars.ship_r;
		// Apply enhanced flight physics
		applyEnhancedFlightPhysics(player, forward, backward, left, right);
	}

	// Add this method to XwingAircraftEntity.java
	private void applyEnhancedFlightPhysics(Player pilot, boolean forward, boolean backward, boolean left, boolean right) {
		Vec3 currentVel = this.getDeltaMovement();
		Vec3 lookDirection = pilot.getLookAngle();
		// Flight parameters
		double maxSpeed = 2.5;
		double acceleration = 0.15;
		double deceleration = 0.92;
		// Calculate desired velocity
		Vec3 desiredVel = Vec3.ZERO;
		// Forward/backward movement
		if (forward && !backward) {
			desiredVel = lookDirection.scale(maxSpeed);
		} else if (backward && !forward) {
			desiredVel = lookDirection.scale(-maxSpeed * 0.6);
		}
		// Strafe movement
		if (left || right) {
			Vec3 rightVector = lookDirection.cross(new Vec3(0, 1, 0)).normalize();
			Vec3 strafeVel = Vec3.ZERO;
			if (left && !right) {
				strafeVel = rightVector.scale(-maxSpeed * 0.7);
			} else if (right && !left) {
				strafeVel = rightVector.scale(maxSpeed * 0.7);
			}
			if (forward || backward) {
				desiredVel = desiredVel.add(strafeVel.scale(0.5));
			} else {
				desiredVel = strafeVel;
			}
		}
		// Smooth velocity transition
		Vec3 velDiff = desiredVel.subtract(currentVel);
		double diffLength = velDiff.length();
		if (diffLength > 0.01) {
			double rate = (desiredVel.length() > currentVel.length()) ? acceleration : acceleration * 1.5;
			double maxChange = rate;
			if (diffLength > maxChange) {
				velDiff = velDiff.normalize().scale(maxChange);
			}
			Vec3 newVel = currentVel.add(velDiff);
			if (desiredVel.length() < 0.1) {
				newVel = newVel.scale(deceleration);
			}
			// Apply banking physics
			if (left && !right) {
				newVel = newVel.add(new Vec3(-0.02 * newVel.length(), -0.006 * newVel.length(), 0));
			} else if (right && !left) {
				newVel = newVel.add(new Vec3(0.02 * newVel.length(), -0.006 * newVel.length(), 0));
			}
			// Apply atmospheric effects
			double altitude = this.getY();
			double atmosphericDensity = Math.max(0.3, 1.0 - (altitude - 64) / 200.0);
			double windResistance = Math.min(0.98, 0.85 + (0.13 * atmosphericDensity));
			if (newVel.length() < 0.1) {
				windResistance = 0.99;
			}
			newVel = newVel.scale(windResistance);
			this.setDeltaMovement(newVel);
		}
		// Handle ship rotation
		handleShipRotation(pilot, left, right, forward, backward);
	}

	// Add this method to XwingAircraftEntity.java  
	private void handleShipRotation(Player pilot, boolean left, boolean right, boolean forward, boolean backward) {
		Vec3 lookDirection = pilot.getLookAngle();
		double targetYaw = Math.toDegrees(Math.atan2(-lookDirection.x, lookDirection.z));
		double targetPitch = Math.toDegrees(Math.asin(-lookDirection.y));
		double currentYaw = this.getYRot();
		double currentPitch = this.getXRot();
		double yawDiff = net.minecraft.util.Mth.wrapDegrees(targetYaw - currentYaw);
		double pitchDiff = net.minecraft.util.Mth.wrapDegrees(targetPitch - currentPitch);
		double rotationSpeed = (forward || backward) ? 0.3 : 0.15;
		this.setYRot((float) (currentYaw + yawDiff * rotationSpeed));
		this.setXRot((float) (currentPitch + pitchDiff * rotationSpeed));
		// Banking effect through pitch adjustment
		if (left && !right) {
			this.setXRot(this.getXRot() - 1.0f);
		} else if (right && !left) {
			this.setXRot(this.getXRot() - 1.0f);
		}
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 100);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.FLYING_SPEED, 0.3);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle_1"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationState event) {
		if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
			if (!this.animationprocedure.equals(prevAnim))
				event.getController().forceAnimationReset();
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
			if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
				this.animationprocedure = "empty";
				event.getController().forceAnimationReset();
			}
		} else if (animationprocedure.equals("empty")) {
			prevAnim = "empty";
			return PlayState.STOP;
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(XwingAircraftEntity.RemovalReason.KILLED);
			this.dropExperience(this);
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}

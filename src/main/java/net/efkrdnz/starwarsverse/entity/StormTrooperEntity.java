package net.efkrdnz.starwarsverse.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import net.efkrdnz.starwarsverse.procedures.StormTrooperOnEntityTickUpdateProcedure;
import net.efkrdnz.starwarsverse.init.StarwarsverseModEntities;

public class StormTrooperEntity extends Monster implements RangedAttackMob {
	public StormTrooperEntity(EntityType<StormTrooperEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		// melee attack for very close range
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		// hurt by target goal
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		// random movement
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		// target players
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
		// enhanced ranged attack with better accuracy at close range
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 8, 20f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return super.getPassengerRidingPosition(entity).add(0, -0.35F, 0);
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
	public void baseTick() {
		super.baseTick();
		StormTrooperOnEntityTickUpdateProcedure.execute(this);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		// improved accuracy based on distance
		improvedLaserShot(target);
	}

	// enhanced laser shooting with distance-based accuracy
	private void improvedLaserShot(LivingEntity target) {
		if (target == null)
			return;
		double distance = this.distanceTo(target);
		LaserEntity laser = new LaserEntity(StarwarsverseModEntities.LASER.get(), this, this.level(), null);
		// calculate trajectory with proper height targeting
		double dx = target.getX() - this.getX();
		double dy = target.getY() + (target.getBbHeight() * 0.5) - this.getY() - (this.getBbHeight() * 0.5); // aim at center mass
		double dz = target.getZ() - this.getZ();
		// improve accuracy at close range
		float inaccuracy;
		if (distance < 5.0) {
			// very accurate at close range
			inaccuracy = 2.0f;
		} else if (distance < 10.0) {
			// good accuracy at medium close range
			inaccuracy = 5.0f;
		} else if (distance < 15.0) {
			// moderate accuracy at medium range
			inaccuracy = 8.0f;
		} else {
			// storm trooper accuracy at long range
			inaccuracy = 12.0f;
		}
		// add some random spread for realism but keep it reasonable at close range
		RandomSource random = this.level().getRandom();
		double spreadX = (random.nextGaussian() * inaccuracy) / 100.0;
		double spreadY = (random.nextGaussian() * inaccuracy) / 100.0;
		double spreadZ = (random.nextGaussian() * inaccuracy) / 100.0;
		// apply spread
		dx += spreadX;
		dy += spreadY;
		dz += spreadZ;
		// shoot the laser with corrected trajectory
		laser.shoot(dx, dy, dz, 3f * 2, inaccuracy);
		laser.setSilent(true);
		laser.setBaseDamage(5);
		laser.setKnockback(0);
		laser.setCritArrow(false);
		this.level().addFreshEntity(laser);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20); // increased follow range
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.2);
		return builder;
	}
}

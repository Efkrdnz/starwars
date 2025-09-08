
package net.efkrdnz.starwarsverse.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;

import net.efkrdnz.starwarsverse.procedures.AttackHitboxOnEntityTickUpdateProcedure;

import java.util.UUID;

public class AttackHitboxEntity extends PathfinderMob {
	public static final EntityDataAccessor<Integer> DATA_amount = SynchedEntityData.defineId(AttackHitboxEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_life = SynchedEntityData.defineId(AttackHitboxEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_maxlife = SynchedEntityData.defineId(AttackHitboxEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_attacker = SynchedEntityData.defineId(AttackHitboxEntity.class, EntityDataSerializers.STRING);

	public AttackHitboxEntity(EntityType<AttackHitboxEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_amount, 0);
		builder.define(DATA_life, 0);
		builder.define(DATA_maxlife, 0);
		builder.define(DATA_attacker, "");
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
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Dataamount", this.entityData.get(DATA_amount));
		compound.putInt("Datalife", this.entityData.get(DATA_life));
		compound.putInt("Datamaxlife", this.entityData.get(DATA_maxlife));
		compound.putString("Dataattacker", this.entityData.get(DATA_attacker));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Dataamount"))
			this.entityData.set(DATA_amount, compound.getInt("Dataamount"));
		if (compound.contains("Datalife"))
			this.entityData.set(DATA_life, compound.getInt("Datalife"));
		if (compound.contains("Datamaxlife"))
			this.entityData.set(DATA_maxlife, compound.getInt("Datamaxlife"));
		if (compound.contains("Dataattacker"))
			this.entityData.set(DATA_attacker, compound.getString("Dataattacker"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		AttackHitboxOnEntityTickUpdateProcedure.execute(this);
	}

	@Override
	protected void doPush(Entity entity) {
		if (!this.level().isClientSide) {
			// get damage amount from synced data
			int damageAmount = this.entityData.get(DATA_amount);
			// get attacker entity from uuid
			Entity attacker = getAttackerEntity();
			// create damage source
			DamageSource damageSource;
			if (attacker != null) {
				damageSource = this.damageSources().mobAttack((LivingEntity) attacker);
			} else {
				damageSource = this.damageSources().generic();
			}
			// deal damage
			entity.hurt(damageSource, damageAmount);
			// remove hitbox after dealing damage
			this.discard();
		}
		super.doPush(entity);
	}

	private Entity getAttackerEntity() {
		String uuidString = this.entityData.get(DATA_attacker);
		if (uuidString != null && !uuidString.isEmpty()) {
			try {
				UUID attackerUuid = UUID.fromString(uuidString);
				// check players first
				for (Player player : this.level().players()) {
					if (player.getUUID().equals(attackerUuid)) {
						return player;
					}
				}
				// check other entities in area if needed
				return this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(100)).stream().filter(e -> e.getUUID().equals(attackerUuid)).findFirst().orElse(null);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		return builder;
	}
}

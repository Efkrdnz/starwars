package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Direction;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.init.StarwarsverseModParticleTypes;
import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;
import net.efkrdnz.starwarsverse.ForcePowers;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

@EventBusSubscriber
public class ForceLightningUseProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		boolean found = false;
		boolean entity_found = false;
		Entity starter = null;
		Entity target = null;
		List<Object> marked_entities = new ArrayList<>();
		List<Object> zigzagOffsetY = new ArrayList<>();
		List<Object> zigzagOffsetZ = new ArrayList<>();
		List<Object> zigzagOffsetX = new ArrayList<>();
		Vec3 Direction = Vec3.ZERO;
		Vec3 Perpendicular1 = Vec3.ZERO;
		Vec3 Perpendicular2 = Vec3.ZERO;
		Vec3 Data = Vec3.ZERO;
		Vec3 NormalizedData = Vec3.ZERO;
		double Steps = 0;
		double ZigzagPoints = 0;
		double currentZ = 0;
		double currentY = 0;
		double offsetX = 0;
		double raytrace_distance = 0;
		double offsetZ = 0;
		double offsetY = 0;
		double BX = 0;
		double BY = 0;
		double BZ = 0;
		double segmentPosition = 0;
		double localProgress = 0;
		double currentSegment = 0;
		double TotalDistance = 0;
		double inteligence = 0;
		double StepZ = 0;
		double Spacing = 0;
		double StepY = 0;
		double StepX = 0;
		double randomOffset2 = 0;
		double randomOffset1 = 0;
		double deviationStrength = 0;
		double currentX = 0;
		double AX = 0;
		double AY = 0;
		double SegmentLength = 0;
		double AZ = 0;
		double progress = 0;
		double segmentIndex = 0;
		double MaxDeviation = 0;
		double pitch = 0;
		double yaw = 0;
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_lightning) {
			ForcePowers.ForcePowerDefinition power = ForcePowers.getPower("force_lightning");
			if (power == null) {
				return;
			}
			int forceCost = power.getForceCost();
			if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power >= forceCost) {
				if (world.dayTime() % 2 == 0) {
					{
						StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
						_vars.force_power = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power - forceCost;
						_vars.syncPlayerVariables(entity);
					}
					raytrace_distance = 0;
					entity_found = false;
					for (int index0 = 0; index0 < 30; index0++) {
						if (!world
								.getEntitiesOfClass(LivingEntity.class,
										AABB.ofSize(
												new Vec3(
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getX()),
														(entity.level().clip(
																new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
																.getBlockPos().getY()),
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getZ())),
												3, 3, 3),
										e -> true)
								.isEmpty()
								&& !(((Entity) world
										.getEntitiesOfClass(LivingEntity.class,
												AABB.ofSize(new Vec3(
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getX()),
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getY()),
														(entity.level().clip(
																new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
																.getBlockPos().getZ())),
														3, 3, 3),
												e -> true)
										.stream().sorted(new Object() {
											Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
												return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
											}
										}.compareDistOf(
												(entity.level()
														.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
														.getBlockPos().getX()),
												(entity.level()
														.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
														.getBlockPos().getY()),
												(entity.level()
														.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
														.getBlockPos().getZ())))
										.findFirst().orElse(null)) == entity)) {
							if (((Entity) world
									.getEntitiesOfClass(LivingEntity.class,
											AABB.ofSize(
													new Vec3(
															(entity.level()
																	.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																			entity))
																	.getBlockPos().getX()),
															(entity.level()
																	.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																			entity))
																	.getBlockPos().getY()),
															(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER,
																	ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())),
													3, 3, 3),
											e -> true)
									.stream().sorted(new Object() {
										Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
											return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
										}
									}.compareDistOf(
											(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
													.getBlockPos().getX()),
											(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
													.getBlockPos().getY()),
											(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
													.getBlockPos().getZ())))
									.findFirst().orElse(null)).isAlive()) {
								if (!(((Entity) world
										.getEntitiesOfClass(LivingEntity.class,
												AABB.ofSize(new Vec3(
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getX()),
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getY()),
														(entity.level().clip(
																new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
																.getBlockPos().getZ())),
														3, 3, 3),
												e -> true)
										.stream().sorted(new Object() {
											Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
												return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
											}
										}.compareDistOf(
												(entity.level()
														.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
														.getBlockPos().getX()),
												(entity.level()
														.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
														.getBlockPos().getY()),
												(entity.level()
														.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
														.getBlockPos().getZ())))
										.findFirst().orElse(null)) instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false)) {
									entity_found = true;
								}
							}
						} else {
							entity_found = false;
							raytrace_distance = raytrace_distance + 1;
						}
					}
					starter = entity;
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(StarwarsverseModMobEffects.SCREEN_SHAKE, 3, 0, false, false));
					inteligence = 1;
					if (entity instanceof LivingEntity _entity)
						_entity.swing(InteractionHand.MAIN_HAND, true);
					if (entity_found) {
						target = (Entity) world
								.getEntitiesOfClass(LivingEntity.class,
										AABB.ofSize(
												new Vec3(
														(entity.level()
																.clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
																		entity))
																.getBlockPos().getX()),
														(entity.level()
																.clip(new ClipContext(
																		entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
																.getBlockPos().getY()),
														(entity.level().clip(
																new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
																.getBlockPos().getZ())),
												3, 3, 3),
										e -> true)
								.stream().sorted(new Object() {
									Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
										return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
									}
								}.compareDistOf(
										(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
												.getBlockPos().getX()),
										(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
												.getBlockPos().getY()),
										(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(raytrace_distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
												.getBlockPos().getZ())))
								.findFirst().orElse(null);
						for (int index1 = 0; index1 < 6; index1++) {
							found = false;
							{
								final Vec3 _center = new Vec3((starter.getX()), (starter.getY()), (starter.getZ()));
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(25 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
								for (Entity entityiterator : _entfound) {
									if (!found && !(entityiterator == entity) && !(entityiterator == starter) && entityiterator instanceof LivingEntity && !marked_entities.contains((entityiterator.getStringUUID()))) {
										if (target == null) {
											target = entityiterator;
										}
										Spacing = 0.05;
										AX = starter.getX();
										AY = starter.getY() + starter.getBbHeight() / 2;
										AZ = starter.getZ();
										BX = target.getX();
										BY = target.getY() + target.getBbHeight() / 2;
										BZ = target.getZ();
										TotalDistance = Math.sqrt(Math.abs(AX - BX) * Math.abs(AX - BX) + Math.abs(AY - BY) * Math.abs(AY - BY) + Math.abs(AZ - BZ) * Math.abs(AZ - BZ));
										Steps = Math.round(TotalDistance / Spacing);
										if (Steps > 0) {
											StepX = (BX - AX) / Steps;
											StepY = (BY - AY) / Steps;
											StepZ = (BZ - AZ) / Steps;
											MaxDeviation = 0.5;
											SegmentLength = 10;
											Direction = new Vec3(StepX, StepY, StepZ).normalize();
											Perpendicular1 = Direction.cross(new Vec3(0, 1, 0)).normalize();
											if (Perpendicular1.length() < 0.1) {
												Perpendicular1 = Direction.cross(new Vec3(1, 0, 0)).normalize();
											}
											Perpendicular2 = Direction.cross(Perpendicular1).normalize();
											ZigzagPoints = (int) (Steps / SegmentLength) + 1;
											for (int index2 = 0; index2 < (int) ZigzagPoints; index2++) {
												progress = (double) index2 / (ZigzagPoints - 1);
												deviationStrength = Math.sin(progress * Math.PI * MaxDeviation);
												randomOffset1 = (Mth.nextDouble(RandomSource.create(), 0, 1) - 0.5) * 2 * deviationStrength;
												randomOffset2 = (Mth.nextDouble(RandomSource.create(), 0, 1) - 0.5) * 2 * deviationStrength;
												zigzagOffsetX.add((Perpendicular1.x() * randomOffset1 + Perpendicular2.x() * randomOffset2));
												zigzagOffsetY.add((Perpendicular1.y() * randomOffset1 + Perpendicular2.y() * randomOffset2));
												zigzagOffsetZ.add((Perpendicular1.z() * randomOffset1 + Perpendicular2.z() * randomOffset2));
											}
											zigzagOffsetX.set(0, 0);
											zigzagOffsetY.set(0, 0);
											zigzagOffsetZ.set(0, 0);
											zigzagOffsetX.set((int) (ZigzagPoints - 1), 0);
											zigzagOffsetY.set((int) (ZigzagPoints - 1), 0);
											zigzagOffsetZ.set((int) (ZigzagPoints - 1), 0);
											for (int index3 = 0; index3 < (int) (Steps + 1); index3++) {
												currentX = AX + StepX * index3;
												currentY = AY + StepY * index3;
												currentZ = AZ + StepZ * index3;
												if (index3 > 0 && index3 < Steps) {
													segmentPosition = index3 / SegmentLength;
													segmentIndex = (int) segmentPosition;
													localProgress = segmentPosition - segmentIndex;
													if (segmentIndex >= ZigzagPoints - 1) {
														segmentIndex = ZigzagPoints - 2;
														localProgress = 1;
													}
													offsetX = (zigzagOffsetX.get((int) segmentIndex) instanceof Double _d ? _d : 0)
															+ ((zigzagOffsetX.get((int) (segmentIndex + 1)) instanceof Double _d ? _d : 0) - (zigzagOffsetX.get((int) segmentIndex) instanceof Double _d ? _d : 0)) * localProgress;
													offsetY = (zigzagOffsetY.get((int) segmentIndex) instanceof Double _d ? _d : 0)
															+ ((zigzagOffsetY.get((int) (segmentIndex + 1)) instanceof Double _d ? _d : 0) - (zigzagOffsetY.get((int) segmentIndex) instanceof Double _d ? _d : 0)) * localProgress;
													offsetZ = (zigzagOffsetZ.get((int) segmentIndex) instanceof Double _d ? _d : 0)
															+ ((zigzagOffsetZ.get((int) (segmentIndex + 1)) instanceof Double _d ? _d : 0) - (zigzagOffsetZ.get((int) segmentIndex) instanceof Double _d ? _d : 0)) * localProgress;
													currentX = currentX + offsetX;
													currentY = currentY + offsetY;
													currentZ = currentZ + offsetZ;
												}
												currentSegment = Math.round(index3 / SegmentLength);
												if (currentSegment % 4 == 0) {
													if (world instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (StarwarsverseModParticleTypes.LIGHTNING_BASE.get()), currentX, currentY, currentZ, 1, 0, 0, 0, 0);
												} else {
													if (world instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (StarwarsverseModParticleTypes.LIGHTNING.get()), currentX, currentY, currentZ, 1, 0, 0, 0, 0);
												}
											}
										}
										target.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("starwarsverse:force_lightning"))), entity),
												(float) (3 + entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level / 4));
										starter = target;
										marked_entities.add((target.getStringUUID()));
										target = null;
										found = true;
									}
								}
							}
						}
					} else {
						starter = entity;
						Spacing = 0.05;
						AX = starter.getX();
						AY = starter.getY() + starter.getBbHeight() / 2;
						AZ = starter.getZ();
						yaw = entity.getYRot() * 0.0174533 + Math.PI / 2;
						pitch = entity.getXRot() * 0.0174533;
						BX = starter.getX() + 12 * Math.cos(yaw) * Math.cos(pitch) + Mth.nextDouble(RandomSource.create(), -2, 2);
						BY = starter.getY() + starter.getBbHeight() / 2 + Mth.nextDouble(RandomSource.create(), 0, 2) + 12 * Math.sin((-1) * pitch);
						BZ = starter.getZ() + 12 * Math.sin(yaw) * Math.cos(pitch) + Mth.nextDouble(RandomSource.create(), -2, 2);
						TotalDistance = Math.sqrt(Math.abs(AX - BX) * Math.abs(AX - BX) + Math.abs(AY - BY) * Math.abs(AY - BY) + Math.abs(AZ - BZ) * Math.abs(AZ - BZ));
						Steps = Math.round(TotalDistance / Spacing);
						if (Steps > 0) {
							StepX = (BX - AX) / Steps;
							StepY = (BY - AY) / Steps;
							StepZ = (BZ - AZ) / Steps;
							MaxDeviation = 0.5;
							SegmentLength = 10;
							Direction = new Vec3(StepX, StepY, StepZ).normalize();
							Perpendicular1 = Direction.cross(new Vec3(0, 1, 0)).normalize();
							if (Perpendicular1.length() < 0.1) {
								Perpendicular1 = Direction.cross(new Vec3(1, 0, 0)).normalize();
							}
							Perpendicular2 = Direction.cross(Perpendicular1).normalize();
							ZigzagPoints = (int) (Steps / SegmentLength) + 1;
							for (int index4 = 0; index4 < (int) ZigzagPoints; index4++) {
								progress = (double) index4 / (ZigzagPoints - 1);
								deviationStrength = Math.sin(progress * Math.PI * MaxDeviation);
								randomOffset1 = (Mth.nextDouble(RandomSource.create(), 0, 1) - 0.5) * 2 * deviationStrength;
								randomOffset2 = (Mth.nextDouble(RandomSource.create(), 0, 1) - 0.5) * 2 * deviationStrength;
								zigzagOffsetX.add((Perpendicular1.x() * randomOffset1 + Perpendicular2.x() * randomOffset2));
								zigzagOffsetY.add((Perpendicular1.y() * randomOffset1 + Perpendicular2.y() * randomOffset2));
								zigzagOffsetZ.add((Perpendicular1.z() * randomOffset1 + Perpendicular2.z() * randomOffset2));
							}
							zigzagOffsetX.set(0, 0);
							zigzagOffsetY.set(0, 0);
							zigzagOffsetZ.set(0, 0);
							zigzagOffsetX.set((int) (ZigzagPoints - 1), 0);
							zigzagOffsetY.set((int) (ZigzagPoints - 1), 0);
							zigzagOffsetZ.set((int) (ZigzagPoints - 1), 0);
							for (int index5 = 0; index5 < (int) (Steps + 1); index5++) {
								currentX = AX + StepX * index5;
								currentY = AY + StepY * index5;
								currentZ = AZ + StepZ * index5;
								if (index5 > 0 && index5 < Steps) {
									segmentPosition = index5 / SegmentLength;
									segmentIndex = (int) segmentPosition;
									localProgress = segmentPosition - segmentIndex;
									if (segmentIndex >= ZigzagPoints - 1) {
										segmentIndex = ZigzagPoints - 2;
										localProgress = 1;
									}
									offsetX = (zigzagOffsetX.get((int) segmentIndex) instanceof Double _d ? _d : 0)
											+ ((zigzagOffsetX.get((int) (segmentIndex + 1)) instanceof Double _d ? _d : 0) - (zigzagOffsetX.get((int) segmentIndex) instanceof Double _d ? _d : 0)) * localProgress;
									offsetY = (zigzagOffsetY.get((int) segmentIndex) instanceof Double _d ? _d : 0)
											+ ((zigzagOffsetY.get((int) (segmentIndex + 1)) instanceof Double _d ? _d : 0) - (zigzagOffsetY.get((int) segmentIndex) instanceof Double _d ? _d : 0)) * localProgress;
									offsetZ = (zigzagOffsetZ.get((int) segmentIndex) instanceof Double _d ? _d : 0)
											+ ((zigzagOffsetZ.get((int) (segmentIndex + 1)) instanceof Double _d ? _d : 0) - (zigzagOffsetZ.get((int) segmentIndex) instanceof Double _d ? _d : 0)) * localProgress;
									currentX = currentX + offsetX;
									currentY = currentY + offsetY;
									currentZ = currentZ + offsetZ;
								}
								currentSegment = Math.round(index5 / SegmentLength);
								if (currentSegment % 4 == 0) {
									if (world instanceof ServerLevel _level)
										_level.sendParticles((SimpleParticleType) (StarwarsverseModParticleTypes.LIGHTNING_BASE.get()), currentX, currentY, currentZ, 1, 0, 0, 0, 0);
								} else {
									if (world instanceof ServerLevel _level)
										_level.sendParticles((SimpleParticleType) (StarwarsverseModParticleTypes.LIGHTNING.get()), currentX, currentY, currentZ, 1, 0, 0, 0, 0);
								}
							}
						}
					}
				}
			}
		}
	}
}

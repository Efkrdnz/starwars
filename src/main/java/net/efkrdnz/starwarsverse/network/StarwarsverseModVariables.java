package net.efkrdnz.starwarsverse.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.efkrdnz.starwarsverse.StarwarsverseMod;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class StarwarsverseModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, StarwarsverseMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		StarwarsverseMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
			PlayerVariables clone = new PlayerVariables();
			clone.force_level = original.force_level;
			clone.max_force_power = original.max_force_power;
			clone.light_side_points = original.light_side_points;
			clone.dark_side_points = original.dark_side_points;
			clone.skill_points = original.skill_points;
			clone.bound_power_1 = original.bound_power_1;
			clone.bound_power_2 = original.bound_power_2;
			clone.bound_power_3 = original.bound_power_3;
			clone.force_indicator = original.force_indicator;
			clone.can_use_force = original.can_use_force;
			if (!event.isWasDeath()) {
				clone.force_power = original.force_power;
				clone.gui_slot_index = original.gui_slot_index;
				clone.is_using_force = original.is_using_force;
				clone.current_charging_power = original.current_charging_power;
				clone.charge_timer = original.charge_timer;
				clone.telekinesis_target_uuid = original.telekinesis_target_uuid;
				clone.telekinesis_distance = original.telekinesis_distance;
				clone.is_using_telekinesis = original.is_using_telekinesis;
				clone.accumulated_throw_velocity_x = original.accumulated_throw_velocity_x;
				clone.accumulated_throw_velocity_y = original.accumulated_throw_velocity_y;
				clone.accumulated_throw_velocity_z = original.accumulated_throw_velocity_z;
				clone.previous_look_direction_x = original.previous_look_direction_x;
				clone.previous_look_direction_y = original.previous_look_direction_y;
				clone.previous_look_direction_z = original.previous_look_direction_z;
				clone.force_scroll_action = original.force_scroll_action;
				clone.telekinesis_resume_timer = original.telekinesis_resume_timer;
				clone.last_mouse_pitch = original.last_mouse_pitch;
				clone.is_force_pushing = original.is_force_pushing;
				clone.is_force_pulling = original.is_force_pulling;
				clone.force_push_animation_progress = original.force_push_animation_progress;
				clone.force_pull_animation_progress = original.force_pull_animation_progress;
				clone.is_using_lightning = original.is_using_lightning;
				clone.ship_f = original.ship_f;
				clone.ship_l = original.ship_l;
				clone.ship_r = original.ship_r;
				clone.ship_b = original.ship_b;
				clone.guard = original.guard;
				clone.lightsaber_attack_pattern = original.lightsaber_attack_pattern;
			}
			event.getEntity().setData(PLAYER_VARIABLES, clone);
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		public double force_level = 1.0;
		public double force_power = 120.0;
		public double max_force_power = 100.0;
		public double light_side_points = 0;
		public double dark_side_points = 0;
		public double skill_points = 0;
		public String bound_power_1 = "\"\"";
		public String bound_power_2 = "\"\"";
		public String bound_power_3 = "\"\"";
		public double gui_slot_index = 0;
		public boolean is_using_force = false;
		public String current_charging_power = "\"\"";
		public double charge_timer = 0;
		public String telekinesis_target_uuid = "\"\"";
		public double telekinesis_distance = 0;
		public boolean is_using_telekinesis = false;
		public boolean force_indicator = true;
		public double accumulated_throw_velocity_x = 0;
		public double accumulated_throw_velocity_y = 0;
		public double accumulated_throw_velocity_z = 0;
		public double previous_look_direction_x = 0;
		public double previous_look_direction_y = 0;
		public double previous_look_direction_z = 0;
		public String force_scroll_action = "\"\"";
		public double telekinesis_resume_timer = 0;
		public double last_mouse_pitch = 0;
		public boolean can_use_force = false;
		public boolean is_force_pushing = false;
		public boolean is_force_pulling = false;
		public double force_push_animation_progress = 0;
		public double force_pull_animation_progress = 0;
		public boolean is_using_lightning = false;
		public boolean ship_f = false;
		public boolean ship_l = false;
		public boolean ship_r = false;
		public boolean ship_b = false;
		public double guard = 0;
		public double lightsaber_attack_pattern = 0;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("force_level", force_level);
			nbt.putDouble("force_power", force_power);
			nbt.putDouble("max_force_power", max_force_power);
			nbt.putDouble("light_side_points", light_side_points);
			nbt.putDouble("dark_side_points", dark_side_points);
			nbt.putDouble("skill_points", skill_points);
			nbt.putString("bound_power_1", bound_power_1);
			nbt.putString("bound_power_2", bound_power_2);
			nbt.putString("bound_power_3", bound_power_3);
			nbt.putDouble("gui_slot_index", gui_slot_index);
			nbt.putBoolean("is_using_force", is_using_force);
			nbt.putString("current_charging_power", current_charging_power);
			nbt.putDouble("charge_timer", charge_timer);
			nbt.putString("telekinesis_target_uuid", telekinesis_target_uuid);
			nbt.putDouble("telekinesis_distance", telekinesis_distance);
			nbt.putBoolean("is_using_telekinesis", is_using_telekinesis);
			nbt.putBoolean("force_indicator", force_indicator);
			nbt.putDouble("accumulated_throw_velocity_x", accumulated_throw_velocity_x);
			nbt.putDouble("accumulated_throw_velocity_y", accumulated_throw_velocity_y);
			nbt.putDouble("accumulated_throw_velocity_z", accumulated_throw_velocity_z);
			nbt.putDouble("previous_look_direction_x", previous_look_direction_x);
			nbt.putDouble("previous_look_direction_y", previous_look_direction_y);
			nbt.putDouble("previous_look_direction_z", previous_look_direction_z);
			nbt.putString("force_scroll_action", force_scroll_action);
			nbt.putDouble("telekinesis_resume_timer", telekinesis_resume_timer);
			nbt.putDouble("last_mouse_pitch", last_mouse_pitch);
			nbt.putBoolean("can_use_force", can_use_force);
			nbt.putBoolean("is_force_pushing", is_force_pushing);
			nbt.putBoolean("is_force_pulling", is_force_pulling);
			nbt.putDouble("force_push_animation_progress", force_push_animation_progress);
			nbt.putDouble("force_pull_animation_progress", force_pull_animation_progress);
			nbt.putBoolean("is_using_lightning", is_using_lightning);
			nbt.putBoolean("ship_f", ship_f);
			nbt.putBoolean("ship_l", ship_l);
			nbt.putBoolean("ship_r", ship_r);
			nbt.putBoolean("ship_b", ship_b);
			nbt.putDouble("guard", guard);
			nbt.putDouble("lightsaber_attack_pattern", lightsaber_attack_pattern);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			force_level = nbt.getDouble("force_level");
			force_power = nbt.getDouble("force_power");
			max_force_power = nbt.getDouble("max_force_power");
			light_side_points = nbt.getDouble("light_side_points");
			dark_side_points = nbt.getDouble("dark_side_points");
			skill_points = nbt.getDouble("skill_points");
			bound_power_1 = nbt.getString("bound_power_1");
			bound_power_2 = nbt.getString("bound_power_2");
			bound_power_3 = nbt.getString("bound_power_3");
			gui_slot_index = nbt.getDouble("gui_slot_index");
			is_using_force = nbt.getBoolean("is_using_force");
			current_charging_power = nbt.getString("current_charging_power");
			charge_timer = nbt.getDouble("charge_timer");
			telekinesis_target_uuid = nbt.getString("telekinesis_target_uuid");
			telekinesis_distance = nbt.getDouble("telekinesis_distance");
			is_using_telekinesis = nbt.getBoolean("is_using_telekinesis");
			force_indicator = nbt.getBoolean("force_indicator");
			accumulated_throw_velocity_x = nbt.getDouble("accumulated_throw_velocity_x");
			accumulated_throw_velocity_y = nbt.getDouble("accumulated_throw_velocity_y");
			accumulated_throw_velocity_z = nbt.getDouble("accumulated_throw_velocity_z");
			previous_look_direction_x = nbt.getDouble("previous_look_direction_x");
			previous_look_direction_y = nbt.getDouble("previous_look_direction_y");
			previous_look_direction_z = nbt.getDouble("previous_look_direction_z");
			force_scroll_action = nbt.getString("force_scroll_action");
			telekinesis_resume_timer = nbt.getDouble("telekinesis_resume_timer");
			last_mouse_pitch = nbt.getDouble("last_mouse_pitch");
			can_use_force = nbt.getBoolean("can_use_force");
			is_force_pushing = nbt.getBoolean("is_force_pushing");
			is_force_pulling = nbt.getBoolean("is_force_pulling");
			force_push_animation_progress = nbt.getDouble("force_push_animation_progress");
			force_pull_animation_progress = nbt.getDouble("force_pull_animation_progress");
			is_using_lightning = nbt.getBoolean("is_using_lightning");
			ship_f = nbt.getBoolean("ship_f");
			ship_l = nbt.getBoolean("ship_l");
			ship_r = nbt.getBoolean("ship_r");
			ship_b = nbt.getBoolean("ship_b");
			guard = nbt.getDouble("guard");
			lightsaber_attack_pattern = nbt.getDouble("lightsaber_attack_pattern");
		}

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(StarwarsverseMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}

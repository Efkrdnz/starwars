
package net.efkrdnz.starwarsverse.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.world.inventory.ForcemenuguiMenu;
import net.efkrdnz.starwarsverse.procedures.Slotselect3Procedure;
import net.efkrdnz.starwarsverse.procedures.Slotselect2Procedure;
import net.efkrdnz.starwarsverse.procedures.Slotselect1Procedure;
import net.efkrdnz.starwarsverse.procedures.ForcemenuguiThisGUIIsClosedProcedure;
import net.efkrdnz.starwarsverse.procedures.AbilitySelectForceSenseProcedure;
import net.efkrdnz.starwarsverse.procedures.AbilitySelectForcePushProcedure;
import net.efkrdnz.starwarsverse.procedures.AbilitySelectForcePullProcedure;
import net.efkrdnz.starwarsverse.procedures.AbilitySelectForceLightningProcedure;
import net.efkrdnz.starwarsverse.procedures.AbilitySelectForceHoldProcedure;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

import java.util.Map;
import java.util.HashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record ForcemenuguiButtonMessage(int buttonID, int x, int y, int z, HashMap<String, String> textstate) implements CustomPacketPayload {

	public static final Type<ForcemenuguiButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(StarwarsverseMod.MODID, "forcemenugui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ForcemenuguiButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ForcemenuguiButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		writeTextState(message.textstate, buffer);
	}, (RegistryFriendlyByteBuf buffer) -> new ForcemenuguiButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), readTextState(buffer)));
	@Override
	public Type<ForcemenuguiButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final ForcemenuguiButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				Player entity = context.player();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				HashMap<String, String> textstate = message.textstate;
				handleButtonAction(entity, buttonID, x, y, z, textstate);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z, HashMap<String, String> textstate) {
		Level world = entity.level();
		HashMap guistate = ForcemenuguiMenu.guistate;
		// connect EditBox and CheckBox to guistate
		for (Map.Entry<String, String> entry : textstate.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			guistate.put(key, value);
		}
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == -2) {

			ForcemenuguiThisGUIIsClosedProcedure.execute(entity);
		}
		if (buttonID == 0) {

			Slotselect1Procedure.execute(entity);
		}
		if (buttonID == 1) {

			Slotselect2Procedure.execute(entity);
		}
		if (buttonID == 2) {

			Slotselect3Procedure.execute(entity);
		}
		if (buttonID == 3) {

			AbilitySelectForcePushProcedure.execute(entity);
		}
		if (buttonID == 4) {

			AbilitySelectForceHoldProcedure.execute(entity);
		}
		if (buttonID == 5) {

			AbilitySelectForcePullProcedure.execute(entity);
		}
		if (buttonID == 6) {

			AbilitySelectForceLightningProcedure.execute(entity);
		}
		if (buttonID == 7) {

			AbilitySelectForceSenseProcedure.execute(entity);
		}
	}

	private static void writeTextState(HashMap<String, String> map, RegistryFriendlyByteBuf buffer) {
		buffer.writeInt(map.size());
		for (Map.Entry<String, String> entry : map.entrySet()) {
			writeComponent(buffer, Component.literal(entry.getKey()));
			writeComponent(buffer, Component.literal(entry.getValue()));
		}
	}

	private static HashMap<String, String> readTextState(RegistryFriendlyByteBuf buffer) {
		int size = buffer.readInt();
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < size; i++) {
			String key = readComponent(buffer).getString();
			String value = readComponent(buffer).getString();
			map.put(key, value);
		}
		return map;
	}

	private static Component readComponent(RegistryFriendlyByteBuf buffer) {
		return ComponentSerialization.TRUSTED_STREAM_CODEC.decode(buffer);
	}

	private static void writeComponent(RegistryFriendlyByteBuf buffer, Component component) {
		ComponentSerialization.TRUSTED_STREAM_CODEC.encode(buffer, component);
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		StarwarsverseMod.addNetworkMessage(ForcemenuguiButtonMessage.TYPE, ForcemenuguiButtonMessage.STREAM_CODEC, ForcemenuguiButtonMessage::handleData);
	}
}

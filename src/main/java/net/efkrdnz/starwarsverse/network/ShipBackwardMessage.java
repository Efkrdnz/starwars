
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.efkrdnz.starwarsverse.procedures.ShipBackwardOnKeyReleasedProcedure;
import net.efkrdnz.starwarsverse.procedures.ShipBackwardOnKeyPressedProcedure;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record ShipBackwardMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<ShipBackwardMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(StarwarsverseMod.MODID, "key_ship_backward"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ShipBackwardMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ShipBackwardMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new ShipBackwardMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<ShipBackwardMessage> type() {
		return TYPE;
	}

	public static void handleData(final ShipBackwardMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				pressAction(context.player(), message.eventType, message.pressedms);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			ShipBackwardOnKeyPressedProcedure.execute(entity);
		}
		if (type == 1) {

			ShipBackwardOnKeyReleasedProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		StarwarsverseMod.addNetworkMessage(ShipBackwardMessage.TYPE, ShipBackwardMessage.STREAM_CODEC, ShipBackwardMessage::handleData);
	}
}

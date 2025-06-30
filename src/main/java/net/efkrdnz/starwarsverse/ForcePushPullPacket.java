package net.efkrdnz.starwarsverse.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import net.efkrdnz.starwarsverse.ForcePushPullHandler;

public record ForcePushPullPacket(boolean isPush) implements CustomPacketPayload {
    
    public static final Type<ForcePushPullPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwarsverse", "force_push_pull"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ForcePushPullPacket> STREAM_CODEC = 
        StreamCodec.composite(
            StreamCodec.of(
                (buf, value) -> buf.writeBoolean(value),
                buf -> buf.readBoolean()
            ), ForcePushPullPacket::isPush,
            ForcePushPullPacket::new
        );
    
    @Override
    public Type<ForcePushPullPacket> type() {
        return TYPE;
    }
    
    public static void handleData(final ForcePushPullPacket message, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                if (message.isPush) {
                    ForcePushPullHandler.executePush(player.level(), player);
                } else {
                    ForcePushPullHandler.executePull(player.level(), player);
                }
            }
        });
    }
}
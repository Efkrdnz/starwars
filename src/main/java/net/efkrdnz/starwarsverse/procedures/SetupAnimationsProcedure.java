package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.efkrdnz.starwarsverse.StarwarsverseMod;

import javax.annotation.Nullable;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;

@EventBusSubscriber(modid = "starwarsverse", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupAnimationsProcedure {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register((player, animationStack) -> {
			ModifierLayer<IAnimation> layer = new ModifierLayer<>();
			animationStack.addAnimLayer(69, layer);
			PlayerAnimationAccess.getPlayerAssociatedData(player).set(ResourceLocation.fromNamespaceAndPath("starwarsverse", "player_animation"), layer);
		});
	}

	@EventBusSubscriber(modid = "starwarsverse", bus = EventBusSubscriber.Bus.MOD)
	public static record StarwarsverseModAnimationMessage(String animation, int target, boolean override) implements CustomPacketPayload {

		public static final Type<StarwarsverseModAnimationMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(StarwarsverseMod.MODID, "setup_animations"));
		public static final StreamCodec<RegistryFriendlyByteBuf, StarwarsverseModAnimationMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, StarwarsverseModAnimationMessage message) -> {
			buffer.writeUtf(message.animation);
			buffer.writeInt(message.target);
			buffer.writeBoolean(message.override);
		}, (RegistryFriendlyByteBuf buffer) -> new StarwarsverseModAnimationMessage(buffer.readUtf(), buffer.readInt(), buffer.readBoolean()));
		@Override
		public Type<StarwarsverseModAnimationMessage> type() {
			return TYPE;
		}

		public static void handleData(final StarwarsverseModAnimationMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND) {
				context.enqueueWork(() -> {
					Level level = context.player().level();
					if (level.getEntity(message.target) != null) {
						Player player = (Player) level.getEntity(message.target);
						setAnimationClientside(player, message.animation, message.override);
					}
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}

		@SubscribeEvent
		public static void registerMessage(FMLCommonSetupEvent event) {
			StarwarsverseMod.addNetworkMessage(StarwarsverseModAnimationMessage.TYPE, StarwarsverseModAnimationMessage.STREAM_CODEC, StarwarsverseModAnimationMessage::handleData);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void setAnimationClientside(Player player, String anim, boolean override) {
		if (player instanceof net.minecraft.client.player.AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_).get(ResourceLocation.fromNamespaceAndPath("starwarsverse", "player_animation"));
			if (animation != null && override ? true : !animation.isActive()) {
				animation.replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(20, (modelName, type, value) -> value), PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("starwarsverse", anim)).playAnimation()
						.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration().setShowRightArm(true).setShowLeftItem(false)));
			}
		}
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
	}
}


/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import org.lwjgl.glfw.GLFW;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.efkrdnz.starwarsverse.network.ShipRightMessage;
import net.efkrdnz.starwarsverse.network.ShipLeftMessage;
import net.efkrdnz.starwarsverse.network.ShipForwardMessage;
import net.efkrdnz.starwarsverse.network.ShipBackwardMessage;
import net.efkrdnz.starwarsverse.network.AbilityGuiMessage;
import net.efkrdnz.starwarsverse.network.Ability3Message;
import net.efkrdnz.starwarsverse.network.Ability2Message;
import net.efkrdnz.starwarsverse.network.Ability1Message;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class StarwarsverseModKeyMappings {
	public static final KeyMapping ABILITY_1 = new KeyMapping("key.starwarsverse.ability_1", GLFW.GLFW_KEY_Z, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new Ability1Message(0, 0));
				Ability1Message.pressAction(Minecraft.getInstance().player, 0, 0);
				ABILITY_1_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - ABILITY_1_LASTPRESS);
				PacketDistributor.sendToServer(new Ability1Message(1, dt));
				Ability1Message.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping ABILITY_2 = new KeyMapping("key.starwarsverse.ability_2", GLFW.GLFW_KEY_X, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new Ability2Message(0, 0));
				Ability2Message.pressAction(Minecraft.getInstance().player, 0, 0);
				ABILITY_2_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - ABILITY_2_LASTPRESS);
				PacketDistributor.sendToServer(new Ability2Message(1, dt));
				Ability2Message.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping ABILITY_3 = new KeyMapping("key.starwarsverse.ability_3", GLFW.GLFW_KEY_C, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new Ability3Message(0, 0));
				Ability3Message.pressAction(Minecraft.getInstance().player, 0, 0);
				ABILITY_3_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - ABILITY_3_LASTPRESS);
				PacketDistributor.sendToServer(new Ability3Message(1, dt));
				Ability3Message.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping ABILITY_GUI = new KeyMapping("key.starwarsverse.ability_gui", GLFW.GLFW_KEY_V, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new AbilityGuiMessage(0, 0));
				AbilityGuiMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping SHIP_FORWARD = new KeyMapping("key.starwarsverse.ship_forward", GLFW.GLFW_KEY_W, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new ShipForwardMessage(0, 0));
				ShipForwardMessage.pressAction(Minecraft.getInstance().player, 0, 0);
				SHIP_FORWARD_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - SHIP_FORWARD_LASTPRESS);
				PacketDistributor.sendToServer(new ShipForwardMessage(1, dt));
				ShipForwardMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping SHIP_LEFT = new KeyMapping("key.starwarsverse.ship_left", GLFW.GLFW_KEY_A, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new ShipLeftMessage(0, 0));
				ShipLeftMessage.pressAction(Minecraft.getInstance().player, 0, 0);
				SHIP_LEFT_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - SHIP_LEFT_LASTPRESS);
				PacketDistributor.sendToServer(new ShipLeftMessage(1, dt));
				ShipLeftMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping SHIP_RIGHT = new KeyMapping("key.starwarsverse.ship_right", GLFW.GLFW_KEY_D, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new ShipRightMessage(0, 0));
				ShipRightMessage.pressAction(Minecraft.getInstance().player, 0, 0);
				SHIP_RIGHT_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - SHIP_RIGHT_LASTPRESS);
				PacketDistributor.sendToServer(new ShipRightMessage(1, dt));
				ShipRightMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping SHIP_BACKWARD = new KeyMapping("key.starwarsverse.ship_backward", GLFW.GLFW_KEY_S, "key.categories.starwars") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PacketDistributor.sendToServer(new ShipBackwardMessage(0, 0));
				ShipBackwardMessage.pressAction(Minecraft.getInstance().player, 0, 0);
				SHIP_BACKWARD_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - SHIP_BACKWARD_LASTPRESS);
				PacketDistributor.sendToServer(new ShipBackwardMessage(1, dt));
				ShipBackwardMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	private static long ABILITY_1_LASTPRESS = 0;
	private static long ABILITY_2_LASTPRESS = 0;
	private static long ABILITY_3_LASTPRESS = 0;
	private static long SHIP_FORWARD_LASTPRESS = 0;
	private static long SHIP_LEFT_LASTPRESS = 0;
	private static long SHIP_RIGHT_LASTPRESS = 0;
	private static long SHIP_BACKWARD_LASTPRESS = 0;

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(ABILITY_1);
		event.register(ABILITY_2);
		event.register(ABILITY_3);
		event.register(ABILITY_GUI);
		event.register(SHIP_FORWARD);
		event.register(SHIP_LEFT);
		event.register(SHIP_RIGHT);
		event.register(SHIP_BACKWARD);
	}

	@EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(ClientTickEvent.Post event) {
			if (Minecraft.getInstance().screen == null) {
				ABILITY_1.consumeClick();
				ABILITY_2.consumeClick();
				ABILITY_3.consumeClick();
				ABILITY_GUI.consumeClick();
				SHIP_FORWARD.consumeClick();
				SHIP_LEFT.consumeClick();
				SHIP_RIGHT.consumeClick();
				SHIP_BACKWARD.consumeClick();
			}
		}
	}
}

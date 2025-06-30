package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class XwingHUDProcedure {
	public static void updateHUD(Player player) {
		if (!(player.getVehicle() instanceof XwingAircraftEntity xwing))
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		// calculate flight data
		Vec3 velocity = xwing.getDeltaMovement();
		double speed = velocity.length() * 20; // convert to km/h equivalent
		double altitude = xwing.getY();
		// store HUD data in player variables (you can display these with overlays)
		// these could be displayed using MCreator's overlay system
		// speed indicator
		setHUDValue(playerVars, "flight_speed", speed);
		// altitude
		setHUDValue(playerVars, "flight_altitude", altitude);
		// heading
		double heading = normalizeHeading(xwing.getYRot());
		setHUDValue(playerVars, "flight_heading", heading);
		// pitch attitude
		setHUDValue(playerVars, "flight_pitch", xwing.getXRot());
		// ground distance
		double groundDist = getGroundDistance(xwing);
		setHUDValue(playerVars, "ground_distance", groundDist);
		// engine status
		boolean engineActive = playerVars.ship_f || playerVars.ship_b;
		setHUDValue(playerVars, "engine_active", engineActive ? 1 : 0);
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void setHUDValue(StarwarsverseModVariables.PlayerVariables vars, String key, double value) {
		// you would need to add these variables to your PlayerVariables class
		// for now, this is a placeholder showing the concept
		switch (key) {
			case "flight_speed" :
				// vars.hud_speed = value;
				break;
			case "flight_altitude" :
				// vars.hud_altitude = value;
				break;
			// add other cases as needed
		}
	}

	private static double normalizeHeading(float yaw) {
		double heading = ((yaw % 360) + 360) % 360;
		return heading > 180 ? heading - 360 : heading;
	}

	private static double getGroundDistance(XwingAircraftEntity xwing) {
		Level level = xwing.level();
		int groundY = level.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING, xwing.blockPosition()).getY();
		return Math.max(0, xwing.getY() - groundY);
	}
}

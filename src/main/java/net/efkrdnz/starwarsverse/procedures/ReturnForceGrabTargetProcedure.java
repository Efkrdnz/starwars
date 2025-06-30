package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import java.util.UUID;

public class ReturnForceGrabTargetProcedure {
	public static String execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_telekinesis) {
			if (!(new Object() {
				Entity entityFromStringUUID(String uuid, Level world) {
					Entity _uuidentity = null;
					if (world instanceof ServerLevel _server) {
						try {
							_uuidentity = _server.getEntity(UUID.fromString(uuid));
						} catch (Exception e) {
						}
					}
					return _uuidentity;
				}
			}.entityFromStringUUID(entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).telekinesis_target_uuid, (Level) world) == null)) {
				return new Object() {
					Entity entityFromStringUUID(String uuid, Level world) {
						Entity _uuidentity = null;
						if (world instanceof ServerLevel _server) {
							try {
								_uuidentity = _server.getEntity(UUID.fromString(uuid));
							} catch (Exception e) {
							}
						}
						return _uuidentity;
					}
				}.entityFromStringUUID(entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).telekinesis_target_uuid, (Level) world).getDisplayName().getString();
			}
			return "None";
		}
		return "";
	}
}

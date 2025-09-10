
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.efkrdnz.starwarsverse.block.SteelChasingBlock;
import net.efkrdnz.starwarsverse.block.MultipurposeWorkbenchBlock;
import net.efkrdnz.starwarsverse.block.LightsaberLightBlockBlock;
import net.efkrdnz.starwarsverse.block.KyberInfuserBlock;
import net.efkrdnz.starwarsverse.block.AircraftWorkbenchBlock;
import net.efkrdnz.starwarsverse.block.AircraftMainframeBlock;
import net.efkrdnz.starwarsverse.block.AircraftEngineBlock;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

public class StarwarsverseModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(StarwarsverseMod.MODID);
	public static final DeferredBlock<Block> KYBER_INFUSER = REGISTRY.register("kyber_infuser", KyberInfuserBlock::new);
	public static final DeferredBlock<Block> STEEL_CHASING = REGISTRY.register("steel_chasing", SteelChasingBlock::new);
	public static final DeferredBlock<Block> AIRCRAFT_MAINFRAME = REGISTRY.register("aircraft_mainframe", AircraftMainframeBlock::new);
	public static final DeferredBlock<Block> AIRCRAFT_ENGINE = REGISTRY.register("aircraft_engine", AircraftEngineBlock::new);
	public static final DeferredBlock<Block> AIRCRAFT_WORKBENCH = REGISTRY.register("aircraft_workbench", AircraftWorkbenchBlock::new);
	public static final DeferredBlock<Block> LIGHTSABER_LIGHT_BLOCK = REGISTRY.register("lightsaber_light_block", LightsaberLightBlockBlock::new);
	public static final DeferredBlock<Block> MULTIPURPOSE_WORKBENCH = REGISTRY.register("multipurpose_workbench", MultipurposeWorkbenchBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}

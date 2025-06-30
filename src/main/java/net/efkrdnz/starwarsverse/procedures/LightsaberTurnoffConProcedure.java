package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@EventBusSubscriber
public class LightsaberTurnoffConProcedure {
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        execute(event, event.getEntity().level(), event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        if (entity == null)
            return;
        if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandlerIter) {
            for (int _idx = 0; _idx < _modHandlerIter.getSlots(); _idx++) {
                ItemStack itemstack = _modHandlerIter.getStackInSlot(_idx);
                if (itemstack.is(ItemTags.create(ResourceLocation.parse("lightsaber"))) && !((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()
                        || (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem())) {
                    
                    // Use AtomicBoolean to capture the current state
                    AtomicBoolean wasEnabled = new AtomicBoolean(false);
                    
                    // Use a Consumer that doesn't return anything
                    Consumer<CompoundTag> tagConsumer = tag -> {
                        if (tag.contains("enabled")) {
                            wasEnabled.set(tag.getBoolean("enabled"));
                        }
                        tag.putBoolean("enabled", false);
                    };
                    
                    // Update the tag
                    CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tagConsumer);
                    
                    // If it was enabled, play the close sound
                    if (wasEnabled.get()) {
                        if (world instanceof Level _level) {
                            _level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), 
                                BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_close")), 
                                SoundSource.PLAYERS, (float)0.3, 1);
                        }
                    }
                    
                    // Save the changes back to the inventory
                    _modHandlerIter.setStackInSlot(_idx, itemstack);
                }
            }
        }
    }
}
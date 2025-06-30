package net.efkrdnz.starwarsverse;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class ForcePowers {
    private static final Map<String, ForcePowerDefinition> POWERS = new HashMap<>();
    
    static {
        // registry
        /*

			Made by Efkrdnz

        */
        registerPower(new ForcePowerDefinition("force_push")
            .setName("Force Push")
            .setAlignment("neutral")
            .setForceCost(20)
            .setCooldown(60)
            .setMinLevel(1)
            .setSkillCost(1)
            .setMaxChargeTime(20)); // 2 seconds charge
            
        registerPower(new ForcePowerDefinition("force_pull")
            .setName("Force Pull")
            .setAlignment("neutral")
            .setForceCost(20)
            .setCooldown(60)
            .setMinLevel(1)
            .setSkillCost(1)
            .setMaxChargeTime(20)); // 1 seconds charge
            
        registerPower(new ForcePowerDefinition("force_heal")
            .setName("Force Heal")
            .setAlignment("light")
            .setForceCost(30)
            .setCooldown(100)
            .setMinLevel(5)
            .setSkillCost(3)
            .setAlignmentRequirement("light", 20)
            .setMaxChargeTime(40)); // 2 seconds charge (complex healing)
            
        registerPower(new ForcePowerDefinition("force_lightning")
            .setName("Force Lightning")
            .setAlignment("dark")
            .setForceCost(5)
            .setCooldown(120)
            .setMinLevel(15)
            .setSkillCost(5)
            .setAlignmentRequirement("dark", 30)
            .setMaxChargeTime(0)); // Hold-to-use (not chargeable)
            
        registerPower(new ForcePowerDefinition("force_hold")
            .setName("Force Hold")
            .setAlignment("neutral")
            .setForceCost(10)
            .setCooldown(0)
            .setMinLevel(1)
            .setSkillCost(4)
            .setMaxChargeTime(0)); // Hold-to-use (not chargeable)
            
        registerPower(new ForcePowerDefinition("force_jump")
            .setName("Force Jump")
            .setAlignment("neutral")
            .setForceCost(15)
            .setCooldown(40)
            .setMinLevel(3)
            .setSkillCost(2)
            .setMaxChargeTime(20)); // 1 second charge (quick ability)

        registerPower(new ForcePowerDefinition("force_sense")
            .setName("Force Sense")
            .setAlignment("neutral")
            .setForceCost(30)
            .setCooldown(40)
            .setMinLevel(1)
            .setSkillCost(2)
            .setMaxChargeTime(10)); // 1 second charge (quick ability)
            
        registerPower(new ForcePowerDefinition("force_freeze")
            .setName("Force Freeze")
            .setAlignment("neutral")
            .setForceCost(25)
            .setCooldown(200)
            .setMinLevel(7)
            .setSkillCost(3)
            .setMaxChargeTime(0)); // Hold-to-use (toggle ability)
            //Made by Efkrdnz
            
        // Additional powers you might add later
        registerPower(new ForcePowerDefinition("force_choke")
            .setName("Force Choke")
            .setAlignment("dark")
            .setForceCost(35)
            .setCooldown(150)
            .setMinLevel(20)
            .setSkillCost(6)
            .setAlignmentRequirement("dark", 40)
            .setMaxChargeTime(0)); // Hold-to-use (continuous strangling)
            
        registerPower(new ForcePowerDefinition("force_shield")
            .setName("Force Shield")
            .setAlignment("light")
            .setForceCost(20)
            .setCooldown(80)
            .setMinLevel(8)
            .setSkillCost(3)
            .setAlignmentRequirement("light", 15)
            .setMaxChargeTime(0)); // Hold-to-use (continuous protection)
            
        registerPower(new ForcePowerDefinition("mind_trick")
            .setName("Mind Trick")
            .setAlignment("light")
            .setForceCost(50)
            .setCooldown(300)
            .setMinLevel(25)
            .setSkillCost(8)
            .setMaxChargeTime(5)); // 0.25 seconds charge (fast use)
    }
    
    public static void registerPower(ForcePowerDefinition power) {
        POWERS.put(power.getId(), power);
    }
    
    public static ForcePowerDefinition getPower(String id) {
        return POWERS.get(id);
        //Made by Efkrdnz
    }
    
    public static Collection<ForcePowerDefinition> getAllPowers() {
        return POWERS.values();
    }
    
    public static class ForcePowerDefinition {
        private String id;
        private String name;
        private String alignment;
        private int forceCost;
        private int cooldown;
        private int minLevel;
        private int skillCost;
        private int maxChargeTime; // NEW: Maximum charge time in ticks (0 = hold-to-use)
        private Map<String, Integer> alignmentRequirements;
        
        public ForcePowerDefinition(String id) {
            this.id = id;
            this.alignmentRequirements = new HashMap<>();
            this.maxChargeTime = 40; // Default 2 seconds if not specified
        }
        
        // Fluent builder methods
        public ForcePowerDefinition setName(String name) { this.name = name; return this; }
        public ForcePowerDefinition setAlignment(String alignment) { this.alignment = alignment; return this; }
        public ForcePowerDefinition setForceCost(int cost) { this.forceCost = cost; return this; }
        public ForcePowerDefinition setCooldown(int cooldown) { this.cooldown = cooldown; return this; }
        public ForcePowerDefinition setMinLevel(int level) { this.minLevel = level; return this; }
        public ForcePowerDefinition setSkillCost(int cost) { this.skillCost = cost; return this; }
        public ForcePowerDefinition setMaxChargeTime(int ticks) { this.maxChargeTime = ticks; return this; }
        public ForcePowerDefinition setAlignmentRequirement(String type, int amount) {
            this.alignmentRequirements.put(type, amount);
            return this;
            //Made by Efkrdnz
        }
        
        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getAlignment() { return alignment; }
        public int getForceCost() { return forceCost; }
        public int getCooldown() { return cooldown; }
        public int getMinLevel() { return minLevel; }
        public int getSkillCost() { return skillCost; }
        public int getMaxChargeTime() { return maxChargeTime; }
        public Map<String, Integer> getAlignmentRequirements() { return alignmentRequirements; }
        
        // Utility methods
        public boolean isChargeable() { 
            return maxChargeTime > 0; 
        }
        
        public boolean isHoldToUse() { 
            return maxChargeTime == 0; 
        }
        
        public float getChargeTimeInSeconds() { 
            return maxChargeTime / 20.0f; 
        }
    }
}
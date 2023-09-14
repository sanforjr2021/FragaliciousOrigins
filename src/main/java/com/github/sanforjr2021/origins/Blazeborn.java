package com.github.sanforjr2021.origins;


import com.github.sanforjr2021.data.jdbc.BlazeHeatDao;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBar;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public class Blazeborn extends Origin {

    private static int STARTING_HEAT;
    private int heat;
    private HeatLevel level;
    private boolean isScorching;
    private int abilityCooldown;

    public Blazeborn(Player player) {
        super(OriginType.BLAZEBORN, player);
        heat = BlazeHeatDao.getBlazeHeat(player.getUniqueId()); //retrieve from Database, if it does not exist, generate one from starting heat.
        if (heat == -1) {
            heat = STARTING_HEAT;
            MessageUtil.sendMessage("You logged in with a heat of " + heat, player);
        }else{
            MessageUtil.sendMessage("Your heat was set to " + heat, player);
        }
        isScorching = false;
        setHeat(heat);

    }
    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {
        BlazeHeatDao.write(getPlayer().getUniqueId(), STARTING_HEAT);
        setHeat(STARTING_HEAT);
    }

    public void remove() {
        super.remove();
        BlazeHeatDao.write(getPlayer().getUniqueId(), heat);
    }

    public static void reload() {
        STARTING_HEAT = ConfigHandler.getBlazeStartingHeat();
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        if(heat > HeatLevel.INCENDIUM.getMaxHeat()){
            heat = HeatLevel.INCENDIUM.getMaxHeat();
        }else if(heat < 0) {
            heat = 0;
        }
        this.heat = heat;
        HeatLevel heatLevel = calculateHeatLevel(heat);
        if( heatLevel != level){
            level = heatLevel;
            level.updateHeatLevel(getPlayer());
        }
        String title = this.heat + "°C";
        BarColor color;
        double percentage;
        //todo: find better solution to ChatColor
        switch (level){
            case INCENDIUM:
                title = ChatColor.translateAlternateColorCodes('&',"&cIncendium: " + title);
                percentage = ((double) heat - HeatLevel.INFERNO.getMaxHeat()) / ( (double) HeatLevel.INCENDIUM.getMaxHeat() - HeatLevel.INFERNO.getMaxHeat());
                color = BarColor.RED;
                break;
            case INFERNO:
                title = ChatColor.translateAlternateColorCodes('&',"&6Inferno: " + title);
                percentage = ((double) heat - HeatLevel.FlAME.getMaxHeat() ) / ( (double) HeatLevel.INFERNO.getMaxHeat() - HeatLevel.FlAME.getMaxHeat());
                color = BarColor.YELLOW;
                break;
            case FlAME:
                title = ChatColor.translateAlternateColorCodes('&',"&aFLAME: " + title);
                percentage = ((double) heat - HeatLevel.KINDLE.getMaxHeat()) / ( (double) HeatLevel.FlAME.getMaxHeat() - HeatLevel.KINDLE.getMaxHeat());
                color = BarColor.GREEN;
                break;
            case KINDLE:
                title = ChatColor.translateAlternateColorCodes('&',"&5Kindle: " + title);
                percentage = ((double) heat - HeatLevel.CINDER.getMaxHeat()) / ( (double) HeatLevel.KINDLE.getMaxHeat() - HeatLevel.CINDER.getMaxHeat());
                color = BarColor.PURPLE;
                break;
            default:
                title = ChatColor.translateAlternateColorCodes('&',"&1Cinder: " + title);
                percentage = ((double) heat) / ( (double) HeatLevel.CINDER.getMaxHeat());
                color = BarColor.BLUE;
                break;
        }
        getBossBar().setColor(color);
        getBossBar().setProgress(percentage);
        getBossBar().setTitle(title);
    }

    public HeatLevel getLevel() {
        return level;
    }

    private HeatLevel calculateHeatLevel(int heat){
        if(heat > HeatLevel.INFERNO.getMaxHeat()){
            return HeatLevel.INCENDIUM;
        }else if (heat > HeatLevel.FlAME.getMaxHeat() ){
            return HeatLevel.INFERNO;
        }else if(heat > HeatLevel.KINDLE.getMaxHeat()){
            return HeatLevel.FlAME;
        }else if(heat > HeatLevel.CINDER.getMaxHeat()){
            return HeatLevel.KINDLE;
        }else{
            return HeatLevel.CINDER;
        }
    }

    public boolean isScorching() {
        return isScorching;
    }

    public void setScorching(boolean scorching) {
        isScorching = scorching;
    }

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public void setAbilityCooldown(int abilityCooldown) {
        if(abilityCooldown < 0){
            abilityCooldown = 0;
        }
        this.abilityCooldown = abilityCooldown;
    }

    public enum Food {
        BLAZE_POWDER(Material.BLAZE_POWDER, ConfigHandler.getBlazeFoodBlazePowder()),
        BLAZE_ROD(Material.BLAZE_ROD, ConfigHandler.getBlazeFoodBlazeRod()),
        COAL(Material.COAL, ConfigHandler.getBlazeFoodCoal()),
        COAL_BLOCK(Material.COAL_BLOCK, ConfigHandler.getBlazeFoodCoalBlock()),
        CHARCOAL(Material.CHARCOAL, ConfigHandler.getBlazeFoodCharcoal()),
        FIRE_CHARGE(Material.FIRE_CHARGE, ConfigHandler.getBlazeFoodFireCharge());
        private final Material type;
        private final int maxLevel, heatValue;

        Food(Material type, String configValues) {
            this.type = type;
            String[] args = configValues.split(",");
            maxLevel = Integer.parseInt(args[0]);
            heatValue = Integer.parseInt(args[1]);
        }

        public Material getType() {
            return type;
        }

        public int getMaxLevel() {
            return maxLevel;
        }

        public int getHeatValue() {
            return heatValue;
        }
    }

    public enum HeatLevel {
        CINDER(1,ConfigHandler.getBlazeHeat1(), ConfigHandler.getBlazeEffect1(), ConfigHandler.getBlazeAbility1()),
        KINDLE(2, ConfigHandler.getBlazeHeat2(), ConfigHandler.getBlazeEffect2(), ConfigHandler.getBlazeAbility2()),
        FlAME(3, ConfigHandler.getBlazeHeat3(), ConfigHandler.getBlazeEffect3(), ConfigHandler.getBlazeAbility3()),
        INFERNO(4, ConfigHandler.getBlazeHeat4(), ConfigHandler.getBlazeEffect4(), ConfigHandler.getBlazeAbility4()),
        INCENDIUM(5, ConfigHandler.getBlazeHeat5(), ConfigHandler.getBlazeEffect5(), ConfigHandler.getBlazeAbility5());
        private final int level;
        private final int maxHeat, passiveDrain, waterDrain, damageDrain, maxHealth,  burnDuration;
        private final double speed, armor, toughness, burnChance;
        private final boolean drainInNether, passiveBurn, regen;
        //ability
        private final int cooldown, abilityDuration, damageDuration, abilityHeat;
        private final boolean purgeAbility;

        HeatLevel(int level, String heatConfig, String effectConfig, String abilityConfig) {
            String[] argsHeat = heatConfig.split(",");
            String[] argsEffects = effectConfig.split(",");
            String[] argsAbility = abilityConfig.split(",");
            this.level = level;
            //heat
            maxHeat = Integer.parseInt(argsHeat[0]);
            passiveDrain = Integer.parseInt(argsHeat[1]);
            waterDrain = Integer.parseInt(argsHeat[2]);
            damageDrain = Integer.parseInt(argsHeat[3]);
            drainInNether = Boolean.parseBoolean(argsHeat[4]);
            passiveBurn = Boolean.parseBoolean(argsHeat[5]);
            //benefits
            maxHealth = Integer.parseInt(argsEffects[0]);
            speed = Double.parseDouble(argsEffects[1]);
            armor = Double.parseDouble(argsEffects[2]);
            toughness = Double.parseDouble(argsEffects[3]);
            regen = Boolean.parseBoolean(argsEffects[4]);
            burnChance = Double.parseDouble(argsEffects[5]);
            burnDuration = Integer.parseInt(argsEffects[6]);
            //ability
            purgeAbility = Boolean.parseBoolean(argsAbility[0]);
            cooldown = Integer.parseInt(argsAbility[1]);
            abilityHeat = Integer.parseInt(argsAbility[2]);
            abilityDuration = Integer.parseInt(argsAbility[3]);
            damageDuration = Integer.parseInt(argsAbility[4]);
        }

        public int getLevel() {
            return level;
        }

        public int getMaxHeat() {
            return maxHeat;
        }

        public int getPassiveDrain() {
            return passiveDrain;
        }

        public int getWaterDrain() {
            return waterDrain;
        }

        public int getDamageDrain() {
            return damageDrain;
        }

        public int getMaxHealth() {
            return maxHealth;
        }

        public double getSpeed() {
            return speed;
        }

        public double getArmor() {
            return armor;
        }

        public double getToughness() {
            return toughness;
        }

        public double getBurnChance() {
            return burnChance;
        }

        public int getBurnDuration() {
            return burnDuration;
        }

        public boolean isDrainInNether() {
            return drainInNether;
        }

        public boolean isPassiveBurn() {
            return passiveBurn;
        }

        public boolean canRegen() {
            return regen;
        }

        public int getCooldown() {
            return cooldown;
        }

        public int getAbilityDuration() {
            return abilityDuration;
        }

        public int getAbilityHeat() {
            return abilityHeat;
        }

        public int getDamageDuration() {
            return damageDuration;
        }

        public boolean canPurgeAbility() {
            return purgeAbility;
        }
        public void updateHeatLevel(Player player){
            PlayerUtils.setMaxHealth(player, maxHealth);
            PlayerUtils.setWalkSpeed(player, (float) speed);
            PlayerUtils.setArmor(player,armor);
            PlayerUtils.setToughness(player, toughness);
            if(regen){
                player.setFoodLevel(20);
                player.setSaturation(20f);
            }else{
                player.setSaturation(0f);
                player.setFoodLevel(10);
            }
        }
    }

    private OriginBossBar getBossBar(){
            if( OriginBossBarManager.geOriginBossBar(getPlayer().getUniqueId(),BossBarType.HEAT_BAR) == null){
                OriginBossBarManager.addBossBar(this.getPlayer().getUniqueId(), new OriginBossBar(this.getPlayer(), BossBarType.HEAT_BAR, "Loading Heat", BarColor.RED, 0.5));
            }
            return OriginBossBarManager.geOriginBossBar(getPlayer().getUniqueId(),BossBarType.HEAT_BAR);
    }
}
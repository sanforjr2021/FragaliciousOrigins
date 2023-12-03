package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import static com.github.sanforjr2021.util.ConfigHandler.getFelineSpeed;

public class Feline extends Origin {
    public static float WALK_SPEED;
    private static int MAX_POUNCE_COOLDOWN;
    private boolean wet;

    public Feline(Player player) {
        super(OriginType.FELINE, player);
        wet = false;
        PlayerUtils.setLuck(player, 1.0);
    }

    @Override
    public void onLogin() {
        PlayerUtils.setWalkSpeed(getPlayer(), WALK_SPEED);
    }

    @Override
    public void onDeath() {
        PlayerUtils.setWalkSpeed(getPlayer(), WALK_SPEED);
    }

    public static void reload() {
        WALK_SPEED = (float) getFelineSpeed();
        MAX_POUNCE_COOLDOWN = ConfigHandler.getFelinePounceCooldown();
    }

    @Override
    public void setPrimaryCooldown(int primaryCooldown) {
        if (primaryCooldown < 0) {
            this.primaryCooldown = 0;
        } else {
            this.primaryCooldown = primaryCooldown;
        }
    }

    @Override
    public int getPrimaryMaxCooldown() {
        return MAX_POUNCE_COOLDOWN;
    }

    @Override
    public void primaryAbilityActivate() {
        Player player = getPlayer();
        if (primaryCooldown > 0) {
            primaryAbilityCooldownMessage("Pounce");
        } else {
            Vector lookDirection = player.getEyeLocation().getDirection();
            if (player.getLocation().getPitch() < -5) {
                player.setVelocity(lookDirection.multiply(2.5));
                PlayerUtils.addEffect(player, PotionEffectType.INCREASE_DAMAGE, 2, 60);
                setPrimaryCooldown(getPrimaryMaxCooldown());
            }
        }
    }

    @Override
    public void primaryAbilityTick() {
        if (primaryCooldown > 0) {
            setPrimaryCooldown(primaryCooldown-1);
        }
    }

    @Override
    public void setSecondaryCooldown(int secondaryCooldown) {
        //nothing
    }

    @Override
    public int getSecondaryMaxCooldown() {
        return 0;
    }

    @Override
    public void secondaryAbilityActivate() {
        //nothing
    }

    @Override
    public void secondaryAbilityTick() {
    }

    @Override
    public void passiveAbilitiesTick() {
        Player player = getPlayer();
        if (primaryCooldown > 0) {
            primaryAbilityTick();
        }
        if (PlayerUtils.isWetIgnoringConduit(player) && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER) && !wet) {
            PlayerUtils.addEffect(player, PotionEffectType.WEAKNESS, 1);
            PlayerUtils.addEffect(player, PotionEffectType.SLOW, 0);
            setWet(true);
        } else if (((!PlayerUtils.isWet(player)) || player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)) && wet) {
            PlayerUtils.removeEffect(player, PotionEffectType.WEAKNESS);
            PlayerUtils.removeEffect(player, PotionEffectType.SLOW);
            setWet(false);
        }
    }

    public boolean isWet() {
        return wet;
    }

    public void setWet(boolean wet) {
        this.wet = wet;
    }
}

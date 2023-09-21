package com.github.sanforjr2021.util.bossBar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class OriginBossBar {
    private BossBarType type;
    private String title;
    private BarColor color;
    private static final BarStyle style = BarStyle.SOLID;
    private final BossBar bossBar;
    private double progress;

    public OriginBossBar(Player player, BossBarType type , String title, BarColor color, double progress) {
        this.title = title;
        this.color = color;
        this.type = type;
        this.progress = progress;
        bossBar = Bukkit.createBossBar(title,color, BarStyle.SOLID);
        bossBar.addPlayer(player);
        bossBar.setProgress(progress);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        bossBar.setTitle(title);
    }


    public BarColor getColor() {
        return color;
    }

    public void setColor(BarColor color) {
        this.color = color;
        bossBar.setColor(color);
    }

    public BossBar getBossBar() {
        return bossBar;
    }


    public double getProgress() {
        return progress;
    }


    public void setProgress(double progress) {
        if(progress > 1.0 ){
            this.progress = 1.0;
        }else if(progress < 0.0){
            this.progress = 0.0;
        }else{
            this.progress = progress;
        }

        bossBar.setProgress(this.progress);
    }
    public void setProgressInverse(int numerator, int denominator) {
        double calculatedProgress = 1.0 - ((double) numerator) / ((double) denominator);
        if(calculatedProgress > 1.0 ){
            this.progress = 1.0;
        }else if(calculatedProgress < 0.0){
            this.progress = 0.0;
        }else{
            this.progress = calculatedProgress;
        }
        bossBar.setProgress(this.progress);
    }
    public void setProgress(int numerator, int denominator) {
        double calculatedProgress = ((double) numerator) / ((double) denominator);
        if(calculatedProgress > 1.0 ){
            this.progress = 1.0;
        }else if(calculatedProgress < 0.0){
            this.progress = 0.0;
        }else{
            this.progress = calculatedProgress;
        }

        bossBar.setProgress(this.progress);
    }

    public BossBarType getType() {
        return type;
    }

    public void setType(BossBarType type) {
        this.type = type;
    }

    public void toggleVisibility(){
        bossBar.setVisible(!bossBar.isVisible());
    }
    public void removeBossBar(){
        bossBar.removeAll();
    }


}
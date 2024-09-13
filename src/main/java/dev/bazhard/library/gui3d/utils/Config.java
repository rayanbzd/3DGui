package dev.bazhard.library.gui3d.utils;

import dev.bazhard.library.gui3d.Gui3D;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private FileConfiguration config;

    public void loadConfig() {
        Plugin instance = Gui3D.getInstance();
        instance.saveDefaultConfig();
        instance.reloadConfig();
        config = instance.getConfig();
    }

    public int getPlayerMoveUpdateFrequency(){
        return config.getInt("player-move-update-frequency");
    }

    public float getPlayerMoveUpdateMinimumOffset(){
        return config.getInt("player-move-update-minimum-offset");
    }

}

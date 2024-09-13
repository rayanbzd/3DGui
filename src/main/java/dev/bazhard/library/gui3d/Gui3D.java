package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.element.ItemDisplayElement;
import dev.bazhard.library.gui3d.listeners.DisplayElementClickListener;
import dev.bazhard.library.gui3d.listeners.DisplayElementHoverListener;
import dev.bazhard.library.gui3d.tasks.PlayerMoveTask;
import dev.bazhard.library.gui3d.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

public class Gui3D extends JavaPlugin implements CommandExecutor, Listener {

    private static Gui3D INSTANCE;
    public static Gui3D getInstance() {
        return INSTANCE;
    }
    private final DisplayManager displayManager = new DisplayManager();
    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    private final Config config = new Config();
    public Config getConfiguration() {
        return config;
    }
    private final PlayerMoveTask playerMoveTask = new PlayerMoveTask();

    @Override
    public void onEnable() {
        INSTANCE = this;

        config.loadConfig();
        Objects.requireNonNull(getCommand("gui3d")).setExecutor(this);
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DisplayElementHoverListener(), this);
        pm.registerEvents(new DisplayElementClickListener(), this);
        pm.registerEvents(playerMoveTask, this);
        playerMoveTask.start();
        getLogger().info("3D GUI has been enabled!");
    }

    @Override
    public void onDisable() {
        playerMoveTask.stop();
        getLogger().info("3D GUI has been disabled!");
    }
    // This is a test command and should be removed
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        if(!(sender instanceof Player player))return false;

        Location loc = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(3));
        ItemDisplayElement itemDisplayElement = new ItemDisplayElement(player, loc, player.getInventory().getItemInMainHand());
        itemDisplayElement.setInterpolationTransformDuration(10);
        itemDisplayElement.setHoverAction(viewer -> {
           itemDisplayElement.setScale(new Vector3f(1.2f, 1.2f, 1.2f));
           itemDisplayElement.setRotationLeft(player.getPitch(), player.getYaw());
           itemDisplayElement.setGlowing(true);
           itemDisplayElement.update();
        });
        itemDisplayElement.setUnhoverAction(viewer -> {
            itemDisplayElement.setScale(new Vector3f(1f, 1f, 1f));
            itemDisplayElement.setRotationLeft(new Quaternionf());
            itemDisplayElement.setGlowing(false);
            itemDisplayElement.update();
        });
        itemDisplayElement.show();
        return true;
    }

}

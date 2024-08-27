package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.listeners.DisplayElementClickListener;
import dev.bazhard.library.gui3d.listeners.DisplayElementHoverListener;
import dev.bazhard.library.gui3d.element.ItemDisplayElement;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
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

    @Override
    public void onEnable() {
        INSTANCE = this;
        getLogger().info("3D GUI has been enabled!");
        Objects.requireNonNull(getCommand("gui3d")).setExecutor(this);
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DisplayElementHoverListener(), this);
        pm.registerEvents(new DisplayElementClickListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("3D GUI has been disabled!");
    }

    private ItemDisplayElement itemDisplayElement;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        if(!(sender instanceof Player player))return false;

        if(itemDisplayElement == null) {
            itemDisplayElement = new ItemDisplayElement(player, new ItemStack(Material.valueOf(args[0])), player.getLocation());
            itemDisplayElement.setHoverAction(viewer -> {
                itemDisplayElement.setGlowing(true);
                itemDisplayElement.setGlowColor(Color.LIME);
                itemDisplayElement.setScale(new Vector3f(1.5f, 1.5f, 1.5f));
                itemDisplayElement.update();
            });
            itemDisplayElement.setUnhoverAction(viewer -> {
                itemDisplayElement.setGlowing(false);
                itemDisplayElement.setScale(new Vector3f(1f, 1f, 1f));
                itemDisplayElement.update();
            });
            itemDisplayElement.setClickAction(viewer -> {
                player.sendMessage("You clicked the element!");
            });
            itemDisplayElement.show();
        }else{
            itemDisplayElement.destroy();
            itemDisplayElement = null;
        }

        return true;
    }

}

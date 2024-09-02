package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.element.TextDisplayElement;
import dev.bazhard.library.gui3d.listeners.DisplayElementClickListener;
import dev.bazhard.library.gui3d.listeners.DisplayElementHoverListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

    private TextDisplayElement textDisplayElement;

    // This is a test command and should be removed
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        if(!(sender instanceof Player player))return false;

        if(textDisplayElement == null) {
            textDisplayElement = new TextDisplayElement(player, player.getLocation(), MiniMessage.miniMessage().deserialize("Lorem ipsum dolor sit amet<newline>consectetur adipiscing<newline><b>sed do eiusmod tempor incididunt</b>"));
            textDisplayElement.hasShadow(true);
            textDisplayElement.setGlowColor(Color.LIME);
            //textDisplayElement.setRotation(player.getPitch(), player.getYaw());
            textDisplayElement.setHoverAction(viewer -> {
                textDisplayElement.setGlowing(true);
                textDisplayElement.setBackgroundColor(Color.ORANGE);
                //textDisplayElement.setScale(new Vector3f(1.5f, 1.5f, 1.5f));
                textDisplayElement.update();
            });
            textDisplayElement.setUnhoverAction(viewer -> {
                textDisplayElement.setGlowing(false);
                textDisplayElement.setBackgroundColor(null);
                //textDisplayElement.setScale(new Vector3f(1f, 1f, 1f));
                textDisplayElement.update();
            });
            textDisplayElement.setClickAction(viewer -> {
                player.sendMessage("You clicked the element!");
            });
            textDisplayElement.show();
        }else{
            textDisplayElement.destroy();
            textDisplayElement = null;
        }

        return true;
    }

}

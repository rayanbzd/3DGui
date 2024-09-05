package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.element.BlockDisplayElement;
import dev.bazhard.library.gui3d.element.TextDisplayElement;
import dev.bazhard.library.gui3d.listeners.DisplayElementClickListener;
import dev.bazhard.library.gui3d.listeners.DisplayElementHoverListener;
import dev.bazhard.library.gui3d.utils.AABBVisualizer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BoundingBox;
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
    private BlockDisplayElement blockDisplayElement;
    private Location loc1, loc2;
    private AABBVisualizer aabbVisualizer;

    // This is a test command and should be removed
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        if(!(sender instanceof Player player))return false;

        //Block b = p.getTargetBlockExact(10);
        //if(b==null)return false;
        if (loc1 == null) {
            loc1 = player.getLocation();
            player.sendMessage("Location 1 set");
        } else if (loc2 == null) {
            loc2 = player.getLocation();
            player.sendMessage("Location 2 set");

            aabbVisualizer = new AABBVisualizer(player, BoundingBox.of(loc1, loc2), 0.025F);
            aabbVisualizer.show();
        } else {
            aabbVisualizer.hide();
            loc1 = null;
            loc2 = null;
        }

        /*if(textDisplayElement == null) {
            textDisplayElement = new TextDisplayElement(player, player.getLocation(), MiniMessage.miniMessage().deserialize("Lorem ipsum dolor sit amet<newline>consectetur adipiscing<newline><b>sed do eiusmod tempor incididunt</b>"));
            textDisplayElement.setRotation(player.getPitch(), player.getYaw());
            textDisplayElement.hasShadow(true);
            textDisplayElement.setGlowColor(Color.LIME);
            textDisplayElement.setInterpolationPosRotateDuration(5);
            textDisplayElement.setInterpolationTransformDuration(5);
            textDisplayElement.setHoverAction(viewer -> {
                textDisplayElement.setGlowing(true);
                textDisplayElement.setBackgroundColor(Color.LIME);
                textDisplayElement.setScale(new Vector3f(1.2f, 1.2f, 1.2f));
                textDisplayElement.update();
            });
            textDisplayElement.setUnhoverAction(viewer -> {
                textDisplayElement.setGlowing(false);
                textDisplayElement.setBackgroundColor(null);
                textDisplayElement.setScale(new Vector3f(1f, 1f, 1f));
                textDisplayElement.update();
            });
            textDisplayElement.setClickAction(viewer -> {
                player.sendMessage("You clicked the element!");
            });
            textDisplayElement.show();
        }else{
            textDisplayElement.destroy();
            textDisplayElement = null;
        }*/

        return true;
    }

}

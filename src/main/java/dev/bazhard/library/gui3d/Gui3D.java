package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.drawing.Box;
import dev.bazhard.library.gui3d.drawing.Composite;
import dev.bazhard.library.gui3d.drawing.DrawingContext;
import dev.bazhard.library.gui3d.drawing.MathUtils;
import dev.bazhard.library.gui3d.drawing.Sphere;
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
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.Objects;

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
        context.clear();
        getLogger().info("3D GUI has been disabled!");
    }

    private final DrawingContext context = new DrawingContext();

    // This is a test command and should be removed
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        if(!(sender instanceof Player player))return false;

        if (true) {
            context.clear();
            context.setViewer(player);
            //Line.from(player.getLocation(), 5).draw(context);
            Location location = player.getLocation();
            Box box = new Box(
                    //location.toVector().toVector3f(),
                    new Vector3f(),
                    new Vector3f(0.5f, 2, 1),
                    new Quaternionf()
                    //MathUtils.rotationFromToWithoutRoll(MathUtils.Z, location.getDirection().toVector3f())
                    //new Quaternionf().lookAlong(location.getDirection().toVector3f(), MathUtils.Y).conjugate()
            );
            Sphere sphere = new Sphere(
                    //location.toVector().toVector3f(),
                    new Vector3f(),
                    new Vector3f(2, 0.5f, 1),
                    new Quaternionf(),
                    //new Quaternionf().lookAlong(location.getDirection().toVector3f(), MathUtils.Y).conjugate(),
                    20,
                    10
            );
            new Composite(Arrays.asList(box, sphere), new Matrix4f().translationRotateScale(
                    location.toVector().toVector3f(),
                    new Quaternionf().lookAlong(location.getDirection().toVector3f(), MathUtils.Y).conjugate(),
                    new Vector3f(5)
            )).draw(context);
            context.show();

            return true;
        }

        return true;
    }

}

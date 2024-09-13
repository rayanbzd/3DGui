package dev.bazhard.library.gui3d.tasks;

import dev.bazhard.library.gui3d.Gui3D;
import dev.bazhard.library.gui3d.events.Gui3DPlayerMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMoveTask implements Listener {

    private final Map<UUID, Location> playerLastLocation = new HashMap<>();
    private Integer taskID;
    public void start() {
        if(taskID != null) return;

        int frequency = Gui3D.getInstance().getConfiguration().getPlayerMoveUpdateFrequency();
        float minOffset = Gui3D.getInstance().getConfiguration().getPlayerMoveUpdateMinimumOffset();

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gui3D.getInstance(), ()-> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                Location lastLocation = playerLastLocation.computeIfAbsent(player.getUniqueId(), uuid -> player.getLocation());
                Location currentLocation = player.getLocation();

                double diffX = Math.abs(lastLocation.getX() - currentLocation.getX());
                double diffY = Math.abs(lastLocation.getY() - currentLocation.getY());
                double diffZ = Math.abs(lastLocation.getZ() - currentLocation.getZ());
                float diffPitch = Math.abs(lastLocation.getPitch() - currentLocation.getPitch());
                float diffYaw = Math.abs(lastLocation.getYaw() - currentLocation.getYaw());

                if(diffX > minOffset || diffY > minOffset || diffZ > minOffset || diffPitch > minOffset || diffYaw > minOffset) {
                    playerLastLocation.put(player.getUniqueId(), currentLocation);
                    Bukkit.getPluginManager().callEvent(new Gui3DPlayerMoveEvent(player, lastLocation, currentLocation));
                }
            }
        }, 0, frequency);
    }

    public void stop() {
        if(taskID == null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        playerLastLocation.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        playerLastLocation.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
    }

}

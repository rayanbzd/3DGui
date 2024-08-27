package dev.bazhard.library.gui3d.listeners;

import dev.bazhard.library.gui3d.Gui3D;
import dev.bazhard.library.gui3d.element.GenericDisplayElement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class DisplayElementClickListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Set<GenericDisplayElement> elements = Gui3D.getInstance().getDisplayManager().getPlayerDisplayedElements().get(player.getUniqueId());
        if(elements == null)return;

        for(GenericDisplayElement element : elements){
            if(element.isCurrentlyHovered()){
                element.handleClick(player);
                if(element.cancelInteractEvent()){
                    event.setCancelled(true);
                }
            }
        }
    }

}

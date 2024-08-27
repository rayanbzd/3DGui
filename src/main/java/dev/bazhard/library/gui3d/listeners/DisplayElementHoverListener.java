package dev.bazhard.library.gui3d.listeners;

import dev.bazhard.library.gui3d.Gui3D;
import dev.bazhard.library.gui3d.element.GenericDisplayElement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Set;

public class DisplayElementHoverListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Set<GenericDisplayElement> elements = Gui3D.getInstance().getDisplayManager().getPlayerDisplayedElements().get(player.getUniqueId());
        if(elements == null)return;

        for(GenericDisplayElement element : elements){
            int distance = (int) player.getLocation().distance(element.getLocation());
            int maxDistance = element.getMaxInteractionDistance();
            if(element.isCurrentlyHovered()){
                if(distance > maxDistance || !element.isLookedAtByViewer(maxDistance)){
                    element.handleUnhover(player);
                }
            }else{
                if(distance <= maxDistance && element.isLookedAtByViewer(maxDistance)){
                    element.handleHover(player);
                }
            }
        }
    }

}

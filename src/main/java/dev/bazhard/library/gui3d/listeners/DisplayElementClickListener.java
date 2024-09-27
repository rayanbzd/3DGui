package dev.bazhard.library.gui3d.listeners;

import dev.bazhard.library.gui3d.Gui3D;
import dev.bazhard.library.gui3d.element.DisplayElement;
import dev.bazhard.library.gui3d.element.GenericDisplayElement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class DisplayElementClickListener implements Listener {

    private final Map<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Set<GenericDisplayElement> elements = Gui3D.getInstance().getDisplayManager().getPlayerDisplayedElements().get(player.getUniqueId());
        if(elements == null)return;
        Long cooldownTime = cooldown.get(player.getUniqueId());
        if(cooldownTime != null && cooldownTime > System.currentTimeMillis())return;

        Set<DisplayElement> eligibleElements = new HashSet<>();

        for(GenericDisplayElement element : elements){
            if(element.isCurrentlyHovered()){
                eligibleElements.add(element);
            }
        }

        DisplayElement clickedElement = null;
        for(DisplayElement element : eligibleElements){
            if(clickedElement == null){
                clickedElement = element;
            }else{
                if(element.getLocation().distance(player.getLocation()) < clickedElement.getLocation().distance(player.getLocation())){
                    clickedElement = element;
                }
            }
        }
        if(clickedElement != null){
            clickedElement.handleClick(player);
            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 250);
        }

    }

}

package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.element.GenericDisplayElement;

import java.util.*;

public class DisplayManager {

    private final Map<UUID, Integer> playerEntityIDIncrementer = new HashMap<>();
    private final Map<UUID, Set<GenericDisplayElement>> playerDisplayedElements = new HashMap<>();
    public Map<UUID, Set<GenericDisplayElement>> getPlayerDisplayedElements() {
        return playerDisplayedElements;
    }

    /**
     * Get the next entity ID for the player UUID (range from -1000 to -2000)
     * @param playerUUID The player UUID
     * @return The next entity ID
     */
    public int getNextEntityID(UUID playerUUID){
        return playerEntityIDIncrementer.compute(playerUUID, (k, v) -> v == null || v <= -2000 ? -1000 : v - 1);
    }

}

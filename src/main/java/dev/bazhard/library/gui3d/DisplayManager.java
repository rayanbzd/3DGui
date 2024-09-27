package dev.bazhard.library.gui3d;

import dev.bazhard.library.gui3d.element.GenericDisplayElement;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DisplayManager {

    private final ConcurrentHashMap<UUID, EntityIDPool> playerEntityIDPool = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Set<GenericDisplayElement>> playerDisplayedElements = new ConcurrentHashMap<>();
    public Map<UUID, Set<GenericDisplayElement>> getPlayerDisplayedElements() {
        return playerDisplayedElements;
    }

    /**
     * Get the next entity ID for the player UUID (range from -1000 to -2000)
     * @param playerUUID The player UUID
     * @return The next entity ID
     */
    public EntityIDPool getEntityIDPool(UUID playerUUID){
        return playerEntityIDPool.computeIfAbsent(playerUUID, k -> new EntityIDPool(-1000000, -1010000));
    }

    public static class EntityIDPool {
        private final Queue<Integer> pool;
        private final Set<Integer> locked;  // Set pour suivre les entiers verrouillés

        public EntityIDPool(int start, int end) {
            pool = new LinkedList<>();
            locked = new HashSet<>();
            for (int i = start; i >= end; i--) {
                pool.add(i);
            }
        }

        public Integer getNext() {
            if (pool.isEmpty()) {
                throw new NoSuchElementException("No more entity ID available");
            }
            Integer nextId = pool.poll();
            locked.add(nextId);
            return nextId;
        }

        // Méthode release qui lance une exception vérifiée
        public void release(Integer number) throws EntityNotLockedException {
            if (locked.contains(number)) {
                locked.remove(number);
                pool.offer(number);
            } else {
                throw new EntityNotLockedException("Entity ID " + number + " was not locked or already released");
            }
        }

        public int remaining() {
            return pool.size();
        }

        public boolean isEmpty() {
            return pool.isEmpty();
        }
    }

    // Exception personnalisée
    public static class EntityNotLockedException extends Exception {
        public EntityNotLockedException(String message) {
            super(message);
        }
    }

}

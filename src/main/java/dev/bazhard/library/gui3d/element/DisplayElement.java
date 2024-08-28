package dev.bazhard.library.gui3d.element;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.UUID;

public interface DisplayElement {

    @FunctionalInterface
    interface HoverAction{
        void onHover(Player viewer);
    }

    @FunctionalInterface
    interface UnhoverAction{
        void onUnhover(Player viewer);
    }

    @FunctionalInterface
    interface ClickAction{
        void onClick(Player viewer);
    }

    Player getViewer();
    int getEntityID();
    Location getLocation();
    boolean isGlowing();
    Color getGlowColor();
    boolean hasCustomName();
    Component getCustomName();
    Vector3f getScale();
    Vector3f getTranslation();
    Quaternionf getRotationRight();
    Quaternionf getRotationLeft();
    BoundingBox getBoundingBox();
    EntityType getEntityType();
    UUID getUUID();
    int getMaxInteractionDistance();
    boolean isCurrentlyHovered();
    boolean cancelInteractEvent();
    int getViewRangeInBlocks();

    DisplayElement setLocation(Location location);
    DisplayElement setGlowing(boolean glowing);
    DisplayElement setGlowColor(Color color);
    DisplayElement setCustomName(Component component);
    DisplayElement setScale(Vector3f scale);
    DisplayElement setTranslation(Vector3f translation);
    DisplayElement setRotationRight(Quaternionf rotation);
    DisplayElement setRotationLeft(Quaternionf rotation);
    DisplayElement setRotation(float pitch, float yaw);
    DisplayElement setHoverAction(HoverAction hover);
    DisplayElement setUnhoverAction(UnhoverAction unhover);
    DisplayElement setClickAction(ClickAction click);
    DisplayElement setMaxInteractionDistance(int distance);
    DisplayElement cancelInteractEvent(boolean cancel);
    DisplayElement setViewRangeInBlocks(int range);

    void show();
    void destroy();
    void update();
    void handleHover(Player viewer);
    void handleUnhover(Player viewer);
    void handleClick(Player viewer);

    boolean isLookedAtByViewer(int maxDistance);

}

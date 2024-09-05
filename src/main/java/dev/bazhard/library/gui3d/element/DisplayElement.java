package dev.bazhard.library.gui3d.element;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
    Quaternionf getRotationLeft();
    Quaternionf getRotationRight();
    EntityType getEntityType();
    UUID getUUID();
    int getMaxInteractionDistance();
    boolean isCurrentlyHovered();
    boolean cancelInteractEvent();
    int getViewRangeInBlocks();
    Billboard getBillboard();
    int getBrightnessOverride();
    int getInterpolationDelay();
    int getInterpolationTransformDuration();
    int getInterpolationPosRotateDuration();

    DisplayElement setLocation(Location location);
    DisplayElement setGlowing(boolean glowing);
    DisplayElement setGlowColor(Color color);
    DisplayElement setCustomName(Component component);
    DisplayElement setScale(Vector3f scale);
    DisplayElement setTranslation(Vector3f translation);
    DisplayElement setRotationRight(Quaternionf rotation);
    DisplayElement setRotationRight(float pitch, float yaw);
    DisplayElement setRotationLeft(Quaternionf rotation);
    DisplayElement setRotationLeft(float pitch, float yaw);
    DisplayElement setHoverAction(HoverAction hover);
    DisplayElement setUnhoverAction(UnhoverAction unhover);
    DisplayElement setClickAction(ClickAction click);
    DisplayElement setMaxInteractionDistance(int distance);
    DisplayElement cancelInteractEvent(boolean cancel);
    DisplayElement setViewRangeInBlocks(int range);
    DisplayElement setBillboard(Billboard billboard);
    DisplayElement setBrightnessOverride(int brightness);
    DisplayElement setInterpolationDelay(int delay);
    DisplayElement setInterpolationTransformDuration(int duration);
    DisplayElement setInterpolationPosRotateDuration(int duration);

    void show();
    void destroy();
    void update();
    void handleHover(Player viewer);
    void handleUnhover(Player viewer);
    void handleClick(Player viewer);

    boolean isLookedAtByViewer(int maxDistance);

    enum Billboard{
        FIXED((byte) 0),
        VERTICAL((byte) 1),
        HORIZONTAL((byte) 2),
        CENTER((byte) 3);

        private final byte byteValue;

        Billboard(byte byteValue){
            this.byteValue = byteValue;
        }

        public byte getByteValue() {
            return byteValue;
        }
    }

}

package dev.bazhard.library.gui3d.element;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import dev.bazhard.library.gui3d.Gui3D;
import dev.bazhard.library.gui3d.utils.WrappedDataSerializers;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

public abstract class GenericDisplayElement implements DisplayElement{

    private final int entityID;
    private final Player viewer;
    private Location location;
    private boolean glowing;
    private Color glowColor = Color.WHITE;
    private Component customName;
    private Vector3f scale = new Vector3f(1, 1, 1);
    private Vector3f translation = new Vector3f(0, 0, 0);
    private Quaternionf rotationRight = new Quaternionf(0, 0, 0, 1);
    private Quaternionf rotationLeft = new Quaternionf(0, 0, 0, 1);
    private HoverAction hover;
    private UnhoverAction unhover;
    private ClickAction click;
    private boolean isCurrentlyHovered = false;
    private final UUID uuid = UUID.randomUUID();
    private int maxInteractionDistance = 10;
    private boolean cancelInteractEvent = false;
    private int viewRangeInBlocks = 64;

    public GenericDisplayElement(Player viewer, Location location) {
        this.entityID = Gui3D.getInstance().getDisplayManager().getNextEntityID(viewer.getUniqueId());
        this.viewer = viewer;
        this.location = location;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Get the viewer of the entity
     * @return The viewer of the entity
     */
    @Override
    public Player getViewer() {
        return viewer;
    }

    /**
     * Get the entity ID
     * @return The entity ID
     */
    @Override
    public int getEntityID() {
        return entityID;
    }

    /**
     * Get the location of the entity
     * @return The location of the entity
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * Check if the entity is glowing
     * @return true if the entity is glowing
     */
    @Override
    public boolean isGlowing() {
        return glowing;
    }

    /**
     * Get the glow color of the entity
     * @return The glow color of the entity
     */
    @Override
    public Color getGlowColor() {
        return glowColor;
    }

    /**
     * Check if the entity has a custom name
     * @return true if the entity has a custom name
     */
    @Override
    public boolean hasCustomName() {
        return customName != null;
    }

    /**
     * Get the custom name of the entity
     * @return The custom name of the entity
     */
    @Override
    public Component getCustomName() {
        return customName;
    }

    /**
     * Get the scale of the entity
     * @return The scale of the entity
     */
    @Override
    public Vector3f getScale() {
        return scale;
    }

    /**
     * Get the translation of the entity
     * @return The translation of the entity
     */
    @Override
    public Vector3f getTranslation() {
        return translation;
    }

    /**
     * Get the rotation right of the entity
     * @return The right rotation of the entity
     */
    @Override
    public Quaternionf getRotationRight() {
        return rotationRight;
    }

    /**
     * Get the left rotation of the entity
     * @return The left rotation of the entity
     */
    @Override
    public Quaternionf getRotationLeft() {
        return rotationLeft;
    }

    /**
     * Get the bounding box of the entity
     * @return The bounding box of the entity
     */
    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }

    /**
     * Get the entity type
     * @return The entity type
     */
    @Override
    public EntityType getEntityType() {
        return null;
    }

    /**
     * Get the maximum interaction distance
     * @return The maximum interaction distance
     */
    @Override
    public int getMaxInteractionDistance() {
        return maxInteractionDistance;
    }

    /**
     * If the entity is currently hovered by the viewer
     * @return true if the entity is currently hovered by the viewer
     */
    @Override
    public boolean isCurrentlyHovered() {
        return isCurrentlyHovered;
    }

    /**
     * If the interact event should be cancelled when the viewer handle the click event
     * @return true if the interact event should be cancelled
     */
    @Override
    public boolean cancelInteractEvent() {
        return cancelInteractEvent;
    }

    /**
     * Get the view range in blocks
     * @return The view range in blocks
     */
    @Override
    public int getViewRangeInBlocks() {
        return viewRangeInBlocks;
    }

    /**
     * Set the location of the entity
     * @param location The location
     * @return The display element
     */
    @Override
    public DisplayElement setLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Set the glowing state of the entity
     * @param glowing The glowing state
     * @return The display element
     */
    @Override
    public DisplayElement setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    /**
     * Set the glow color of the entity
     * @param color The glow color
     * @return The display element
     */
    @Override
    public DisplayElement setGlowColor(Color color) {
        this.glowColor = color;
        return this;
    }

    /**
     * Set the custom name of the entity
     * @param component The custom name
     * @return The display element
     */
    @Override
    public DisplayElement setCustomName(Component component) {
        this.customName = component;
        return this;
    }

    /**
     * Set the scale of the entity
     * @param scale The scale
     * @return The display element
     */
    @Override
    public DisplayElement setScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }

    /**
     * Set the translation of the entity
     * @param translation The translation
     * @return The display element
     */
    @Override
    public DisplayElement setTranslation(Vector3f translation) {
        this.translation = translation;
        return this;
    }

    /**
     * Set the rotation right of the entity
     * @param rotation The right rotation
     * @return The display element
     */
    @Override
    public DisplayElement setRotationRight(Quaternionf rotation) {
        this.rotationRight = rotation;
        return this;
    }

    /**
     * Set the rotation left of the entity
     * @param rotation The left rotation
     * @return The display element
     */
    @Override
    public DisplayElement setRotationLeft(Quaternionf rotation) {
        this.rotationLeft = rotation;
        return this;
    }

    @Override
    public DisplayElement setRotation(float pitch, float yaw) {
        // Convert degrees to radians
        float pitchRad = (float) Math.toRadians(pitch);
        float yawRad = (float) Math.toRadians(yaw);

        // Calculate quaternion components
        float cy = (float) Math.cos(yawRad * 0.5);
        float sy = (float) Math.sin(yawRad * 0.5);
        float cp = (float) Math.cos(pitchRad * 0.5);
        float sp = (float) Math.sin(pitchRad * 0.5);

        // Create quaternion
        Quaternionf quaternion = new Quaternionf();
        quaternion.x = sp * cy;
        quaternion.y = -sy * cp;
        quaternion.z = sy * sp;
        quaternion.w = cp * cy;

        this.rotationRight = new Quaternionf(0, 0, 0, 1);
        this.rotationLeft = quaternion;

        return this;
    }

    /**
     *  Set the hover action
     * @param hover The hover action
     * @return The display element
     */
    @Override
    public DisplayElement setHoverAction(HoverAction hover) {
        this.hover = hover;
        return this;
    }

    /**
     * Set the unhover action
     * @param unhover The unhover action
     * @return The display element
     */
    @Override
    public DisplayElement setUnhoverAction(UnhoverAction unhover) {
        this.unhover = unhover;
        return this;
    }

    /**
     * Set the click action
     * @param click The click action
     * @return The display element
     */
    @Override
    public DisplayElement setClickAction(ClickAction click) {
        this.click = click;
        return this;
    }

    /**
     * Set the maximum interaction distance
     * @param distance The maximum interaction distance
     * @return The display element
     */
    @Override
    public DisplayElement setMaxInteractionDistance(int distance) {
        this.maxInteractionDistance = distance;
        return this;
    }

    /**
     * Set if the interact event should be cancelled when the viewer handle the click event
     * @param cancelInteractEvent The cancel interact event state
     * @return The display element
     */
    @Override
    public DisplayElement cancelInteractEvent(boolean cancelInteractEvent) {
        this.cancelInteractEvent = cancelInteractEvent;
        return this;
    }

    /**
     * Set the view range in blocks
     * @param range The view range in blocks
     * @return The display element
     */
    @Override
    public DisplayElement setViewRangeInBlocks(int range) {
        this.viewRangeInBlocks = range;
        return this;
    }

    /**
     * Show the entity to the viewer
     */
    @Override
    public void show() {
        if(!getViewer().isOnline())return;

        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers().write(0, getEntityID());
        spawnPacket.getUUIDs().write(0, UUID.randomUUID());
        spawnPacket.getEntityTypeModifier().write(0, getEntityType());
        spawnPacket.getDoubles().write(0, getLocation().getX());
        spawnPacket.getDoubles().write(1, getLocation().getY());
        spawnPacket.getDoubles().write(2, getLocation().getZ());

        ProtocolLibrary.getProtocolManager().sendServerPacket(getViewer(), spawnPacket);

        Set<GenericDisplayElement> elements = Gui3D.getInstance().getDisplayManager().getPlayerDisplayedElements()
                .computeIfAbsent(getViewer().getUniqueId(), k -> new HashSet<>());

        if (elements.stream().noneMatch(e -> e.getUUID().equals(this.getUUID()))) {
            elements.add(this);
        }

        update();
    }

    /**
     * Hide the entity from the viewer
     */
    @Override
    public void destroy() {
        PacketContainer destroyEntity = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        destroyEntity.getModifier().write(0, new IntArrayList(new int[]{getEntityID()}));
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(destroyEntity);

        Set<GenericDisplayElement> elements = Gui3D.getInstance().getDisplayManager().getPlayerDisplayedElements()
                .computeIfAbsent(getViewer().getUniqueId(), k -> new HashSet<>());
        elements.removeIf(e -> e.getUUID().equals(this.getUUID()));
    }

    /**
     * Update the entity meta data
     */
    @Override
    public void update() {
        if (!getViewer().isOnline()) return;

        PacketContainer metaDataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA); // Apply meta data packet (https://wiki.vg/Protocol#Set_Entity_Metadata)
        metaDataPacket.getIntegers().write(0, getEntityID()); // Entity ID
        List<WrappedDataValue> dataValues = new ArrayList<>(); // Meta datas (https://wiki.vg/Entity_metadata#Entity_Metadata)

        dataValues.add(new WrappedDataValue(0, WrappedDataSerializers.byteSerializer, (byte) (isGlowing() ? 0x40 : 0x00))); // Glowing flag
        if (isGlowing()) {
            dataValues.add(new WrappedDataValue(22, WrappedDataSerializers.integerSerializer, getGlowColor().asRGB())); // Glowing color
        }

        dataValues.add(new WrappedDataValue(3, WrappedDataSerializers.booleanSerializer, hasCustomName())); // Custom name visible
        if (hasCustomName()) {
            dataValues.add(new WrappedDataValue(2, WrappedDataSerializers.optionalComponentSerializer, // Custom name
                    Optional.of(WrappedChatComponent.fromJson(JSONComponentSerializer.json().serialize(getCustomName())).getHandle())
            ));
        }

        dataValues.add(new WrappedDataValue(11, WrappedDataSerializers.vector3fSerializer, getTranslation())); // Translation

        dataValues.add(new WrappedDataValue(12, WrappedDataSerializers.vector3fSerializer, getScale())); // Scale

        dataValues.add(new WrappedDataValue(13, WrappedDataSerializers.quaternionfSerializer, getRotationLeft())); // Rotation left
        dataValues.add(new WrappedDataValue(14, WrappedDataSerializers.quaternionfSerializer, getRotationRight())); // Rotation right

        dataValues.add(new WrappedDataValue(17, WrappedDataSerializers.floatSerializer, (float)(viewRangeInBlocks/64))); // View range (1F = 64 blocks)

        List<WrappedDataValue> additionalDataValues = getAdditionalDataValues();
        if (additionalDataValues != null) dataValues.addAll(additionalDataValues);

        metaDataPacket.getDataValueCollectionModifier().write(0, dataValues);

        ProtocolLibrary.getProtocolManager().sendServerPacket(getViewer(), metaDataPacket);
    }

    /**
     * RayCasting - Check if the viewer is looking at the entity bounding box within a certain distance
     * @param maxDistance The maximum distance to check
     * @return true if the viewer is looking at the entity bounding box within the maximum distance
     */
    @Override
    public boolean isLookedAtByViewer(int maxDistance) {
        if(!viewer.isOnline())return false;
        Location playerLocation = viewer.getEyeLocation();
        Vector direction = playerLocation.getDirection().normalize();

        Vector entityMin = getBoundingBox().getMin();
        Vector entityMax = getBoundingBox().getMax();

        double invDirX = 1.0 / direction.getX();
        double invDirY = 1.0 / direction.getY();
        double invDirZ = 1.0 / direction.getZ();

        double tMin, tMax, tyMin, tyMax, tzMin, tzMax;

        if (invDirX >= 0) {
            tMin = (entityMin.getX() - playerLocation.getX()) * invDirX;
            tMax = (entityMax.getX() - playerLocation.getX()) * invDirX;
        } else {
            tMin = (entityMax.getX() - playerLocation.getX()) * invDirX;
            tMax = (entityMin.getX() - playerLocation.getX()) * invDirX;
        }

        if (invDirY >= 0) {
            tyMin = (entityMin.getY() - playerLocation.getY()) * invDirY;
            tyMax = (entityMax.getY() - playerLocation.getY()) * invDirY;
        } else {
            tyMin = (entityMax.getY() - playerLocation.getY()) * invDirY;
            tyMax = (entityMin.getY() - playerLocation.getY()) * invDirY;
        }

        if ((tMin > tyMax) || (tyMin > tMax)) {
            return false; // No intersection on Y-axis.
        }

        if (tyMin > tMin) tMin = tyMin;
        if (tyMax < tMax) tMax = tyMax;

        if (invDirZ >= 0) {
            tzMin = (entityMin.getZ() - playerLocation.getZ()) * invDirZ;
            tzMax = (entityMax.getZ() - playerLocation.getZ()) * invDirZ;
        } else {
            tzMin = (entityMax.getZ() - playerLocation.getZ()) * invDirZ;
            tzMax = (entityMin.getZ() - playerLocation.getZ()) * invDirZ;
        }

        if ((tMin > tzMax) || (tzMin > tMax)) {
            return false; // No intersection on Z-axis.
        }

        if (tzMin > tMin) tMin = tzMin;
        if (tzMax < tMax) tMax = tzMax;

        return tMin < maxDistance && tMax > 0; // Check if the intersection is within the maximum distance
    }

    /* RayTracing (alternative, more precise method but less efficient)
    @Override
    public boolean isLookedAtByViewer(Player player, int maxDistance) {
        Location playerLocation = player.getEyeLocation();
        Vector direction = playerLocation.getDirection();

        Vector entityMin = getBoundingBox().getMin();
        Vector entityMax = getBoundingBox().getMax();

        for (double i = 0; i < maxDistance; i += 0.01) {
            Vector ray = direction.clone().multiply(i);
            Vector point = playerLocation.toVector().add(ray);

            if (point.isInAABB(entityMin, entityMax)) {
                return true;  // Le joueur regarde l'entité
            }
        }
        return false; // Le joueur ne regarde pas l'entité
    }*/

    @Override
    public void handleHover(Player viewer){
        if(hover == null)return;
        hover.onHover(viewer);
        isCurrentlyHovered = true;
    }

    @Override
    public void handleUnhover(Player viewer){
        if(unhover == null)return;
        unhover.onUnhover(viewer);
        isCurrentlyHovered = false;
    }

    @Override
    public void handleClick(Player viewer){
        if(click != null)click.onClick(viewer);
    }

    protected abstract List<WrappedDataValue> getAdditionalDataValues();
}

package dev.bazhard.library.gui3d.element;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import dev.bazhard.library.gui3d.DisplayManager;
import dev.bazhard.library.gui3d.Gui3D;
import dev.bazhard.library.gui3d.utils.WrappedDataSerializers;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

public abstract class GenericDisplayElement implements DisplayElement{

    private int entityID;
    private final Player viewer;
    private Location location;
    private boolean glowing;
    private Color glowColor = Color.WHITE;
    private Component customName;
    private Vector3f scale = new Vector3f(1, 1, 1);
    private Vector3f translation = new Vector3f(0, 0, 0);
    private Quaternionf rotationLeft = new Quaternionf(0, 0, 0, 1);
    private Quaternionf rotationRight = new Quaternionf(0, 0, 0, 1);
    private HoverAction hover;
    private UnhoverAction unhover;
    private ClickAction click;
    private boolean isCurrentlyHovered = false;
    private final UUID uuid = UUID.randomUUID();
    private int maxInteractionDistance = 10;
    private boolean cancelInteractEvent = false;
    private int viewRangeInBlocks = 64;
    private Billboard billboard = Billboard.FIXED;
    private int brightnessOverride = -1;
    private int interpolationDelay = 0;
    private int interpolationTransformDuration = 0;
    private int interpolationPosRotateDuration = 0;

    public GenericDisplayElement(Player viewer, Location location) {
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
     * Get the rotation left of the entity
     * @return The left rotation of the entity
     */
    @Override
    public Quaternionf getRotationLeft() {
        return rotationLeft;
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
     * Get the billboard type
     * @return The billboard type
     */
    @Override
    public Billboard getBillboard() {
        return billboard;
    }

    /**
     * Get the brightness override
     * @return The brightness override
     */
    @Override
    public int getBrightnessOverride() {
        return brightnessOverride;
    }

    /**
     * Get the delay before the interpolation starts
     * @return The delay before the interpolation starts
     */
    @Override
    public int getInterpolationDelay() {
        return interpolationDelay;
    }

    /**
     * Get the duration of the interpolation for the transform
     * @return The duration of the interpolation for the transform
     */
    @Override
    public int getInterpolationTransformDuration() {
        return interpolationTransformDuration;
    }

    /**
     * Get the duration of the interpolation for the position and rotation
     * @return The duration of the interpolation for the position and rotation
     */
    @Override
    public int getInterpolationPosRotateDuration() {
        return interpolationPosRotateDuration;
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
     * Set the rotation left of the entity
     * @param rotationLeft The left rotation
     * @return The display element
     */
    @Override
    public DisplayElement setRotationLeft(Quaternionf rotationLeft) {
        this.rotationLeft = rotationLeft;
        return this;
    }

    @Override
    public DisplayElement setRotationLeft(float pitch, float yaw) {
        setRotationLeft(pitchYawToQuaternion(pitch, yaw));
        return this;
    }

    /**
     * Set the rotation right of the entity
     * @param rotationRight The right rotation
     * @return The display element
     */
    @Override
    public DisplayElement setRotationRight(Quaternionf rotationRight) {
        this.rotationRight = rotationRight;
        return this;
    }

    @Override
    public DisplayElement setRotationRight(float pitch, float yaw) {
        setRotationRight(pitchYawToQuaternion(pitch, yaw));
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
     * Set the billboard type
     * @param billboard The billboard type
     * @return The display element
     */
    @Override
    public DisplayElement setBillboard(Billboard billboard) {
        this.billboard = billboard;
        return this;
    }

    /**
     * Set the brightness override
     * @param blockLight The block brightness override
     * @param skyLight The sky brightness override
     * @return The display element
     */
    @Override
    public DisplayElement setBrightnessOverride(int blockLight, int skyLight) {
        return setBrightnessOverride(blockLight << 4 | skyLight << 20);
    }

    /**
     * Set the brightness override
     * @param brightnessOverride The brightness override
     * @return The display element
     */
    @Override
    public DisplayElement setBrightnessOverride(int brightnessOverride) {
        this.brightnessOverride = brightnessOverride;
        return this;
    }

    /**
     * Set the delay before the interpolation starts
     * @param delay The delay before the interpolation starts
     * @return The display element
     */
    @Override
    public DisplayElement setInterpolationDelay(int delay) {
        this.interpolationDelay = delay;
        return this;
    }

    /**
     * Set the duration of the interpolation for the transform
     * @param duration The duration of the interpolation for the transform
     * @return The display element
     */
    @Override
    public DisplayElement setInterpolationTransformDuration(int duration) {
        this.interpolationTransformDuration = duration;
        return this;
    }

    /**
     * Set the duration of the interpolation for the position and rotation
     * @param duration The duration of the interpolation for the position and rotation
     * @return The display element
     */
    @Override
    public DisplayElement setInterpolationPosRotateDuration(int duration) {
        this.interpolationPosRotateDuration = duration;
        return this;
    }

    /**
     * Show the entity to the viewer
     */
    @Override
    public void show() {
        if(!getViewer().isOnline())return;
        this.entityID = Gui3D.getInstance().getDisplayManager().getEntityIDPool(viewer.getUniqueId()).getNext();
        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers().write(0, this.entityID);
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
        ProtocolLibrary.getProtocolManager().sendServerPacket(viewer, destroyEntity);

        Set<GenericDisplayElement> elements = Gui3D.getInstance().getDisplayManager().getPlayerDisplayedElements()
                .computeIfAbsent(getViewer().getUniqueId(), k -> new HashSet<>());
        elements.removeIf(e -> e.getUUID().equals(this.getUUID()));
        DisplayManager.EntityIDPool entityIDPool = Gui3D.getInstance().getDisplayManager().getEntityIDPool(getViewer().getUniqueId());
        try {
            entityIDPool.release(getEntityID());
        } catch (DisplayManager.EntityNotLockedException e) {
            Gui3D.getInstance().getLogger().severe("Entity ID " + getEntityID() + " was not locked or already released (type: " + this.getClass().getSimpleName() + ")");
        }
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

        dataValues.add(new WrappedDataValue(3, WrappedDataSerializers.booleanSerializer, hasCustomName())); // Custom name visible
        if (hasCustomName()) {
            dataValues.add(new WrappedDataValue(2, WrappedDataSerializers.optionalComponentSerializer, // Custom name
                    Optional.of(WrappedChatComponent.fromJson(JSONComponentSerializer.json().serialize(getCustomName())).getHandle())
            ));
        }

        dataValues.add(new WrappedDataValue(8, WrappedDataSerializers.integerSerializer, getInterpolationDelay())); // Interpolation delay

        dataValues.add(new WrappedDataValue(9, WrappedDataSerializers.integerSerializer, getInterpolationTransformDuration())); // Interpolation transform duration

        dataValues.add(new WrappedDataValue(10, WrappedDataSerializers.integerSerializer, getInterpolationPosRotateDuration())); // Interpolation position and rotation duration

        dataValues.add(new WrappedDataValue(11, WrappedDataSerializers.vector3fSerializer, getTranslation())); // Translation

        dataValues.add(new WrappedDataValue(12, WrappedDataSerializers.vector3fSerializer, getScale())); // Scale

        dataValues.add(new WrappedDataValue(13, WrappedDataSerializers.quaternionfSerializer, getRotationLeft())); // Rotation left
        dataValues.add(new WrappedDataValue(14, WrappedDataSerializers.quaternionfSerializer, getRotationRight())); // Rotation right

        dataValues.add(new WrappedDataValue(15, WrappedDataSerializers.byteSerializer, getBillboard().getByteValue())); // Billboard

        dataValues.add(new WrappedDataValue(16, WrappedDataSerializers.integerSerializer, getBrightnessOverride())); // Brightness override

        dataValues.add(new WrappedDataValue(17, WrappedDataSerializers.floatSerializer, (float)(viewRangeInBlocks/64))); // View range (1F = 64 blocks)

        if (isGlowing()) {
            dataValues.add(new WrappedDataValue(22, WrappedDataSerializers.integerSerializer, getGlowColor().asRGB())); // Glowing color
        }

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
    public abstract boolean isLookedAtByViewer(int maxDistance);

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
    private Quaternionf pitchYawToQuaternion(float pitch, float yaw){
        float pitchRad = (float) Math.toRadians(pitch);
        float yawRad = (float) Math.toRadians(yaw);

        float cy = (float) Math.cos(yawRad * 0.5);
        float sy = (float) Math.sin(yawRad * 0.5);
        float cp = (float) Math.cos(pitchRad * 0.5);
        float sp = (float) Math.sin(pitchRad * 0.5);

        Quaternionf quaternion = new Quaternionf();
        quaternion.x = sp * cy;
        quaternion.y = -sy * cp;
        quaternion.z = sy * sp;
        quaternion.w = cp * cy;

        return quaternion;
    }

}

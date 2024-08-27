package dev.bazhard.library.gui3d.element;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import dev.bazhard.library.gui3d.WrappedDataSerializers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextDisplayElement extends GenericDisplayElement{

    public enum Alignment {
        LEFT, CENTER, RIGHT
    }

    private Component component;
    private Color backgroundColor;
    private Byte textOpacity;
    private Integer lineWith = 200;
    private boolean hasShadow = false;
    private boolean canSeeThrough = false;
    private Alignment alignment = Alignment.CENTER;

    public TextDisplayElement(Player viewer, Location location, Component component) {
        super(viewer, location);
        this.component = component;
    }

    public DisplayElement setComponent(Component component) {
        this.component = component;
        return this;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextOpacity(Byte textOpacity) {
        this.textOpacity = textOpacity;
    }

    public void setLineWith(Integer lineWith) {
        this.lineWith = lineWith;
    }

    public void hasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    public void canSeeThrough(boolean canSeeThrough) {
        this.canSeeThrough = canSeeThrough;
    }

    public void alignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Component getComponent() {
        return component;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Byte getTextOpacity() {
        return textOpacity;
    }

    public Integer getLineWith() {
        return lineWith;
    }

    public boolean hasShadow() {
        return hasShadow;
    }

    public boolean canSeeThrough() {
        return canSeeThrough;
    }

    public Alignment alignment() {
        return alignment;
    }

    @Override
    public BoundingBox getBoundingBox() {
        //TODO calculate bounding box based on component size (it's gonna be a mess omg)
        return BoundingBox.of(getLocation(), getScale().x()/2, getScale().y()/2, getScale().z()/2);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TEXT_DISPLAY;
    }

    @Override
    protected List<WrappedDataValue> getAdditionalDataValues() { // https://wiki.vg/Entity_metadata#Text_Display
        List<WrappedDataValue> values = new ArrayList<>();

        values.add(new WrappedDataValue(23,  WrappedDataSerializers.componentSerializer,
                WrappedChatComponent.fromJson(JSONComponentSerializer.json().serialize(component)).getHandle()));

        values.add(new WrappedDataValue(24, WrappedDataSerializers.integerSerializer, Objects.requireNonNullElse(lineWith, 200)));

        if (backgroundColor != null) {
            values.add(new WrappedDataValue(25, WrappedDataSerializers.integerSerializer, backgroundColor.asARGB()));
        }else{
            values.add(new WrappedDataValue(25, WrappedDataSerializers.integerSerializer, 0x40000000));
        }

        values.add(new WrappedDataValue(26, WrappedDataSerializers.byteSerializer, Objects.requireNonNullElseGet(textOpacity, () -> (byte) -1)));

        byte bitmask = calculateBitmask();
        if(bitmask!=0){
            values.add(new WrappedDataValue(27, WrappedDataSerializers.byteSerializer, bitmask));
        }

        return values;
    }

    public byte calculateBitmask() {
        byte bitmask = 0;

        // Check if shadow is enabled
        if (hasShadow) {
            bitmask |= 0x01; // Set bit 0
        }

        // Check if it is see-through
        if (canSeeThrough) {
            bitmask |= 0x02; // Set bit 1
        }

        // Determine alignment
        switch (alignment) {
            case CENTER:
                // CENTER alignment is represented by 0 in the bitmask (no additional bits set)
                break;
            case LEFT:
                bitmask |= 0x08; // Set bit 3 for LEFT
                break;
            case RIGHT:
                bitmask |= 0x10; // Set bit 4 for RIGHT
                break;
        }

        return bitmask;
    }
}

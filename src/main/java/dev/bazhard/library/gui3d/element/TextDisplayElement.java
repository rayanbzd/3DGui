package dev.bazhard.library.gui3d.element;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import dev.bazhard.library.gui3d.utils.BitmapGlyphInfo;
import dev.bazhard.library.gui3d.utils.WrappedDataSerializers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

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
    private Integer linePixelWidth = 200;
    private int numberOflines = 1;
    private boolean hasShadow = false;
    private boolean canSeeThrough = false;
    private Alignment alignment = Alignment.CENTER;

    private float scaleWidth;
    private float scaleHeight;
    private Location center;

    public TextDisplayElement(Player viewer, Location location, Component component) {
        super(viewer, location);
        setComponent(component);
    }

    public DisplayElement setComponent(Component component) {
        this.component = component;
        calculateComponentHeightWidth();
        return this;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextOpacity(Byte textOpacity) {
        this.textOpacity = textOpacity;
    }

    public void setLinePixelWidth(Integer linePixelWidth) {
        this.linePixelWidth = linePixelWidth;
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

    public Integer getLinePixelWidth() {
        return linePixelWidth;
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
    public EntityType getEntityType() {
        return EntityType.TEXT_DISPLAY;
    }

    @Override
    public boolean isLookedAtByViewer(int maxDistance) {//TODO implement billboard and translation calculation
        Location center = this.center;
        float scaledWidth = this.scaleWidth;
        float scaledHeight = this.scaleHeight;
        Quaternionf rotation = getRotation();
        AxisAngle4f axisAngle = new AxisAngle4f();
        axisAngle = rotation.get(axisAngle);
        Vector axis = new Vector(axisAngle.x, axisAngle.y, axisAngle.z);

        float angle = axisAngle.angle;
        Location eyeLocation = getViewer().getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize().multiply(maxDistance);

        Segment segment = new Segment(eyeLocation, direction);
        Vector i = new Vector(1, 0, 0);
        Vector j = new Vector(0, 1, 0);
        Vector k = new Vector(0, 0, 1);
        if(!axis.isZero() && angle!=0){
            i.rotateAroundAxis(axis, angle);
            j.rotateAroundAxis(axis, angle);
            k.rotateAroundAxis(axis, angle);
        }
        Referential referential = new Referential(center.toVector(),
                i,
                j,
                k);

        Quad quad = new Quad(referential, scaledWidth/2, scaledHeight/2);
        return intersectionSegmentQuad(segment, quad, null);
    }

    @Override
    protected List<WrappedDataValue> getAdditionalDataValues() { // https://wiki.vg/Entity_metadata#Text_Display
        List<WrappedDataValue> values = new ArrayList<>();

        values.add(new WrappedDataValue(23,  WrappedDataSerializers.componentSerializer,
                WrappedChatComponent.fromJson(JSONComponentSerializer.json().serialize(component)).getHandle()));

        values.add(new WrappedDataValue(24, WrappedDataSerializers.integerSerializer, Objects.requireNonNullElse(linePixelWidth, 200)));

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

    private byte calculateBitmask() {
        byte bitmask = 0;

        // Check if shadow is enabled
        if (hasShadow) bitmask |= 0x01; // Set bit 0

        // Check if it is see-through
        if (canSeeThrough) bitmask |= 0x02; // Set bit 1

        // Determine alignment
        switch (alignment) {
            case CENTER: // CENTER alignment is represented by 0 in the bitmask (no additional bits set)
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

    private void calculateComponentHeightWidth() { // TODO check to see if this is correct and to simplify it
        int numberOflines = 1;             // Number of lines in the text
        int currentLineWidth = 0;  // The current width of the line in pixels
        int maxLineWidth = 0;      // The maximum width encountered among all lines
        boolean isBold = false;    // Flag to check if the text is bold
        MiniMessage mm = MiniMessage.miniMessage();
        String miniMessageText = mm.serialize(component).replace("\n", "<newline>"); // Replace newline characters with MiniMessage tag
        for (int i = 0; i < miniMessageText.length(); i++) {
            char c = miniMessageText.charAt(i);
            if (c == '<') {
                // Start of a MiniMessage tag
                int endTagIndex = miniMessageText.indexOf('>', i);
                String tagContent = miniMessageText.substring(i + 1, endTagIndex);

                switch (tagContent) {
                    case "newline": // Detect a newline tag
                        maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                        currentLineWidth = 0;
                        numberOflines++;
                        break;
                    case "bold": // Start of bold text
                        isBold = true;
                        break;
                    case "/bold": // End of bold text
                        isBold = false;
                        break;
                }
                i = endTagIndex; // Move the index past the closing tag
            } else {
                // Regular text character
                BitmapGlyphInfo glyphInfo = BitmapGlyphInfo.getBitmapGlyphInfo(c);
                currentLineWidth += glyphInfo.width(isBold); // Add the character width to the current line width
                currentLineWidth += 1; // Add 1 pixel for the spacing between characters
            }
        }

        maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
        this.linePixelWidth = maxLineWidth;
        this.numberOflines = numberOflines;
        this.scaleWidth = linePixelWidth/40F; // 40 pixels = 1 block at 1:1 scale
        this.scaleHeight = numberOflines/4F; // 4 lines = 1 block at 1:1 scale
        this.center = getLocation().clone().add(0, scaleHeight/2F, 0);
    }


    //--------------------//
    //TODO move this to a utility class

    public static boolean intersectionSegmentPlane(Segment segment, Plane plane, Vector interPt) {
        double vectorDotN = segment.vector.dot(plane.normal);
        if (vectorDotN > -1e-3)
            return false;
        Vector v = segment.origin.toVector();
        double t = (plane.distance - v.dot(plane.normal)) / vectorDotN;
        if (t < 0 || t > 1)
            return false;
        v.add(segment.vector.clone().multiply(t));
        if (interPt != null)
            interPt.copy(v);
        return true;
    }
    public static boolean intersectionSegmentQuad(Segment segment, Quad quad, Vector interPt) {
        Vector interPtPlane = new Vector();
        if (!intersectionSegmentPlane(segment, quad.asPlane(), interPtPlane))
            return false;
        Vector interPtLocal = globalToLocalPos(interPtPlane, quad.referential);
        if (Math.abs(interPtLocal.getX()) > quad.extX || Math.abs(interPtLocal.getY()) > quad.extY)
            return false;
        if (interPt != null)
            interPt.copy(interPtPlane);
        return true;
    }
    public static Vector globalToLocalPos(Vector pos, Referential local) {
        Vector v = pos.clone().subtract(local.origin);
        return new Vector(v.dot(local.i), v.dot(local.j), v.dot(local.k));
    }
    public record Segment(Location origin, Vector vector) {}
    public record Plane(Vector normal, double distance) {}
    public record Referential(Vector origin, Vector i, Vector j, Vector k) {}
    public record Quad(Referential referential, double extX, double extY) {
        public Plane asPlane() {
            return new Plane(this.referential.k, this.referential.origin.dot(this.referential.k));
        }
    }


}

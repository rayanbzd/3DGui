package dev.bazhard.library.gui3d.utils;

import dev.bazhard.library.gui3d.element.BlockDisplayElement;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

public class AABBVisualizer {

    private final Set<BlockDisplayElement> elements = new HashSet<>();

    private final Player player;
    private final BoundingBox boundingBox;
    private final float thickness;
    private boolean glowing = false;
    private Color glowingColor = Color.WHITE;
    public AABBVisualizer(Player player, BoundingBox boundingBox, float thickness) {
        this.player = player;
        this.boundingBox = boundingBox;
        this.thickness = thickness;
    }
    public AABBVisualizer(Player player, BoundingBox boundingBox, float thickness, boolean glowing, Color glowingColor) {
        this.player = player;
        this.boundingBox = boundingBox;
        this.thickness = thickness;
        this.glowing = glowing;
        this.glowingColor = glowingColor;
    }

    public void show() {
        Location loc = boundingBox.getMin().toLocation(player.getWorld());
        Material material = Material.WHITE_CONCRETE;
        float widthX = (float) boundingBox.getWidthX();
        float widthZ = (float) boundingBox.getWidthZ();
        float height = (float) boundingBox.getHeight();
        int blockLight = 10;
        int skyLight = 15;
        int brightness = (blockLight << 4) | (skyLight << 20);

        // Low edges
        displayElement(player, loc, new Vector3f(widthX, thickness, thickness), brightness, material, null);
        displayElement(player, loc, new Vector3f(thickness, thickness, widthZ), brightness, material, null);
        displayElement(player, loc, new Vector3f(widthX, thickness, thickness), brightness, material, new Vector3f(0, 0, widthZ - thickness));
        displayElement(player, loc, new Vector3f(thickness, thickness, widthZ), brightness, material, new Vector3f(widthX - thickness, 0, 0));

        // Top edges
        displayElement(player, loc, new Vector3f(widthX, thickness, thickness), brightness, material, new Vector3f(0, height - thickness, 0));
        displayElement(player, loc, new Vector3f(thickness, thickness, widthZ), brightness, material, new Vector3f(0, height - thickness, 0));
        displayElement(player, loc, new Vector3f(widthX, thickness, thickness), brightness, material, new Vector3f(0, height - thickness, widthZ - thickness));
        displayElement(player, loc, new Vector3f(thickness, thickness, widthZ), brightness, material, new Vector3f(widthX - thickness, height - thickness, 0));

        // Side edges
        displayElement(player, loc, new Vector3f(thickness, height, thickness), brightness, material, null);
        displayElement(player, loc, new Vector3f(thickness, height, thickness), brightness, material, new Vector3f(widthX - thickness, 0, 0));
        displayElement(player, loc, new Vector3f(thickness, height, thickness), brightness, material, new Vector3f(0, 0, widthZ - thickness));
        displayElement(player, loc, new Vector3f(thickness, height, thickness), brightness, material, new Vector3f(widthX - thickness, 0, widthZ - thickness));
    }

    public void hide() {
        elements.forEach(BlockDisplayElement::destroy);
        elements.clear();
    }

    private void displayElement(Player p, Location loc, Vector3f scale, int brightness, Material material, Vector3f translation) {
        BlockDisplayElement element = new BlockDisplayElement(p, loc, material);
        element.setViewRangeInBlocks(p.getClientViewDistance()*16);
        element.setBrightnessOverride(brightness);
        element.setScale(scale);
        if (translation != null) {
            element.setTranslation(translation);
        }
        if(glowing && glowingColor != null) {
            element.setGlowing(true);
            element.setGlowColor(glowingColor);
        }
        element.show();
        elements.add(element);
    }

}

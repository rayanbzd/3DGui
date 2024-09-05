package dev.bazhard.library.gui3d.element;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import dev.bazhard.library.gui3d.utils.WrappedDataSerializers;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class ItemDisplayElement extends GenericDisplayElement {

    private ItemStack itemStack;

    public ItemDisplayElement(Player viewer, Location location, ItemStack itemStack) {
        super(viewer, location);
        setItemStack(itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public DisplayElement setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        getPendingUpdates().put(23, new WrappedDataValue(23, WrappedDataSerializers.itemStackSerializer,
                MinecraftReflection.getMinecraftItemStack(getItemStack())));
        return this;
    }

    public BoundingBox getBoundingBox() {
        //TODO implement translation calculation
        return BoundingBox.of(getLocation(), getScale().x()/2, getScale().y()/2, getScale().z()/2);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ITEM_DISPLAY;
    }

    @Override
    public boolean isLookedAtByViewer(int maxDistance) {
        if (!getViewer().isOnline()) return false;

        Location playerLocation = getViewer().getEyeLocation();
        Vector direction = playerLocation.getDirection();
        Vector entityMin = getBoundingBox().getMin();
        Vector entityMax = getBoundingBox().getMax();

        double[] tMin = new double[3];
        double[] tMax = new double[3];
        double[] invDir = {1.0 / direction.getX(), 1.0 / direction.getY(), 1.0 / direction.getZ()};
        double[] playerPos = {playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()};
        double[] min = {entityMin.getX(), entityMin.getY(), entityMin.getZ()};
        double[] max = {entityMax.getX(), entityMax.getY(), entityMax.getZ()};

        for (int i = 0; i < 3; i++) {
            double diffMin = (min[i] - playerPos[i]) * invDir[i];
            double diffMax = (max[i] - playerPos[i]) * invDir[i];

            tMin[i] = Math.min(diffMin, diffMax);
            tMax[i] = Math.max(diffMin, diffMax);
        }

        if (tMin[0] > tMax[1] || tMin[1] > tMax[0]) return false;
        if (tMin[1] > tMin[0]) tMin[0] = tMin[1];
        if (tMax[1] < tMax[0]) tMax[0] = tMax[1];

        if (tMin[0] > tMax[2] || tMin[2] > tMax[0]) return false;
        if (tMin[2] > tMin[0]) tMin[0] = tMin[2];
        if (tMax[2] < tMax[0]) tMax[0] = tMax[2];

        return tMin[0] < maxDistance && tMax[0] > 0;
    }

}

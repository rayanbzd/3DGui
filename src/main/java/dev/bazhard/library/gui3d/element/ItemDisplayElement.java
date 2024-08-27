package dev.bazhard.library.gui3d.element;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import dev.bazhard.library.gui3d.WrappedDataSerializers;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class ItemDisplayElement extends GenericDisplayElement {

    private ItemStack itemStack;

    public ItemDisplayElement(Player viewer, Location location, ItemStack itemStack) {
        super(viewer, location);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public DisplayElement setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @Override
    public BoundingBox getBoundingBox() {
        //TODO improve bounding box calculation
        return BoundingBox.of(getLocation(), getScale().x()/2, getScale().y()/2, getScale().z()/2);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ITEM_DISPLAY;
    }

    @Override
    protected List<WrappedDataValue> getAdditionalDataValues(){ // https://wiki.vg/Entity_metadata#Item_Display
        return List.of(new WrappedDataValue(23, WrappedDataSerializers.itemStackSerializer,
                MinecraftReflection.getMinecraftItemStack(getItemStack())));                                            // ItemStack data
    }

}

package dev.bazhard.library.gui3d.utils;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class WrappedDataSerializers {

    public static WrappedDataWatcher.Serializer booleanSerializer = WrappedDataWatcher.Registry.get(Boolean.class);
    public static WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
    public static WrappedDataWatcher.Serializer vector3fSerializer = WrappedDataWatcher.Registry.get(Vector3f.class);
    public static WrappedDataWatcher.Serializer quaternionfSerializer = WrappedDataWatcher.Registry.get(Quaternionf.class);
    public static WrappedDataWatcher.Serializer floatSerializer = WrappedDataWatcher.Registry.get(Float.class);
    public static WrappedDataWatcher.Serializer integerSerializer = WrappedDataWatcher.Registry.get(Integer.class);
    public static WrappedDataWatcher.Serializer componentSerializer = WrappedDataWatcher.Registry.getChatComponentSerializer(false);
    public static WrappedDataWatcher.Serializer optionalComponentSerializer = WrappedDataWatcher.Registry.getChatComponentSerializer(true);
    public static WrappedDataWatcher.Serializer itemStackSerializer = WrappedDataWatcher.Registry.getItemStackSerializer(false);

}

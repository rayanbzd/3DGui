# Gui3D - Open-Sourced 3D GUI library for spigot/papermc plugins

## ⚠ This project is still in development and is not ready for use in production.

This library is a 3D GUI library for spigot/papermc plugins (currently compatible only with Minecraft 1.21).
It allows you to create 3D GUIs with ease in minecraft without mods or resource packs required.
It is open-sourced and free to use.
You can use it in your projects with credit to the original author.

## Features planned:
- Multiples elements style (item display, block display, text display, slider, background, arrows, etc.)
- Customizable 3D GUIs interface
- Hover events for 3D elements
- Click events for 3D elements
- Scrolling in 3D GUIs with mouse wheel
- Possibility to use custom models for 3D elements (compatibility planned with ItemsAdder and Oraxen)
- 3D GUIs spawn/despawn animations
- 3D GUIs pagination system
- 3D GUIs follow player movement

## 100% packet-based - No entities created server-side
This library is 100% packet-based, meaning that no entities are created server-side.
This allows for a better performance and less lag on the server.
Packets are handled by [ProtocolLib](https://github.com/dmulloy2/ProtocolLib/), which is a required dependency for this library.

## Compilation
Gui3D uses Gradle Kotlin DSL for its build system. If you have it installed, just run `./gradlew build` in the root project folder.

## Using Gui3D in your project
To use this library, first add Gui3D.jar in your plugins folder of your server.
Then, add Gui3D as a dependency of your plugin in your IDE.

Maven:
```xml
<repositories>
    <repository>
        <id>bazhard-dev-repository</id>
        <url>https://repo.bazhard.dev/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>dev.bazhard.library</groupId>
        <artifactId>Gui3D</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Gradle (Kotlin DSL):
```kotlin
repositories {
    maven("https://repo.bazhard.dev/repository/maven-public/")
}

dependencies {
    compileOnly("dev.bazhard.library:Gui3D:1.0.0-SNAPSHOT")
}
```

## License

This project is licensed under the [Creative Commons Attribution 4.0 International (CC BY 4.0)](https://creativecommons.org/licenses/by/4.0/). You are free to use, share, and adapt this project as long as appropriate credit is given to the original author. For more details, please refer to the [LICENSE](./LICENSE) file.

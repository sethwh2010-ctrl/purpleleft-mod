# Purple Left Outliner — Minecraft 1.21.11

This is a small client-side companion for Re:Entity Outliner.

## What it does

Re:Entity Outliner continues to provide the through-wall entity outline.

This add-on filters the vanilla glowing state so outlined entities on the
right side of the camera are suppressed. Your existing 50/50 purple-left
shader supplies the purple screen split.

## Install

Put the compiled `purple-left-outliner-1.0.0.jar` in the same `mods` folder as:

- Fabric Loader for Minecraft 1.21.11
- Fabric API for 1.21.11
- Re:Entity Outliner

Keep your working purple-left shader enabled in Iris.

## Build

Use Java 21 and Gradle/Loom:

    gradle build

The JAR is created in `build/libs/`.

## GitHub Actions

The included workflow builds the JAR automatically on GitHub and uploads it
as an artifact named `purple-left-outliner-jar`.

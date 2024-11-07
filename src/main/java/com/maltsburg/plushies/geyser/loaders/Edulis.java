package com.maltsburg.plushies.geyser.loaders;

import com.maltsburg.plushies.geyser.loaders.food.*;
import com.maltsburg.plushies.geyser.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public class Edulis {

    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static void loadEdulisData(GeyserDefineCustomItemsEvent event, ItemBuilder itemBuilder) {

        File pluginFolder = loadPluginFolder();
        if (pluginFolder == null) {
            log.warning("Edulis folder could not be loaded.");
            return;
        }

        Map<String, Object[]> recipesData = Recipes.load(pluginFolder,"recipes.yml");
        if (!recipesData.isEmpty()) {
            registerItems(event, recipesData, itemBuilder);
        }

        Map<String, Object[]> smokerData = SmokerRecipes.load(pluginFolder, "smoker_recipes.yml");
        if (!smokerData.isEmpty()) {
            registerItems(event, smokerData, itemBuilder);
        }

        Map<String, Object[]> cakeData = CakeSlices.load(pluginFolder, "cakes.yml");
        if (!cakeData.isEmpty()) {
            registerItems(event, cakeData, itemBuilder);
        }

        Map<String, Object[]> mobData = MobDrops.load(pluginFolder, "mob_drops.yml");
        if (!mobData.isEmpty()) {
            registerItems(event, mobData, itemBuilder);
        }

        Map<String, Object[]> ingData = Ingredients.load(pluginFolder, "ingredients.yml");
        if (!ingData.isEmpty()) {
            registerItems(event, ingData, itemBuilder);
        }
    }

    private static File loadPluginFolder() {
        File pluginsDir = new File(Bukkit.getServer().getWorldContainer(), "plugins");

        // Get the plugin folder by name
        File pluginFolder = new File(pluginsDir, "Edulis");

        // Check if the plugin folder exists
        if (!pluginFolder.exists() || !pluginFolder.isDirectory()) {
            return null;
        }
        return pluginFolder;
    }

    private static void registerItems(GeyserDefineCustomItemsEvent event, Map<String, Object[]> data, ItemBuilder itemBuilder) {
        for (Map.Entry<String, Object[]> entry : data.entrySet()) {
            String name = entry.getKey();
            Object[] itemData = entry.getValue();
            String material = (String) itemData[0];
            int customModelData = (int) itemData[1];
            itemBuilder.buildAndRegisterItem(event, customModelData, name, "minecraft:" + material.toLowerCase());
        }
    }
}

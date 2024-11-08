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

        File pluginFolder = new File(Bukkit.getServer().getWorldContainer(), "plugins/Edulis");
        if (!pluginFolder.exists() || !pluginFolder.isDirectory()) {
            log.warning("Edulis folder not found.");
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

        String[] files = {"cakes", "mob_drops", "ingredients"};

        for (String file : files) {
            Map<String, Object[]> data = Items.load(pluginFolder, file);
            if (!data.isEmpty()) {
                registerItems(event, data, itemBuilder);
            }
        }
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

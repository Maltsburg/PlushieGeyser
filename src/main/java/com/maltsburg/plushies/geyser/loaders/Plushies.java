package com.maltsburg.plushies.geyser.loaders;

import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import com.maltsburg.plushies.geyser.utils.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Plushies {

    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static void loadPlushieData(GeyserDefineCustomItemsEvent event, ItemBuilder itemBuilder) {

        // Don't check if Plushie is loaded, we dont need it.
        // Check for folder instead

        File pluginsDir = new File(Bukkit.getServer().getWorldContainer(), "plugins");
        File plushiesFolder = new File(pluginsDir, "Plushies");

        if (!plushiesFolder.exists() || !plushiesFolder.isDirectory()) {
            log.warning("Plushies folder does not exist.");
            return;
        }

        File plushiesFile = new File(plushiesFolder, "plushies.yml");

        // Check if the config file exists
        if (!plushiesFile.exists()) {
            log.warning("plushies.yml does not exist.");
            return;
        }

        Map<String, Integer[]> plushieData = loadFromConfig(plushiesFile);

        if (plushieData != null) {
            registerPlushies(event, plushieData, itemBuilder);
        }
    }

    private static Map<String, Integer[]> loadFromConfig(File plushiesFile) {
        Map<String, Integer[]> plushieData = new HashMap<>();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(plushiesFile);
        ConfigurationSection plushiesSection = config.getConfigurationSection("plushies");

        if (plushiesSection == null) {
            log.warning("No 'plushies' section found in config.");
            return null;
        }

        Set<String> keys = plushiesSection.getKeys(false);

        for (String key : keys) {
            int plushModelData = plushiesSection.getInt(key + ".plush", -1);
            int statueModelData = plushiesSection.getInt(key + ".statue", -1);

            if (plushModelData == -1 || statueModelData == -1) {
                log.warning("Invalid data for plushie '" + key + "'.");
                continue;
            }

            key = key.toLowerCase();
            plushieData.put(key, new Integer[]{plushModelData, statueModelData});
        }

        return plushieData;
    }

    private static void registerPlushies(GeyserDefineCustomItemsEvent event, Map<String, Integer[]> plushieData, ItemBuilder itemBuilder) {
        for (Map.Entry<String, Integer[]> entry : plushieData.entrySet()) {
            String name = entry.getKey();
            Integer[] modelDataArray = entry.getValue();

            for (int i = 0; i < modelDataArray.length; i++) {
                int modelData = modelDataArray[i];
                String itemName = (i == 1) ? name + ".st" : name;
                // Custom item name 'geyser_custom:foxncat' already exists and was registered again! Skipping... blah blah blah
                itemBuilder.buildAndRegisterItem(event, modelData, itemName, "minecraft:totem_of_undying");
            }
        }
    }
}

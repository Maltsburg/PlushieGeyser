package com.maltsburg.plushies.geyser.loaders.food;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Items {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static Map<String, Object[]> load(File folder, String fileName) {
        Map<String, Object[]> fileData = new HashMap<>();

        File file = new File(folder, fileName + ".yml");

        if (!file.exists()) {
            log.warning(fileName + " does not exist in the folder " + folder.getName());
            return fileData;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        fileName = fileName.equals("mob_drops") ? "drops" : fileName;

        ConfigurationSection dropSection = config.getConfigurationSection(fileName);
        if (dropSection == null) {
            log.warning("no config section found in file " + file.getName());
            return fileData;
        }

        Set<String> recipeKeys = dropSection.getKeys(false);

        for (String recipeKey : recipeKeys) {
            ConfigurationSection recipeSection = dropSection.getConfigurationSection(recipeKey);

            if (recipeSection == null) {
                log.warning("No section found for recipe '" + recipeKey + "' in file " + file.getName());
                continue;
            }

            String material = recipeSection.getString("material");
            int customModelData = recipeSection.getInt("customModelData", -1);

            if (material == null || customModelData == -1) {
                log.warning("Missing 'material' or 'customModelData' for recipe '" + recipeKey + "' in file " + file.getName());
                continue;
            }

            fileData.put(recipeKey.toLowerCase(), new Object[]{material, customModelData});
        }
        return fileData;
    }

}

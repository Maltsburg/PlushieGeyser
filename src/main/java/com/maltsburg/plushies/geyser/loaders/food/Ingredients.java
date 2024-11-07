package com.maltsburg.plushies.geyser.loaders.food;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Ingredients {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static Map<String, Object[]> load(File folder, String fileName) {
        Map<String, Object[]> ingData = new HashMap<>();

        File ingFile = new File(folder, fileName);

        if (!ingFile.exists()) {
            log.warning(fileName + " does not exist in the folder " + folder.getName());
            return ingData;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(ingFile);

        ConfigurationSection ingSection = config.getConfigurationSection("ingredients");
        if (ingSection == null) {
            log.warning("No 'recipes' section found in file " + ingFile.getName());
            return ingData;
        }

        Set<String> recipeKeys = ingSection.getKeys(false);

        for (String recipeKey : recipeKeys) {
            ConfigurationSection recipeSection = ingSection.getConfigurationSection(recipeKey);

            if (recipeSection == null) {
                log.warning("No section found for recipe '" + recipeKey + "' in file " + ingFile.getName());
                continue;
            }

            String material = recipeSection.getString("material");
            int customModelData = recipeSection.getInt("customModelData", -1);

            if (material == null || customModelData == -1) {
                log.warning("Missing 'material' or 'customModelData' for recipe '" + recipeKey + "' in file " + ingFile.getName());
                continue;
            }

            ingData.put(recipeKey.toLowerCase(), new Object[]{material, customModelData});
        }
        return ingData;
    }

}

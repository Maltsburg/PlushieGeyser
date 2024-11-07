package com.maltsburg.plushies.geyser.loaders.food;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class SmokerRecipes {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static Map<String, Object[]> load(File folder, String fileName) {
        Map<String, Object[]> smokerData = new HashMap<>();

        File recipeFile = new File(folder, fileName);

        if (!recipeFile.exists()) {
            log.warning(fileName + " does not exist in the folder " + folder.getName());
            return smokerData;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(recipeFile);

        ConfigurationSection recipesSection = config.getConfigurationSection("recipes");
        if (recipesSection == null) {
            log.warning("No 'recipes' section found in file " + recipeFile.getName());
            return smokerData;
        }

        Set<String> recipeKeys = recipesSection.getKeys(false);

        for (String recipeKey : recipeKeys) {
            ConfigurationSection recipeSection = recipesSection.getConfigurationSection(recipeKey);

            if (recipeSection == null) {
                log.warning("No section found for recipe '" + recipeKey + "' in file " + recipeFile.getName());
                continue;
            }

            ConfigurationSection resultSection = recipeSection.getConfigurationSection("result");
            if (resultSection == null) {
                log.warning("No 'result' section found for recipe '" + recipeKey + "' in file " + recipeFile.getName());
                continue;
            }

            String material = resultSection.getString("material");
            int customModelData = resultSection.getInt("customModelData", -1);

            if (material == null || customModelData == -1) {
                log.warning("Missing or invalid 'material' or 'customModelData' for recipe '" + recipeKey + "'.");
                continue;
            }

            smokerData.put(recipeKey.toLowerCase(), new Object[]{material, customModelData});
        }

        return smokerData;
    }

}

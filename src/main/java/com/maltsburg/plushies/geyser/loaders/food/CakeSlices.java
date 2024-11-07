package com.maltsburg.plushies.geyser.loaders.food;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class CakeSlices {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static Map<String, Object[]> load(File folder, String fileName) {
        Map<String, Object[]> cakeData = new HashMap<>();

        File cakeFile = new File(folder, fileName);

        if (!cakeFile.exists()) {
            log.warning(fileName + " does not exist in the folder " + folder.getName());
            return cakeData;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(cakeFile);

        ConfigurationSection cakesSection = config.getConfigurationSection("cakes");
        if (cakesSection == null) {
            log.warning("No 'recipes' section found in file " + cakeFile.getName());
            return cakeData;
        }

        Set<String> recipeKeys = cakesSection.getKeys(false);

        for (String recipeKey : recipeKeys) {
            ConfigurationSection recipeSection = cakesSection.getConfigurationSection(recipeKey);

            if (recipeSection == null) {
                log.warning("No section found for recipe '" + recipeKey + "' in file " + cakeFile.getName());
                continue;
            }

            String material = recipeSection.getString("material");
            int customModelData = recipeSection.getInt("customModelData", -1);

            if (material == null || customModelData == -1) {
                log.warning("Missing 'material' or 'customModelData' for recipe '" + recipeKey + "' in file " + cakeFile.getName());
                continue;
            }

            cakeData.put(recipeKey.toLowerCase(), new Object[]{material, customModelData});
        }
        return cakeData;
    }

}

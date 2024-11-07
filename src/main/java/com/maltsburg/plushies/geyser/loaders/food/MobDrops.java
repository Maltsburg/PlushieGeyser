package com.maltsburg.plushies.geyser.loaders.food;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class MobDrops {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static Map<String, Object[]> load(File folder, String fileName) {
        Map<String, Object[]> mobData = new HashMap<>();

        File mobFile = new File(folder, fileName);

        if (!mobFile.exists()) {
            log.warning(fileName + " does not exist in the folder " + folder.getName());
            return mobData;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(mobFile);

        ConfigurationSection dropSection = config.getConfigurationSection("drops");
        if (dropSection == null) {
            log.warning("No 'recipes' section found in file " + mobFile.getName());
            return mobData;
        }

        Set<String> recipeKeys = dropSection.getKeys(false);

        for (String recipeKey : recipeKeys) {
            ConfigurationSection recipeSection = dropSection.getConfigurationSection(recipeKey);

            if (recipeSection == null) {
                log.warning("No section found for recipe '" + recipeKey + "' in file " + mobFile.getName());
                continue;
            }

            String material = recipeSection.getString("material");
            int customModelData = recipeSection.getInt("customModelData", -1);

            if (material == null || customModelData == -1) {
                log.warning("Missing 'material' or 'customModelData' for recipe '" + recipeKey + "' in file " + mobFile.getName());
                continue;
            }

            mobData.put(recipeKey.toLowerCase(), new Object[]{material, customModelData});
        }
        return mobData;
    }

}

package com.maltsburg.plushies.geyser.loaders.food;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Recipes {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");

    public static Map<String, Object[]> load(File folder, String fileName) {
        Map<String, Object[]> allRecipesData = new HashMap<>();

        File recipeFile = new File(folder, fileName);

        if (!recipeFile.exists()) {
            log.warning(fileName + " does not exist in the folder " + folder.getName());
            return allRecipesData;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(recipeFile);
        List<Map<?, ?>> recipes = config.getMapList("recipes");

        for (Map<?, ?> recipe : recipes) {
            Map<?, ?> resultSection = (Map<?, ?>) recipe.get("result");
            if (resultSection == null) {
                log.warning("No 'result' section found for recipe in file " + fileName);
                continue;
            }

            String material = (String) resultSection.get("material");
            Integer customModelData = (Integer) resultSection.get("customModelData");

            if (material == null || customModelData == null) {
                log.warning("Invalid 'material' or 'customModelData' in result section in file " + fileName);
                continue;
            }

            String recipeName = (String) recipe.get("name");
            if (recipeName == null) {
                log.warning("Missing 'name' for recipe in file " + fileName);
                continue;
            }

            allRecipesData.put(recipeName.toLowerCase(), new Object[]{material, customModelData});
        }

        return allRecipesData;
    }
}

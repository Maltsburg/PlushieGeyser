package com.maltsburg.plushies.geyser;

import com.maltsburg.plushies.geyser.loaders.Edulis;
import com.maltsburg.plushies.geyser.loaders.Plushies;
import com.maltsburg.plushies.geyser.utils.ItemBuilder;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.extension.Extension;

public class PlushiesGeyser implements Extension {

    private final ItemBuilder itemBuilder = new ItemBuilder();

    @Subscribe
    public void onEnable(GeyserDefineCustomItemsEvent event) {
        Plushies.loadPlushieData(event, itemBuilder);
        Edulis.loadEdulisData(event,itemBuilder);
    }
}
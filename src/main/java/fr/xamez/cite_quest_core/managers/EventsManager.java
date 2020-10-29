package fr.xamez.cite_quest_core.managers;

import fr.xamez.cite_quest_core.CiteQuestCore;
import fr.xamez.cite_quest_core.events.JoinEvent;
import fr.xamez.cite_quest_core.events.QuitEvent;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

    public EventsManager(CiteQuestCore instance){

        PluginManager pluginManager = instance.getServer().getPluginManager();

        pluginManager.registerEvents(new JoinEvent(instance), instance);
        pluginManager.registerEvents(new QuitEvent(), instance);
    }


}

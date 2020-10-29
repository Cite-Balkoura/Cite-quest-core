package fr.xamez.cite_quest_core.events;

import fr.xamez.cite_quest_core.CiteQuestCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private final CiteQuestCore instance;

    public JoinEvent(CiteQuestCore instance){
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        instance.getMANAGER().getPlayerManager().loadPlayerQuest(e.getPlayer().getUniqueId());
    }

}

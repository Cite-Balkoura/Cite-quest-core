package fr.xamez.cite_quest_core.managers;

import fr.xamez.cite_quest_core.CiteQuestCore;
import fr.xamez.cite_quest_core.objects.Quest;
import fr.xamez.cite_quest_core.utils.FastInvManager;
import fr.xamez.cite_quest_core.utils.FileUtils;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Manager {

    private final CiteQuestCore citeQuestCore;
    private final SerializationManager serializationManager;
    private final FileUtils fileUtils;
    private final PlayerManager playerManager;
    private QuestManager questManager;
    public static ArrayList<Quest> questList = new ArrayList<>();
    public static Map<UUID, Map<String, Integer>> playerQuests = new HashMap<>();
    public static List<UUID> playerDialogues = new ArrayList<>();

    public Manager(CiteQuestCore instance){
        this.citeQuestCore = instance;

        FastInvManager.register(instance);

        this.fileUtils = new FileUtils(instance);
        this.serializationManager = new SerializationManager();
        questList = Arrays.asList(getSerializationManager().deserialize(getFileUtils().load())).equals(null) ? new ArrayList<>() : new ArrayList<>(Arrays.asList(getSerializationManager().deserialize(getFileUtils().load())));
        this.questManager = new QuestManager(questList, playerQuests);
        this.playerManager = new PlayerManager(this);
    }


    public CiteQuestCore getCiteQuest() {
        return citeQuestCore;
    }

    public final QuestManager getQuestManager() {
        return questManager;
    }

    public final SerializationManager getSerializationManager() {
        return serializationManager;
    }

    public final FileUtils getFileUtils() {
        return fileUtils;
    }

    public final List<Quest> getQuestList() {
        return questList;
    }

    public final PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }
}

package fr.xamez.cite_quest_core.managers;

import fr.milekat.cite_libs.MainLibs;
import fr.xamez.cite_quest_core.enumerations.MessagesEnum;
import fr.xamez.cite_quest_core.enumerations.QuestTypeEnum;
import fr.xamez.cite_quest_core.objects.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerManager {

    private final Manager manager;
    public final int totalSecondaryQuest;

    public PlayerManager(Manager manager){

        this.manager = manager;
        this.totalSecondaryQuest = manager.getQuestManager().getQuestListByType(QuestTypeEnum.SECONDARY).size();
    }

    public void loadPlayerQuest(UUID uuid) {
        Connection connection = MainLibs.sql.getConnection();
        try {
            PreparedStatement q = connection.prepareStatement("SELECT * FROM `balkoura_quest` WHERE `player_id` = " +
                    "(SELECT `player_id` FROM `balkoura_player` WHERE `uuid` = '" + uuid.toString() + "');");
            q.execute();

            if (Manager.playerQuests.containsKey(uuid)){
                Manager.playerQuests.get(uuid).clear();
            }
            Manager.playerQuests.put(uuid, new HashMap<>());

            while (q.getResultSet().next()) {
                if (manager.getQuestManager().getQuestByIdentifier(q.getResultSet().getString("quest_id")).isPresent()){
                    Manager.playerQuests.get(uuid).put(q.getResultSet().getString("quest_id"), q.getResultSet().getInt("step_id"));
                }
            }
            q.close();
        } catch (SQLException throwable) {
            Bukkit.getLogger().warning(MessagesEnum.PREFIX_CONSOLE.getText() + "Erreur lors du load des quêtes de: " + uuid);
            throwable.printStackTrace();
        }
    }

    /**
     *      Save de l'avancement d'une quête pour un joueur
     */
    public static void updatePlayerStep(UUID uuid, String id, int new_step) {
        Connection connection = MainLibs.sql.getConnection();
        try {
            PreparedStatement q = connection.prepareStatement("INSERT INTO `balkoura_quest`" +
                    "(`player_id`, `quest_id`, `step_id`) VALUES (" +
                    "(SELECT `player_id` FROM `balkoura_player` WHERE `uuid` = '" + uuid.toString() + "'),?, ?) " +
                    "ON DUPLICATE KEY UPDATE `step_id` = ?;");
            q.setString(1, id);
            q.setInt(2, new_step);
            q.setInt(3, new_step);
            q.execute();
            q.close();
            Manager.playerQuests.get(uuid).put(id, new_step);
            QuestManager.giveStepReward(Bukkit.getPlayer(uuid), id, new_step - 1);
        } catch (SQLException throwable) {
            Bukkit.getLogger().warning(MessagesEnum.PREFIX_CONSOLE.getText() + "Erreur lors de l'update d'une quête.");
            throwable.printStackTrace();
        }
    }

    public final List<Quest> getPlayerQuests(UUID uuid){
        if (Manager.playerQuests.get(uuid) == null) { return new ArrayList<>(); }
        return Manager.playerQuests.get(uuid).keySet().stream().map(id -> manager.getQuestManager().getQuestByIdentifier(id).get()).collect(Collectors.toList());
    }

    /**
     *      Si le joueur n'a pas la quête, 0 sera retourné
     */
    public final int getPlayerStep(UUID uuid, Quest quest) {
        if (Manager.playerQuests.get(uuid) == null) { return 0; }
        return Manager.playerQuests.get(uuid).getOrDefault(quest.getIdentifier(),0);
    }

    public final int getPlayerProgression(Player p){
        if (Manager.playerQuests.get(p.getUniqueId()) == null) { return 0; }
        int secondaryQuestOfPlayer = (int) this.getPlayerQuests(p.getUniqueId()).stream().filter(q -> q.getQuestType().equals(QuestTypeEnum.SECONDARY) && manager.getQuestManager().isQuestOver(p, q.getIdentifier())).count();
        return this.totalSecondaryQuest == 0 ? 0 : 100 * secondaryQuestOfPlayer / this.totalSecondaryQuest;

    }

    public final List<Quest> getDiscoveredSecondaryQuest(Player p){
        if (Manager.playerQuests.get(p.getUniqueId()) == null) { return new ArrayList<>(); }
        return this.getPlayerQuests(p.getUniqueId()).stream().filter(q -> q.getQuestType().equals(QuestTypeEnum.SECONDARY)).collect(Collectors.toList());
    }

    public final long getDiscoveredCountSecondaryQuest(Player p){
        if (Manager.playerQuests.get(p.getUniqueId()) == null) { return 0; }
        return this.getDiscoveredSecondaryQuest(p).size();
    }


}

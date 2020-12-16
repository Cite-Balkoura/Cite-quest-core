package fr.xamez.cite_quest_core.managers;

import fr.milekat.cite_core.core.utils.QuestPoints;
import fr.xamez.cite_quest_core.enumerations.MessagesEnum;
import fr.xamez.cite_quest_core.enumerations.QuestTypeEnum;
import fr.xamez.cite_quest_core.objects.Quest;
import fr.xamez.cite_quest_core.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class QuestManager {

    private final List<Quest> questList;
    private final Map<UUID, Map<String, Integer>> playerQuests;

    public QuestManager(List<Quest> questList, Map<UUID, Map<String, Integer>> playerQuests){
        this.questList = questList;
        this.playerQuests = playerQuests;
    }

    public final Optional<Quest> getQuestByIdentifier(String identifier){
        return this.questList.stream().filter(q -> q.getIdentifier().equals(identifier)).findFirst();
    }

    public final List<Quest> getQuestListByType(QuestTypeEnum questType){
        return this.questList.stream().filter(q -> q.getQuestType().equals(questType)).collect(Collectors.toList());
    }

    public final long getQuestSizeByType(QuestTypeEnum questType){
        return this.questList.stream().filter(q -> q.getQuestType().equals(questType)).count();
    }

    public final boolean isQuestOver(Player p, String id){
        if (this.playerQuests.get(p.getUniqueId()) == null) { return false; }
        if (!getQuestByIdentifier(id).isPresent()) { return false; }
        return this.playerQuests.get(p.getUniqueId()).get(id) > getQuestByIdentifier(id).get().getSteps().size();
    }

    public static void giveStepReward(Player p, String id, int step) {
        Quest quest = Manager.questList.stream().filter(q -> q.getIdentifier().equals(id)).findFirst().get();
        if (quest.getSteps().size() < step - 1) {
            for (ItemStackUtil itemstack : quest.getSteps().get(step).getRewardItems()) {
                p.getInventory().addItem(itemstack.toItemstack());
            }
            if (quest.getSteps().get(step).getRewardCoins() > 0) {
                try {
                    QuestPoints.addPoint(p.getUniqueId(), quest.getSteps().get(step).getRewardCoins());
                    p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous avez reçu §6" + quest.getSteps().get(step).getRewardCoins() + " points de quête");
                } catch (SQLException throwable) {
                    Bukkit.getLogger().warning("POINTS OF QUEST \"" + quest.getIdentifier() + "\" FOR " + p + " CAN'T BE ADD");
                    throwable.printStackTrace();
                }
            }
        }
    }

}

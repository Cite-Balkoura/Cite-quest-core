package fr.xamez.cite_quest_core.objects;

import fr.xamez.cite_quest_core.utils.ItemStackUtil;

import java.util.List;

public class QuestSteps {

    private final String identifier;
    private final List<ItemStackUtil> questIndices;
    private final List<ItemStackUtil> rewardItems;
    private final List<String> sentences;
    private final int rewardCoins;

    public QuestSteps(String identifier, List<String> sentences, List<ItemStackUtil> questIndices, List<ItemStackUtil> rewardItems, int rewardCoins) {
        this.identifier = identifier;
        this.sentences = sentences;
        this.questIndices = questIndices;
        this.rewardItems = rewardItems;
        this.rewardCoins = rewardCoins;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public List<ItemStackUtil> getQuestIndices() {
        return questIndices;
    }

    public List<ItemStackUtil> getRewardItems() {
        return rewardItems;
    }

    public int getRewardCoins() {
        return rewardCoins;
    }

}

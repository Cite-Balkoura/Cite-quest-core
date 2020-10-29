package fr.xamez.cite_quest_core.objects;

import fr.xamez.cite_quest_core.enumerations.QuestTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class Quest {

    private final String identifier;
    private final String difficulty;
    private final ArrayList<String> description;
    private final List<QuestSteps> steps;
    private final QuestTypeEnum questType;
    private final List<String> begin_sentences;
    private final List<String> end_sentences;
    private final int points;

    public Quest(String identifier, String difficulty, ArrayList<String> description, List<QuestSteps> steps, QuestTypeEnum questType, List<String> begin_sentences, List<String>  end_sentences, int points) {
        this.identifier = identifier;
        this.difficulty = difficulty;
        this.description = description;
        this.steps = steps;
        this.questType = questType;
        this.begin_sentences = begin_sentences;
        this.end_sentences = end_sentences;
        this.points = points;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public final ArrayList<String> getDescription() {
        return description;
    }

    public final List<QuestSteps> getSteps() {
        return steps;
    }

    public final QuestTypeEnum getQuestType() {
        return questType;
    }

    public List<String> getBegin_sentences() {
        return begin_sentences;
    }

    public List<String> getEnd_sentences() {
        return end_sentences;
    }

    public int getPoints() {
        return points;
    }
}

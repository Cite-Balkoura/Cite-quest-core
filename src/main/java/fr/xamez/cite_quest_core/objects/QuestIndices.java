package fr.xamez.cite_quest_core.objects;


import fr.xamez.cite_quest_core.utils.ItemStackUtil;

import java.util.List;

public class QuestIndices {

    private final List<ItemStackUtil> indices;

    public QuestIndices(List<ItemStackUtil> indices) {
        this.indices = indices;
    }

    public List<ItemStackUtil> getIndices() {
        return indices;
    }

}

package fr.xamez.cite_quest_core.managers;

import fr.xamez.cite_quest_core.CiteQuestCore;
import fr.xamez.cite_quest_core.commands.QuestCommand;
import fr.xamez.cite_quest_core.commands.TextComponentCommand;

public class CommandManager {

    public CommandManager(CiteQuestCore instance){

        instance.getCommand("quest").setExecutor(new QuestCommand(instance.getMANAGER()));
        instance.getCommand("tcc").setExecutor(new TextComponentCommand(instance.getMANAGER())); // erreur ici

    }


}
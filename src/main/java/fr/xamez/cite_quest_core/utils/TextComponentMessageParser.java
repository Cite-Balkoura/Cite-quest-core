package fr.xamez.cite_quest_core.utils;

import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.objects.Quest;
import fr.xamez.cite_quest_core.objects.TextComponentMessage;

public class TextComponentMessageParser {

    public static TextComponentMessage parse(Manager manager, String s){
        String[] elements = s.split("\\|");
        return new TextComponentMessage(elements[0], elements[1], Integer.parseInt(elements[2]), elements[3]);
    }


}

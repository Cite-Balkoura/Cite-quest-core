package fr.xamez.cite_quest_core.utils;

import fr.xamez.cite_quest_core.objects.TextComponentMessage;
import java.util.regex.Pattern;

public class TextComponentMessageParser {

    public static TextComponentMessage parse(String s){
        String[] elements = s.split(Pattern.quote("|"));
        return new TextComponentMessage(elements[0], elements[1], Integer.parseInt(elements[2]), elements[3]);
    }


}

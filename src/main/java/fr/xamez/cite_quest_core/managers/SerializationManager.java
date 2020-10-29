package fr.xamez.cite_quest_core.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.xamez.cite_quest_core.objects.Quest;

public class SerializationManager {

    private final Gson gson;

    public SerializationManager(){

        this.gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(Quest[] quest){
        return this.gson.toJson(quest);
    }

    public Quest[] deserialize(String json){
        return json.isEmpty() ? new Quest[0] : gson.fromJson(json, Quest[].class);
    }

    public Gson getGson() {
        return gson;
    }

}

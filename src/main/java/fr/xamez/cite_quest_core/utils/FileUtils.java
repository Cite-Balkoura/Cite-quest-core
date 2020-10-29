package fr.xamez.cite_quest_core.utils;

import fr.xamez.cite_quest_core.CiteQuestCore;

import java.io.*;

public final class FileUtils {

    private final CiteQuestCore instance;
    private final File file;

    public FileUtils(CiteQuestCore instance){
        this.instance = instance;
        this.file = getQuestFile();
    }

    public final void save(String text){
        final FileWriter fw;

        try {

            fw = new FileWriter(this.file);
            fw.write(text);
            fw.flush();
            fw.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public final String load(){

        try {

            final BufferedReader reader = new BufferedReader(new FileReader(this.file));
            final StringBuilder text = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                text.append(line);
            }
            return text.toString();


        } catch (IOException e){
            e.printStackTrace();
        }

        return "";
    }

    public final void createFile() {
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }
        File file = new File(instance.getDataFolder(), "Quests.json");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final File getQuestFile(){
        return new File(instance.getDataFolder(), "Quests.json");
    }

}

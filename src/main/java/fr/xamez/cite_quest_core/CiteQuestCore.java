package fr.xamez.cite_quest_core;
import fr.xamez.cite_quest_core.managers.*;
import fr.xamez.cite_quest_core.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class CiteQuestCore extends JavaPlugin {

    private Manager MANAGER;
    private static CiteQuestCore instance;
    public FileUtils fileUtils;


    @Override
    public void onEnable() {

        instance = this;

        this.fileUtils = new FileUtils(this);
        this.fileUtils.createFile(); // create the json file if not exist

        this.MANAGER = new Manager(this); // register all Managers

        new CommandManager(this); // register all commands
        new EventsManager(this); // register all Events
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public final Manager getMANAGER() {
        return MANAGER;
    }

    public static CiteQuestCore getInstance() {
        return instance;
    }
}

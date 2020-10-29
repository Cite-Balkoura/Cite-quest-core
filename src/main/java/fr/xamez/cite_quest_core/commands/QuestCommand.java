package fr.xamez.cite_quest_core.commands;

import fr.xamez.cite_quest_core.enumerations.MessagesEnum;
import fr.xamez.cite_quest_core.gui.QuestGUI;
import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.managers.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestCommand implements CommandExecutor {

    private final Manager manager;

    public QuestCommand(Manager manager){
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("quest") || label.equalsIgnoreCase("q") || label.equalsIgnoreCase("quête")){
            if (sender instanceof Player){
                Player p = (Player) sender;

                if (args.length == 0){
                    new QuestGUI(manager, p, 1);
                } else if (args.length == 1){
                    if (args[0].equalsIgnoreCase("reload")){
                        manager.getFileUtils().createFile();
                        Manager.questList = new ArrayList<>(Arrays.asList(manager.getSerializationManager().deserialize(manager.getFileUtils().load())));
                        manager.setQuestManager(new QuestManager(manager.getQuestList(), Manager.playerQuests));
                        for (Player player : Bukkit.getOnlinePlayers()){
                            manager.getPlayerManager().loadPlayerQuest(player.getUniqueId());
                        }
                        sender.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§eRechargement des quêtes effectué !");
                    } else {
                        sendHelp(sender);
                        return false;
                    }

                } else {
                    sendHelp(sender);
                    return false;
                }
            } else { return false; }
        } else {
            sendHelp(sender);
        }
        return false;
    }

    private void sendHelp(CommandSender p){
        // TODO put correct help messages
        p.sendMessage("");
        p.sendMessage("§6/quest §7Ouvre le menu principale des quêtes");
        p.sendMessage("§6/quest reload §7Reload le json ansi que l'avancement des joueurs");
        p.sendMessage("");
    }
}

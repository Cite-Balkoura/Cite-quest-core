package fr.xamez.cite_quest_core.commands;

import fr.xamez.cite_quest_core.enumerations.MessagesEnum;
import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.managers.PlayerManager;
import fr.xamez.cite_quest_core.objects.TextComponentMessage;
import fr.xamez.cite_quest_core.utils.TextComponentMessageParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextComponentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("TCC")){ // TCC for TextComponentCommand
            if (sender instanceof Player){
                Player p = (Player) sender;
                StringBuilder builder = new StringBuilder();
                for (String s : args){
                    builder.append(s).append(" ");
                }
                TextComponentMessage parsed = TextComponentMessageParser.parse(builder.toString());
                if (!parsed.getKey().equals("E$R79!^#5")){
                    p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§cErreur, commande inconnu");
                    return false;
                }
                Integer id = Manager.playerQuests.get(p.getUniqueId()).get(parsed.getIdentifier());
                if (parsed.getExtra().equals("accept ")) {
                    if (id == null || id == 0) {
                        PlayerManager.updatePlayerStep(p.getUniqueId(), parsed.getIdentifier(), parsed.getStep()); // put this quest as a discovery
                        p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous venez d'accepter la quête !");
                        return true;
                    }
                } else if (parsed.getExtra().equals("refuse ")) {
                    if (id == null || id == 0) {
                        p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§cVous venez de refuser la quête");
                        return false;
                    }
                } else {
                    p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§cErreur inattendu ! Veillez conntacter un membre du staff.");
                    return false;
                }
            }
        }
        return false;
    }
}

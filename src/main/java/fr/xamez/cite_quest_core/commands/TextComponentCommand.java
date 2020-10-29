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

    private final Manager manager;

    public TextComponentCommand(Manager manager){
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("TCC")){ // TCC for TextComponentCommand
            if (sender instanceof Player){
                Player p = (Player) sender;
                TextComponentMessage parsed = TextComponentMessageParser.parse(this.manager, args[0]);
                if (!parsed.getKey().equals("E$R79!^#5")){
                    p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§cErreur, commande inconnu");
                    return false;
                }
                if (parsed.getExtra().equals("accept")) {
                    if (Manager.playerQuests.get(p.getUniqueId()).get(parsed.getIdentifier()) == null) {
                        PlayerManager.updatePlayerStep(p.getUniqueId(), parsed.getIdentifier(), parsed.getStep()); // put this quest as a discovery
                        p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous venez d'accepter la quête !");
                        return true;
                    }
                } else if (parsed.getExtra().equals("refuse")) {
                    if (Manager.playerQuests.get(p.getUniqueId()).get(parsed.getIdentifier()) == null) {
                        p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§cVous venez de refuser la quête");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}

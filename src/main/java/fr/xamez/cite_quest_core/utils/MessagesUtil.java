package fr.xamez.cite_quest_core.utils;

import fr.milekat.cite_core.core.utils.QuestPoints;
import fr.xamez.cite_quest_core.CiteQuestCore;
import fr.xamez.cite_quest_core.enumerations.MessagesEnum;
import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.objects.Quest;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessagesUtil {

    public static void sendRPMessage(String text, Player p, NPC npc){
        String s = text.replaceAll("%npc%", npc.getName());
        s = s.replaceAll("%player%", "§b" + p.getName());
        Manager.playerDialogues.remove(p.getUniqueId());
        p.sendMessage(s);
    }

    public static void sendBaseComponent(Quest quest, Player p){
        BaseComponent baseComponent = new TextComponent();
        baseComponent.addExtra(new TextComponent("        "));
        TextComponent accept = new TextComponent("§a[Accepter]");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tcc E$R79!^#5|" + quest.getIdentifier() + "|" + 1 + "|" + "accept")); // key|questID|stepID|extra
        baseComponent.addExtra(accept);
        baseComponent.addExtra(new TextComponent("    "));
        TextComponent refuse = new TextComponent("§c[Passer mon chemin]");
        refuse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tcc E$R79!^#5|" + quest.getIdentifier() + "|" + 1 + "|" + "refuse")); // key|questID|stepID|extra
        baseComponent.addExtra(refuse);
        p.spigot().sendMessage(baseComponent);
    }

    public static void sendDialogues(CiteQuestCore citeQuestCore, Quest quest, int step, Player p, NPC npc){

        new BukkitRunnable(){
            @Override
            public void run() {
                List<String> dialogues;
                if (step == 0){ dialogues = quest.getBegin_sentences(); }
                else { dialogues = quest.getSteps().get(step - 1).getSentences(); }
                for (String sentence : dialogues) {
                    try {
                        MessagesUtil.sendRPMessage(sentence, p, npc);
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException ignored) {}
                }
                if (step == 0){
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MessagesUtil.sendBaseComponent(quest, p);
                }
                Manager.playerDialogues.remove(p.getUniqueId());
            }
        }.runTaskAsynchronously(citeQuestCore);
    }

    public static void sendEndMessage(Player p, Quest quest, NPC npc){
        for (String sentence : quest.getEnd_sentences()){
            sendRPMessage(sentence, p, npc);
        }
        try {
            QuestPoints.addPoint(p.getUniqueId(), quest.getPoints());
            p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous avez reçu §6" + quest.getPoints() + " points");
        } catch (SQLException throwable) {
            Bukkit.getLogger().warning("POINTS OF QUEST \"" + quest.getIdentifier() + "\" FOR " + p + " CAN'T BE ADD");
            throwable.printStackTrace();
        }
    }

    public static String getColorForDifficulty(String difficulty){
        switch (difficulty.toLowerCase()){
            case "facile":
                return "§a";
            case "normal":
                return "§6";
            case "difficile":
                return "§c";
            case "extrême":
                return "§4";
            default:
                return "§f";
        }
    }

}

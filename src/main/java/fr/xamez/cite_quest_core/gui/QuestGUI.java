package fr.xamez.cite_quest_core.gui;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.ItemBuilder;
import fr.xamez.cite_quest_core.enumerations.QuestTypeEnum;
import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.objects.Quest;
import fr.xamez.cite_quest_core.utils.FastInv;
import fr.xamez.cite_quest_core.utils.MessagesUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class QuestGUI extends FastInv {

    public QuestGUI(Manager manager, Player p, int page) {

        super(6*9, "§6» §eListe des quêtes");

        // DECORATION

        ItemStack lime_decoration = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").toItemStack();
        setItems(0, 8, lime_decoration);
        ItemStack green_decoration = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").toItemStack();
        setItems(18, 26, green_decoration);

        // TODO put the correct % for the progression of each player
        ItemStack player_skull = new ItemBuilder(Material.PLAYER_HEAD).setName("§6» §e" + p.getName()).setLore(
                Arrays.asList("", "§7➥ §fProgression: §a" + manager.getPlayerManager().getPlayerProgression(p) + "%",
                              "§7➥ §fQuêtes découverte: §b" + manager.getPlayerManager().getDiscoveredCountSecondaryQuest(p) + "§7/§b" + manager.getPlayerManager().totalSecondaryQuest,
                              "§7➥ §fPoints quêtes: §d" + MainCore.profilHashMap.get(p.getUniqueId()).getPoints_quest())
                ).setSkullOwner(p).toItemStack();
        setItem(4, player_skull);


        // DISPLAY MAIN QUESTS
        int i = 9;
        for (Quest quest : manager.getQuestManager().getQuestListByType(QuestTypeEnum.MAIN)) {
            Material material = manager.getQuestManager().isQuestOver(p, quest.getIdentifier()) ? Material.WRITTEN_BOOK : Material.WRITABLE_BOOK;
            ArrayList<String> lore = new ArrayList<String>(quest.getDescription()) {};
            lore.add(0, quest.getDifficulty());
            ItemStack itemStack = new ItemBuilder(material).setName("§6» §e" + quest.getIdentifier()).setLore(lore).toItemStack();
            setItem(i, itemStack, inventoryClickEvent -> {
                new QuestDetailsGUI(manager, p, quest);
            });
            i++;
        }
        displayPage(manager, p, 1);
        open(p);
    }


    private void displayPage(Manager manager, Player p, int page){

        setItems(3*9, 6*9 - 1, new ItemStack(Material.AIR));

        final int size = manager.getPlayerManager().getPlayerQuests(p.getUniqueId()).size();
        int index = 0;
        for (Quest quest : manager.getPlayerManager().getPlayerQuests(p.getUniqueId())) {
            if (index >= page * (3*9) - 3*9 && index < page * 3*9) {
                Material material = manager.getQuestManager().isQuestOver(p, quest.getIdentifier()) ? Material.WRITTEN_BOOK : Material.BOOK;
                ArrayList<String> lore = new ArrayList<String>(quest.getDescription()) {};
                lore.add(0, "§bDifficulté: " + MessagesUtil.getColorForDifficulty(quest.getDifficulty()) + quest.getDifficulty());
                lore.add(1, "§7§nDescription: ");
                ItemStack itemStack = new ItemBuilder(material).setName("§8» §6" +  quest.getIdentifier()).setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();

                itemStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                setItem(index + 3*9 - (3*9 * (page - 1)), itemStack, inventoryClickEvent -> {
                    new QuestDetailsGUI(manager, p, quest);
                });
            }
            index++;
        }

        ItemStack green_decoration = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").toItemStack();
        setItems(18, 26, green_decoration);

        if (Math.ceil(size / (3 * 9 - 9)) > page) {
            ItemStack next_page = new ItemBuilder(Material.ARROW).setName("§8» §7Page suivante").setLore(Collections.singletonList("§8➥ §7Aller à la page " + (page + 1))).toItemStack();
            setItem(3*9 - 1, next_page, inventoryClickEvent -> { displayPage(manager, p, page+1); });
        }
        if (page > 1){
            ItemStack previous_page = new ItemBuilder(Material.ARROW).setName("§8» §7Page précédente").setLore(Collections.singletonList("§8➥ §7Retour à la page " + (page - 1))).toItemStack();
            setItem(2*9, previous_page, inventoryClickEvent -> { displayPage(manager, p, page-1); });
        }

    }


    @Override
    protected void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }
}

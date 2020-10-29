package fr.xamez.cite_quest_core.gui;

import fr.milekat.cite_libs.utils_tools.ItemBuilder;
import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.objects.Quest;
import fr.xamez.cite_quest_core.objects.QuestSteps;
import fr.xamez.cite_quest_core.utils.FastInv;
import fr.xamez.cite_quest_core.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestDetailsGUI extends FastInv {

    public QuestDetailsGUI(Manager manager, Player p, Quest quest) {

        super(54, "§6» §eQuête §f" + quest.getIdentifier());

        ItemStack arrow_back = new ItemBuilder(Material.ARROW).setName("§6» §7Retour à la liste des quêtes").toItemStack();
        setItem(0, arrow_back, inventoryClickEvent -> { new QuestGUI(manager, p, 1); });
        ItemStack lime_decoration = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").toItemStack();
        setItems(1, 9, lime_decoration);
        ItemStack green_decoration = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").toItemStack();
        setItems(18, 26, green_decoration);
        Material material = manager.getQuestManager().isQuestOver(p, quest.getIdentifier()) ? Material.WRITTEN_BOOK : Material.WRITABLE_BOOK;
        int current_step_of_player = Manager.playerQuests.get(p.getUniqueId()).get(quest.getIdentifier());
        if (current_step_of_player > quest.getSteps().size()) { current_step_of_player = quest.getSteps().size(); }
        int progression = 100 * current_step_of_player / quest.getSteps().size();
        ItemBuilder quest_item = new ItemBuilder(material).setName("§6» §eQuête " + quest.getIdentifier()).
            setLore(Arrays.asList("", "§7➥ §fProgression: §a" + progression + "%")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
        if (current_step_of_player > quest.getSteps().size()){ quest_item.addLoreLine("§7➥ §fNuméro de l'étapes en cours: " + "§b" + current_step_of_player); }
        setItem(4, quest_item.toItemStack());

        // DISPLAY ALL STEPS
        int slot_steps = 27;
        for (QuestSteps step : quest.getSteps()) {


            if (current_step_of_player >= slot_steps - 26){ // if step has been discovered

                ItemStack stepItemstack = new ItemBuilder(Material.FLOWER_BANNER_PATTERN).setName("§6● §e" + step.getIdentifier()).setLore(getStepDescription(step)).toItemStack();
                setItem(slot_steps, stepItemstack, inventoryClickEvent -> {
                    setItems(9, 17, new ItemStack(Material.AIR));
                    int current_slot = inventoryClickEvent.getRawSlot() - 27;
                    int slot_indices = 9;
                    for (ItemStackUtil itemStackUtil : quest.getSteps().get(current_slot).getQuestIndices()) {
                        setItem(slot_indices, itemStackUtil.toItemstack());
                        slot_indices++;
                    }

                });
            } else {
                ItemStack stepItemstack = new ItemBuilder(Material.FLOWER_BANNER_PATTERN).setName("§6● §cInconnu").setLore(Collections.singletonList("§cÉtape non découverte.")).toItemStack();
                setItem(slot_steps, stepItemstack);
            }

            slot_steps++;
        }

        // DISPLAY ALL INDICES ACCORDING TO THE STEP

        setItems(9, 17, new ItemStack(Material.AIR));
        int slot_indices = 9;
        int sfq = Manager.playerQuests.get(p.getUniqueId()).get(quest.getIdentifier()) > quest.getSteps().size() ? quest.getSteps().size() : Manager.playerQuests.get(p.getUniqueId()).get(quest.getIdentifier());

        for (ItemStackUtil itemStackUtil : quest.getSteps().get(sfq - 1).getQuestIndices()) {
            setItem(slot_indices, itemStackUtil.toItemstack());
            slot_indices++;
        }
        open(p);
    }

    @Override
    protected void onClick(InventoryClickEvent e) { e.setCancelled(true); }


    private List<String> getStepDescription(QuestSteps step){

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§7➥ §fCoins: " + step.getRewardCoins());

        if (step.getRewardItems().size() == 0) { return lore; }

        lore.add("§7➥ §fRécompense");
        for (ItemStackUtil rewardItem : step.getRewardItems()) {
            if (rewardItem.getMaterial() != Material.AIR){
                String material = rewardItem.getMaterial().toString().toLowerCase().replaceAll("_", " ");
                lore.add("  §7- §b" + rewardItem.getQuantity() + "x " + material);
            }
        }

        return lore;
    }


}

package manager;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.quietemperor.Main;
import utils.ChatUtil;
import utils.TimeUtil;

public class RozdajManager {
    private final Main plugin;
    private BossBar bossBar;
    private long endTime;
    private String currentType;
    private int taskId = -1;
    private int amount;

    public RozdajManager(Main plugin) {
        this.plugin = plugin;
    }

    public void startRozdaj(String type, long duration, int amount) {
        stopRozdaj();

        currentType = type;
        this.amount = amount;
        endTime = System.currentTimeMillis() + duration;

        bossBar = Bukkit.createBossBar(ChatUtil.fixColor("&b⌚ &8⁎ &fᴡʏꜱᴛᴀʀᴛᴏᴡᴀʟᴏ &b&lʀᴏᴢᴅᴀɴɪᴇ &fɴᴀ: &b")  + type.toUpperCase(), BarColor.BLUE, BarStyle.SEGMENTED_10);
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }

        taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long remainingTime = endTime - System.currentTimeMillis();
            if (remainingTime <= 0) {
                executeCommand(type, amount);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(ChatUtil.fixColor("&9&lROZDANO!"), ChatUtil.fixColor("&7Pomyślnie rozdano: &b" + type.toUpperCase()), 10, 70, 20);
                }
                stopRozdaj();
                return;
            }

            double progress = (double) remainingTime / duration;
            bossBar.setProgress(progress);
            bossBar.setTitle(ChatUtil.fixColor("&b⌚ &8⁎ &fᴡʏꜱᴛᴀʀᴛᴏᴡᴀʟᴏ &b&lʀᴏᴢᴅᴀɴɪᴇ &fɴᴀ: &b&l" + amount + "x" + " &3" + type.toUpperCase() + " &8&l| &fᴘᴏᴢᴏꜱᴛᴀʟᴏ: &3" + TimeUtil.formatTime(remainingTime / 1000)));
        }, 0L, 20L).getTaskId();
    }

    public void stopRozdaj() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }

        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }

        currentType = null;
        endTime = 0;
        amount = 0;
    }

    private void executeCommand(String type, int amount) {
        String command = getCommandForType(type, amount);
        if (command != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    private String getCommandForType(String type, int amount) {
        switch (type.toLowerCase()) {
            case "luckyblock":
                return "crates giveAll luckyblock " + amount;
            case "starbox":
                return "crates giveAll starbox " + amount;
            case "epicbox":
                return "crates giveAll epicbox " + amount;
            case "minebox":
                return "crates giveAll minebox " + amount;
            case "lodowa":
                return "crates giveAll lodowa " + amount;
            default:
                return null;
        }
    }

    public boolean isRozdajActive() {
        return bossBar != null;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public static boolean isValidType(String type) {
        return type.equalsIgnoreCase("luckyblock") ||
                type.equalsIgnoreCase("starbox") ||
                type.equalsIgnoreCase("epicbox") ||
                type.equalsIgnoreCase("minebox") ||
                type.equalsIgnoreCase("lodowa");
    }
}

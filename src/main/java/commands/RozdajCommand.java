package commands;

import manager.RozdajManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.quietemperor.Main;
import utils.ChatUtil;
import utils.TimeUtil;

public class RozdajCommand implements CommandExecutor {

    private static RozdajManager rozdajManager;
    private Main plugin;

    public RozdajCommand(Main plugin) {
        this.plugin = plugin;
        this.rozdajManager = plugin.getRozdajManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rozdaj")) {
            if (!(sender instanceof Player) || sender.hasPermission("digmc.commands.rozdaj")) {
                if (args.length != 3) {
                    sender.sendMessage(ChatUtil.fixColor("&2☺ &aPoprawne użycie: &2/rozdaj <typ> <czas> <ilość>"));
                    return true;
                }

                String type = args[0].toLowerCase();
                String time = args[1];
                String amountArg = args[2];

                if (!RozdajManager.isValidType(type)) {
                    sender.sendMessage(ChatUtil.fixColor("&4☹ &cNieprawidłowy typ! &4Użyj: &cluckyblock/epicbox/starbox/minebox/lodowa"));
                    return true;
                }

                int amount;
                try {
                    amount = Integer.parseInt(amountArg);
                    if (amount <= 0) {
                        sender.sendMessage(ChatUtil.fixColor("&4☹ &cIlość musi być liczbą dodatnią!"));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatUtil.fixColor("&4☹ &cIlość musi być liczbą!"));
                    return true;
                }

                try {
                    long duration = TimeUtil.parseTimeToSeconds(time) * 1000L;
                    rozdajManager.startRozdaj(type, duration, amount);
                    sender.sendMessage(ChatUtil.fixColor("&2☺ &aRozdanie na §2" + amount + "x " + "&#08FB8E" + type + " &azostało rozpoczęte na czas: &2" + time + "&a!"));
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatUtil.fixColor("&4☹ &cNieprawidłowy format czasu! &4Użyj: &c15m/30m/1h/2h etc."));
                }
            } else {
                sender.sendMessage(ChatUtil.fixColor("&4☹ &#E72929Nie posiadasz permisji do tej komendy! &4(&#E72929digmc.commands.rozdaj&4)"));
            }
            return true;
        }
        return false;
    }
}

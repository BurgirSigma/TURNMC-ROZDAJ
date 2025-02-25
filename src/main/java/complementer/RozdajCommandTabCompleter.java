package complementer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RozdajCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> types = Arrays.asList("luckyblock", "starbox", "epicbox", "minebox", "lodowa");
            for (String type : types) {
                if (type.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(type);
                }
            }
        } else if (args.length == 2) {
            List<String> times = Arrays.asList("15m", "30m", "1h", "2h", "4h", "8h", "12h", "1d");
            for (String time : times) {
                if (time.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(time);
                }
            }
        } else if (args.length == 3) {
            completions.add("1");
            completions.add("5");
            completions.add("10");
            completions.add("20");
        }




        return completions;
    }
}

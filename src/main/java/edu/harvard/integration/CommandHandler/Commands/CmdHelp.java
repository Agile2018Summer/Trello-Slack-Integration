package edu.harvard.integration.CommandHandler.Commands;

import edu.harvard.integration.CommandHandler.CommandSender;
import edu.harvard.integration.Integrator;
import edu.harvard.integration.Utils;
import edu.harvard.integration.api.Cmd;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of {@link Cmd} to list all the commands the {@link CommandSender} has access to.
 */
public class CmdHelp implements Cmd {
    @Override
    public void performCommand(@Nonnull String[] args, @Nonnull CommandSender sender) {
        if (args.length > 0 && !Utils.legalInt(args[0])) {
            sender.sendMessage("Error: You must enter a valid help page.");
            return;
        }
        int page = 0;
        if (args.length > 0)
            page = Integer.parseInt(args[0]);
        if (args.length == 0 || page <= 0)
            page = 1;
        int rounder = 0;
        List<String> helpList = Integrator.getCommandHandler().getHelpList(sender);
        if (helpList.size() % 10 != 0)
            rounder = 1;
        int totalPages = helpList.size() / 10 + rounder;
        if (page > totalPages) {
            sender.sendMessage("Error: Input a number from 1 to " + totalPages);
            return;
        }
        StringBuilder m = new StringBuilder(" ---- Help -- Page " + page + " / " + totalPages + " ---- \n");
        page -= 1;
        int end = page * 10 + 9;
        List<String> curPage = helpList.subList(page * 10, end > helpList.size() ? helpList.size() : end);
        for (String msg : curPage)
            m.append(msg).append('\n');
        if (page + 1 < totalPages)
            m.append("Type !help ").append(page + 2).append(" to read the next page.\n");
        sender.sendMessage(m.toString());
    }

    @Override
    @Nonnull
    public String helpDoc() {
        return "View the help messages on <page>.";
    }

    @Override
    @Nonnull
    public String getUsage() {
        return "!help <page>";
    }

    @Override
    @Nonnull
    public String getName() {
        return "Help";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("commands");
    }
}
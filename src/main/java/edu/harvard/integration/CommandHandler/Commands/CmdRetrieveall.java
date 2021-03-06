package edu.harvard.integration.CommandHandler.Commands;

import edu.harvard.integration.CommandHandler.CommandSender;
import edu.harvard.integration.Trello.BacklogItem;
import edu.harvard.integration.Trello.Commons;
import edu.harvard.integration.Trello.TrelloIntegration;
import edu.harvard.integration.api.Cmd;

import javax.annotation.Nonnull;
import java.util.List;

public class CmdRetrieveall implements Cmd {
    @Override
    public void performCommand(@Nonnull String[] args, @Nonnull CommandSender sender) {
        if(args.length < 1){
            sender.sendMessage("There should be the name of a board. Check usage for details.");
            return;
        }
        String boardName = Commons.concatArr(args);
        boardName = boardName.replace("@@", "");
        try{
            List<BacklogItem> pbis =
                    TrelloIntegration.getAllPBIs(boardName, Commons.getTrelloToken(), Commons.getTrelloKey());
            StringBuilder output = new StringBuilder();
            for(BacklogItem pbi : pbis) {
                output.append(pbi.toSimpleString());
            }
            sender.sendMessage(output.toString());
        }
        catch (Exception e){
            sender.sendMessage("Unfortunately you don't have access to a board with that name.");
        }
    }

    @Nonnull
    @Override
    public String helpDoc() {
        return "Return all cards in the given board.";
    }

    @Nonnull
    @Override
    public String getUsage() {
        return "!getAll @@<board_name>";
    }

    @Nonnull
    @Override
    public String getName() {
        return "getAll";
    }
}

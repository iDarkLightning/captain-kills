package io.github.iDarkLightning.captainKills.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.iDarkLightning.captainKills.helpers.PlayerEntityHelper;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;


public class CaptainKillQuery {

    private static int captainKillResult(ServerCommandSource source, PlayerEntity player) throws CommandSyntaxException {
        int captainKillCount = ((PlayerEntityHelper) player).getCaptainKillCount();
        String playerName = player.getDisplayName().getString();

        LiteralText feedbackMessage = new LiteralText(playerName + " has killed " + captainKillCount + " pillager captains");
        source.sendFeedback(feedbackMessage, false);

        return 0;
    }

    private static int resetCaptainKillResult(ServerCommandSource source, PlayerEntity player) throws CommandSyntaxException{
        ((PlayerEntityHelper) player).resetCaptainKill();

        String playerName = player.getDisplayName().getString();
        source.sendFeedback(new LiteralText("Captain kill count for " + playerName + " has been reset"), false);
        return 0;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("killcaptain").
                        then(CommandManager.argument("player", EntityArgumentType.players()).
                                then(CommandManager.literal("query")
                                        .executes(c -> captainKillResult(c.getSource(), EntityArgumentType.getPlayer(c, "player"))))
                                .then(CommandManager.literal("reset")
                                        .executes(c -> resetCaptainKillResult(c.getSource(), EntityArgumentType.getPlayer(c, "player"))))));
    }
}

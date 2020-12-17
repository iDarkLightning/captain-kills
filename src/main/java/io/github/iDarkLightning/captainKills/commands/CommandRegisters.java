package io.github.iDarkLightning.captainKills.commands;

import com.mojang.brigadier.CommandDispatcher;
import io.github.iDarkLightning.captainKills.commands.CaptainKillQuery;
import net.minecraft.server.command.ServerCommandSource;

public class CommandRegisters {

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        CaptainKillQuery.register(dispatcher);
    }
}

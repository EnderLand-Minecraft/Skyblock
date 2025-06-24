package com.creepyx.template.bukkit.command;

import com.creepyx.template.bukkit.util.ComponentUtil;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.TextArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SimpleCommand {

	public SimpleCommand() {
		treeCommand.register();
		treeCommand2.register();
	}

	private static final CommandTree treeCommand = new CommandTree("template")
			.executes((sender, args) -> {
				sender.sendMessage("You has executed the command /template!");
				sender.sendMessage(String.join(", ", args.rawArgs()));
			}, ExecutorType.ALL)
			.then(new TextArgument("text")
					.executes((sender, args) -> {
						sender.sendMessage("You has executed the command /template text !");
						sender.sendMessage(String.join(", ", args.rawArgs()));
					})
			);

	private static final CommandTree treeCommand2 = new CommandTree("gm")
			.executes((sender, args) -> {
				sender.sendMessage(ComponentUtil.asComponent("[&6CreepyX&r] please use /gm <0-4>"));
			}, ExecutorType.PLAYER)


			.then(new IntegerArgument("gamemode", 0, 3)
					.executes((sender, args) -> {
						GameMode mode = null;

						switch ((int) args.getOrDefault(0, 0)) {
							case 0 -> mode = GameMode.SURVIVAL;
							case 1 -> mode = GameMode.CREATIVE;
							case 2 -> mode = GameMode.ADVENTURE;
							case 3 -> mode = GameMode.SPECTATOR;
						}

						if (mode == null) {
							return;
						}
						sender.sendMessage(ComponentUtil.asComponent("[&6CreepyX&r] Du hast deinen gamemode zu").append(Component.translatable(mode.translationKey())).append(ComponentUtil.asComponent(" gewechselt !")));
						((Player) sender).setGameMode(mode);
					}).replaceSuggestions(ArgumentSuggestions.strings("0", "1", "2", "3"))
			);

}

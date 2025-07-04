package de.enderland.skyblock.util.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public abstract class PaginatedMenu extends Menu {

	private int page = 0;


	private int maxItemsPerPage = 27;

	private int index = 10;

	public PaginatedMenu(Player player) {
		super(player);
	}

}

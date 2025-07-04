package de.enderland.skyblock.util;

import lombok.Getter;

@Getter
public enum IslandOptions {

	ALLOW_VISITS,
	ALLOW_BLOCK_INTERACTION,
	ALLOW_PVP,
	ALLOW_ITEM_DROP,
	ALLOW_ITEM_COLLECT,
	ALLOW_BLOCK_EXPLODE;

	final String lowerCaseName = name().toLowerCase();

}

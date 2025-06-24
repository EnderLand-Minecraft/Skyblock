package com.creepyx.template.bukkit.util;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

public class RegionUtil {


	private boolean isInside(int x1, int y1, int z1, int x2, int y2, int z2) {

		Region cuboidRegion = new CuboidRegion(BlockVector3.at(x1, y1, z1), BlockVector3.at(x2, y2, z2));
		return cuboidRegion.contains(BlockVector3.at(x1, y1, z2));
	}
}

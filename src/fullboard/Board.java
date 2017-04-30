package fullboard;

import static java.lang.System.*;

import java.util.*;

public class Board {

	ArrayList<Map> stringMaps = new ArrayList<Map>();


	// Convert input stream into ArrayList of maps.
	public void importMaps(String read) {
		String[] split = read.split("map");
		for (int x = 1; x < split.length; x += 2)
			stringMaps.add(new Map(split[x].substring(0, split[x].length() - 3).trim()));
	}


	// Solve each map. Print "Complete" when done.
	public void solveAllMaps() {
		int totalMoves = 0;
		for (Map map : stringMaps)
			totalMoves += map.solveMap();
		out.println("Complete");
		// out.println("in " + totalMoves + " moves");
	}

}

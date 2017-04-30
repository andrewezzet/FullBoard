package fullboard;

import static java.lang.System.*;

import java.util.*;

public class Map {
	MapNode original;

	int totalMoveCount, lowestMoves = 100000;

	double avgOrphanMoves, t1, time;

	ArrayList<int[]> startPoints = new ArrayList<int[]>();

	ArrayList<MapNode> solutions = new ArrayList<MapNode>();


	// Takes map as a string and stores it as 2D integer array.
	// Saves all possible starting points(open spaces) in ArrayList.
	public Map(String map) {


		String[] lines = map.split("\\r\\n");
		int[][] array = new int[lines.length][lines[0].length()];
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[0].length; x++) {
				if (lines[y].charAt(x) == ' ') {
					startPoints.add(new int[] { x, y });
					array[x][y] = 0;
				} else
					array[x][y] = 1;
			}
		}
		original = new MapNode(array, 1, 1, 0, 1);


	}


	// Solve the original map and prints solutions with lowest move-counts in
	// format req'd by tester.
	public int solveMap() {
		time = 0;
		totalMoveCount = 0;
		MapNode map;
		for (int[] sp : startPoints) {
			map = new MapNode(copyMap(original.arrayMap), sp[0], sp[1], 0, 1);
			map.arrayMap[map.x][map.y] = 5;
			tryAllMoves(map);


		}

		out.println("map");

		if (solutions.isEmpty())
			out.println("No solution\n" + original.toString() + "endmap");
		else {
			Collections.sort(solutions);

			int mc = solutions.get(0).moveCount;
			for (MapNode solution : solutions)
				if (solution.moveCount < mc)
					mc = solution.moveCount;
			out.println(mc + " moves");

			for (MapNode solution : solutions)
				if (solution.moveCount == mc)
					out.println("solution\n" + solution.toString() + "endsolution");
			out.println("endmap");
		}

		return totalMoveCount;
	}


	// Recursively try all moves from node and its child boards.
	// If board is full, add node to ArrayList of solutions.
	private void tryAllMoves(MapNode node) {
		node.children[0] = (move(node, -1, 0, true));
		node.children[1] = (move(node, 1, 0, true));
		node.children[2] = (move(node, 0, -1, false));
		node.children[3] = (move(node, 0, 1, false));
		for (MapNode child : node.children) {
			if (child != null && child.moveCount < lowestMoves) {
				if (child.visited == startPoints.size()) {
					child.arrayMap[child.x][child.y] = 9;
					solutions.add(child);
				} else if (!splitMap(child))
					if (child.arrayMap.length > 20 && child.moveCount == 10) {
						if (contiguousSpaceCount(child) == startPoints.size() - child.visited)
							tryAllMoves(child);
					} else
						tryAllMoves(child);

			}
		}
	}


	// Check all contiguous spaces from open space adjacent to child's cursor.
	private int contiguousSpaceCount(MapNode child) {
		int x = child.x, y = child.y;

		if (child.arrayMap[x - 1][y] == 0)
			x -= 1;
		else if (child.arrayMap[x + 1][y] == 0)
			x += 1;
		else if (child.arrayMap[x][y - 1] == 0)
			y -= 1;
		else
			y += 1;

		return checkAllSpaces(copyMap(child.arrayMap), x, y, 0);
	}


	// Recursively count all open spaces that can be reached from initial x/y coordinates.
	private int checkAllSpaces(int[][] arrayMap, int x, int y, int count) {
		if (x < 1 || y < 1 || x > arrayMap[0].length || y > arrayMap[0].length || arrayMap[x][y] != 0)
			return count;
		arrayMap[x][y] = 9;
		count++;
		if (arrayMap[x - 1][y] == 0)
			count = checkAllSpaces(arrayMap, x - 1, y, count);
		if (arrayMap[x + 1][y] == 0)
			count = checkAllSpaces(arrayMap, x + 1, y, count);
		if (arrayMap[x][y - 1] == 0)
			count = checkAllSpaces(arrayMap, x, y - 1, count);
		if (arrayMap[x][y + 1] == 0)
			count = checkAllSpaces(arrayMap, x, y + 1, count);

		return count;

	}


	// Return true if current row/column is full and both adjacent are not.
	private boolean splitMap(MapNode node) {
		if ((node.lastMoveHorizontal) && rowFull(node, 0) && !rowFull(node, -1) && !rowFull(node, 1))
			return true;
		else
			return ((!node.lastMoveHorizontal) && colFull(node, 0) && !colFull(node, -1) && !colFull(node, 1));
	}


	// Return true if specified row (cursor, offset by i) is full.
	private boolean rowFull(MapNode node, int offset) {
		int filled = 0;

		for (int x = 1; x < node.arrayMap[0].length - 1; x++)
			if (node.arrayMap[x][node.y + offset] > 0)
				filled++;

		return (filled == node.arrayMap[0].length - 2);
	}


	// Return true if specified column (cursor, offset by i) is full.
	private boolean colFull(MapNode node, int offset) {
		int filled = 0;

		for (int y = 1; y < node.arrayMap.length - 1; y++)
			if (node.arrayMap[node.x + offset][y] > 0)
				filled++;

		return (filled == node.arrayMap.length - 2);
	}


	// Returns the a new mapNode after a move is made from given node.
	// Returns null if move can't be made.
	private MapNode move(MapNode mapNode, int chX, int chY, boolean horizontal) {

		if (mapNode.arrayMap[mapNode.x + chX][mapNode.y + chY] != 0)
			return null;

		totalMoveCount++;

		MapNode newNode = new MapNode(copyMap(mapNode.arrayMap), mapNode.x, mapNode.y, mapNode.moveCount,
				mapNode.visited);

		// While there is space in direction of move, move to next spot.
		while (newNode.arrayMap[newNode.x + chX][newNode.y + chY] == 0) {
			newNode.arrayMap[newNode.x + chX][newNode.y + chY] = 5 + 2 * (chX) + chY;
			newNode.x += chX;
			newNode.y += chY;
			newNode.visited++;
		}

		newNode.lastMoveHorizontal = horizontal;
		newNode.moveCount++;

		return newNode;

	}


	// Returns a clone of a 2D integer array.
	private int[][] copyMap(int[][] src) {

		int[][] newMap = new int[src.length][];

		for (int i = 0; i < src.length; i++)
			newMap[i] = src[i].clone();
		return newMap;
	}

}

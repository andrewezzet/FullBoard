package fullboard;

public class MapNode implements Comparable<MapNode> {
	int[][] arrayMap;

	int moveCount, visited, x, y;

	MapNode[] children = new MapNode[4];

	boolean lastMoveHorizontal;


	// Constructor which sets all node fields except children.
	public MapNode(int[][] arrayMap, int x, int y, int moveCount, int visited) {
		this.arrayMap = arrayMap;
		this.x = x;
		this.y = y;
		this.moveCount = moveCount;
		this.visited = visited;
	}


	@Override
	// Returns comparison of maps by string value.
	public int compareTo(MapNode node) {
		return (toString().compareTo(node.toString()));
	}


	@Override
	// Returns the map as a string (converted from 2D integer array).
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < arrayMap.length; y++) {
			for (int x = 0; x < arrayMap[0].length; x++)
				sb.append(paintPixel(arrayMap[x][y]));
			sb.append("\n");
		}
		return sb.toString();

	}


	// Converts numeric code for a cell to string output format.
	private String paintPixel(int i) {
		String pixel = "e";
		switch (i) {
		case 0:
			pixel = " ";
			break;
		case 1:
			pixel = "\u2593";
			break;
		case 3:
			pixel = "\u2190";
			break;
		case 4:
			pixel = "\u2191";
			break;
		case 6:
			pixel = "\u2193";
			break;
		case 7:
			pixel = "\u2192";
			break;
		case 5:
			pixel = "S";
			break;
		case 9:
			pixel = "F";
		}

		return (pixel);
	}

}

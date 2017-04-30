package fullboard;

import static java.lang.System.*;
import static org.apache.commons.io.FileUtils.*;

import java.io.*;
import java.util.*;

public class Main {

	static String read;


	public static void main(String[] args) {
		double[] times = new double[10];
		double avgTime = 0.0;
		read = null;
		Scanner scan = new Scanner(in);
		try {
			read = readFileToString(new File(scan.nextLine()));
		} catch (IOException e) {
			out.println("File not found.\nComplete");
		}
		scan.close();

		// for (int x = 0; x < times.length; x++) {
		//
		// double timeBefore = currentTimeMillis();
		// if (read != null) {
		// Board board = new Board();
		// board.importMaps(read);
		// board.solveAllMaps();
		// }
		// double timeAfter = currentTimeMillis();
		// times[x] = (timeAfter - timeBefore) / 1000;
		//
		// }
		//
		// for (double time : times) {
		// avgTime += time;
		// out.println(time);
		// }
		//
		// out.println("Avg time: " + avgTime / times.length + " seconds");


		// double timeBefore = currentTimeMillis();
		if (read != null) {
			Board board = new Board();
			board.importMaps(read);
			board.solveAllMaps();
		}
		// double timeAfter = currentTimeMillis();
		// out.println("Took " + (timeAfter - timeBefore) / 1000 + " seconds.");

	}

}

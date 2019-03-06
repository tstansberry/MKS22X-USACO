import java.util.*;
import java.io.*;
public class  USACO {
  public static int bronze(String filename) {
    try {
      int r = -1;
      int c = -1;
      int e = -1;
      int n = -1;

      File data = new File(filename);
      Scanner info = new Scanner(data);

      if (info.hasNextLine()) {
        String line = info.nextLine();
        String[] vars = line.split(" ");
        r = Integer.parseInt(vars[0]);
        c = Integer.parseInt(vars[1]);
        e = Integer.parseInt(vars[2]);
        n = Integer.parseInt(vars[3]);
      }

      int[][] elevations = new int[r][c];
      for (int x = 0; x < r; x ++) {
        String line = info.nextLine();
        String[] vars = line.split(" ");
        for (int y = 0; y < c; y ++) {
          elevations[x][y] = Integer.parseInt(vars[y]);
        }
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found... make sure file name is correct.");
    }
    return -1;
  }
}

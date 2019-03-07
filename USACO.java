import java.util.*;
import java.io.*;
public class  USACO {
  public static void main(String[] args) {
    System.out.println(bronze("makelake.1.in"));
  }

  public static int bronze(String filename) {
    int r = -1;
    int c = -1;
    int e = -1;
    int n = -1;
    int[][] elevations;
    int[][] moves;

    try {
      File data = new File(filename);
      Scanner info = new Scanner(data);

      //Reading the 4 variables
      String line = info.nextLine();
      String[] vars = line.split(" ");
      r = Integer.parseInt(vars[0]);
      c = Integer.parseInt(vars[1]);
      e = Integer.parseInt(vars[2]);
      n = Integer.parseInt(vars[3]);


      //Reading the grid
      elevations = new int[r][c];
      for (int x = 0; x < r; x ++) {
        line = info.nextLine();
        vars = line.split(" ");
        for (int y = 0; y < c; y ++) {
          elevations[x][y] = Integer.parseInt(vars[y]);
        }
      }

      //Reading the digging instructions
      moves = new int[n][3];
      for (int x = 0; x < n; x ++) {
        line = info.nextLine();
        vars = line.split(" ");
        for (int y = 0; y < 3; y ++) {
          moves[x][y] = Integer.parseInt(vars[y]);
        }
      }

      //Calling the Digs
      executeDigs(moves, elevations);

      //Calculating the total volume and returning
      return findVolume(elevations, e);
    }
    catch (FileNotFoundException t) {
      System.out.println("File not found... make sure file name is correct.");
    }

    return -1; //Dummy val
  }

  private static void executeDigs(int[][] directions, int[][] elevations) {
    for (int[] x: directions) {
      x[0] --;
      x[1] --;
      dig(x[0], x[1], x[2], elevations);
    }
  }

  private static void dig(int r, int c, int depth, int[][] elevations) {
    int highest = findHighest(elevations, r, c);
    for (int x = 0; x < depth; x ++) {
      for (int y = r; y < r + 3; y ++) {
        for (int z = c; z < c + 3; z ++) {
          if (elevations[y][z] >= highest) elevations[y][z] --;
        }
      }
      highest --;
    }
  }

  private static int findHighest(int[][] elevations, int r, int c) {
    int output = 0;
    for (int x = r; x < r + 3; x ++) {
      for (int y = c; y < c + 3; y ++) {
        if (output < elevations[r][c]) output = elevations[r][c];
      }
    }
    return output;
  }

  private static int findVolume(int[][] elevations, int height) {
    //Finding the total depth
    int totalDepth = 0;
    for (int x[]: elevations) {
      for (int y: x) {
        if (y < height) totalDepth += height - y;
      }
    }
    return totalDepth * 72 * 72;
  }

  private static void printArr(int[][] arr) {
    String output = "";
    for (int x[]: arr) {
      for (int y: x) {
        output += y + " ";
      }
      output += "\n";
    }
    System.out.println(output);
  }
}

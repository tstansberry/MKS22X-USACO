import java.util.*;
import java.io.*;
public class  USACO {
  public static void main(String[] args) throws FileNotFoundException{
    System.out.println(silver("ctravel.2.in"));
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
      x[0] = x[0] - 1;
      x[1] = x[1] - 1;
      dig(x[0], x[1], x[2], elevations);
    }
  }

  private static void dig(int r, int c, int depth, int[][] elevations) {
    int highest = findHighest(elevations, r, c);
    for (int x = 0; x < depth; x ++) {
      for (int y = r; y < r + 3; y ++) {
        for (int z = c; z < c + 3; z ++) {
          if (elevations[y][z] == highest) elevations[y][z] --;
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

  //PROBLEM 2
  public static int silver(String filename) throws FileNotFoundException{
    int[][] field;

    int[] specs = genSilverNums(filename);
    int rows = specs[0];
    int cols = specs[1];
    int time = specs[2];

    int[] coords = genSilverCoords(filename, rows);
    int r1 = coords[0]-1;
    int c1 = coords[1]-1;
    int r2 = coords[2]-1;
    int c2 = coords[3]-1;

    field = genSilverBoard(filename, rows, cols);

    field[r1][c1] = 1;

    for (int t = 1; t <= time; t++) {
      for (int i = 0; i < rows; i++) {
        for (int j = (t+i+r1+c1) % 2; j < cols; j+=2) {
          if (field[i][j] >= 0) {
            if (i > 0 && field[i-1][j] >= 0) {
              field[i][j] += field[i-1][j];
            }
            if (i < rows - 1 && field[i+1][j] >= 0) {
              field[i][j] += field[i+1][j];
            }
            if (j > 0 && field[i][j-1] >= 0) {
              field[i][j] += field[i][j-1];
            }
            if (j < cols - 1 && field[i][j+1] >= 0) {
              field[i][j] += field[i][j+1];
            }
          }
        }
      }
      for (int x = 0; x < rows; x ++) {
        for (int y = (t + x + r1 + c1 + 1) % 2; y < cols; y += 2) {
          if (field[x][y] >= 0) field[x][y] = 0;
        }
      }
    }
    return field[r2][c2];
  }

  private static int[] genSilverNums(String filename) throws FileNotFoundException {
    Scanner data = new Scanner(new File(filename));
    String dimensions = data.nextLine();
    int index = dimensions.indexOf(' ');
    int rows = Integer.parseInt(dimensions.substring(0, index));
    dimensions = dimensions.substring(index+1);
    index = dimensions.indexOf(' ');
    int cols = Integer.parseInt(dimensions.substring(0, index));
    int time = Integer.parseInt(dimensions.substring(index+1));
    return new int[] {rows, cols, time};
  }

  private static int[] genSilverCoords(String filename, int r) throws FileNotFoundException {
    Scanner in = new Scanner(new File(filename));
    for (int x = 0; x <= r; x ++) {
      in.nextLine();
    }
    String line = in.nextLine();
    int index = line.indexOf(' ');
    int r1 = Integer.parseInt(line.substring(0, index));
    line = line.substring(index+1);
    index = line.indexOf(' ');
    int c1 = Integer.parseInt(line.substring(0, index));
    line = line.substring(index+1);
    index = line.indexOf(' ');
    int r2 = Integer.parseInt(line.substring(0, index));
    int c2 = Integer.parseInt(line.substring(index+1));
    return new int[] {r1, c1, r2, c2};
  }

  private static int[][] genSilverBoard(String filename, int r, int c) throws FileNotFoundException {
    int[][] ans =  new int[r][c];
    Scanner in = new Scanner(new File(filename));
    in.nextLine();
    for (int i = 0; i < r; i++) {
      String line = in.nextLine();
      for (int j = 0; j < c; j++) {
        switch (line.charAt(j)) {
          case '.': ans[i][j] = 0;
          break;
          case '*': ans[i][j] = -1;
          break;
          default: throw new IllegalStateException("illegal character");
        }
      }
    }
    return ans;
  }
}

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

//Maze Assignment
//Author: Jake McGinn
//Date: Apr 23, 2017
//Class: CS163
//Email: jrmcginn@rams.colostate.edu
public class Maze implements IMaze {

	public static void main(String[] args) {
		Maze bah = new Maze();
		bah.readFile("mazeGrader2.txt");
		System.out.println(Arrays.toString(bah.findStart(bah.readFile("mazeGrader2.txt"))));
		System.out.println(bah.findPath(bah.readFile("mazeGrader2.txt"), bah.findStart(bah.readFile("mazeGrader2.txt"))));
		System.out.println(bah.printMaze(bah.readFile("mazeGrader2.txt")));
	}

	@Override
	public char[][] readFile(String filename) {
		/* Precondition - filename set to file containing map of the maze.
		 * Postcondition - two dimensional char array holding a map of the maze
		 * Postcondition - returns null if file not found
		 */
		try {
			Scanner scan = new Scanner(new File(filename));
			int[] ints = {scan.nextInt(),scan.nextInt()};
			scan.nextLine();
			char[][] maze = new char [ints[0]][ints[1]];
			for (int i = 0; i < maze.length; i++) {
				 String line = scan.nextLine();
				 maze[i] = line.toCharArray();
			}
			scan.close();
			return maze;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int[] findStart(char[][] maze) {
		int[] start = new int[2];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if (maze[i][j] == 'S') {
					start[0] = i;
					start[1] = j;
					return start;
				}
			}
		}
		return null;
		/* Precondition - maze array initialized to a valid maze
		 * Postcondition - array containing row, column of location of S 
		 * 		returned.  Ex. if S is in maze[1][2]. the return 
		 * 		array is {1, 2}
		 * Postcondition - returns null if no S found
		 */
	}
	private String recPath(char[][] maze, int r, int c) {
    /*
     * Precondition - maze array initialized to a valid maze
     * Postcondition - returns the path from the location r,c to the goal
     * Postcondition - '.' set from locatbion r,c in the maze to the goal
     * Requirement - Implemented as a recursive method that finds a path
     *               from position (row,col) to the goal position,
     *               marked by 'G'
     */
		String path = "";
		if (r < 0 || c < 0 || r >=maze.length || c >= maze[r].length)
			return path += "";
		if (maze[r][c] == '#' || maze[r][c] == '.')
				return path += "";
		if (maze[r][c] == 'G')
			return path+="G";
		maze[r][c] = '.';
		
		path = recPath(maze,r-1,c);
		if (path.length() > 0)
			return "U" + path;
		path = recPath(maze,r,c+1);
		if (path.length() > 0)
			return "R" + path;
		path = recPath(maze,r+1,c);
		if (path.length() > 0)
			return "D" + path;
		path = recPath(maze,r,c-1);
		if (path.length() > 0)
			return "L" + path;
		
		maze[r][c] = ' ';
			return path += "";
}

	@Override
	public String findPath(char[][] maze, int[] startPosition) {
		/* Precondition - maze array initialize to a valid maze
		 * Precondition - StartPosition contains row, column of location of S 
		 * 		Ex. if S is in maze[1][2], the startPosition 
		 * 		 array is {1, 2}
		 * Postcondition - returns a String composed of the appropriate
		 *     characters from  'U', 'R', 'D', 'L', and the final 'G', 
		 *     indicating the solution path.
		 * Postcondition - successful path marked with '.' characters in maze
		 * 		 array from 'S' to the final 'G', indicating the 
		 * 		 solution path.
		 */
		return recPath(maze, startPosition[0], startPosition[1]);
	}

	@Override
	public String printMaze(char[][] maze) {
		/*
		 * Postcondition - returns a String representation of the map character array
		 * 	(complete with end of line characters).
		 */
		StringBuilder sb = new StringBuilder();
        for(int x=0; x<maze.length; x++){
            for(int y=0; y<maze[0].length; y++)
                sb.append(maze[x][y]);
            sb.append("\n");
        }
        return sb.toString();
	}

}

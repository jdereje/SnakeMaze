import java.util.Random;
import java.util.Scanner;

// Names: Jonathan Dereje
// x500s: derej009

public class MyMaze {
    //Initalizing the variables required
    Cell[][] maze;
    private int startRow;
    private int endRow;
    private int rows;
    private int cols;

    //Creating the constructor to intialize the entire maze, creating the rows required
    public MyMaze(int rows, int cols, int startRow, int endRow) {
        maze = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell();
                if (i == rows - 1 && j == cols - 1) {
                    maze[i][j].setRight(false);
                }
            }
        }
        this.startRow = startRow;
        this.endRow = endRow;
    }
    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter your maze size: ");
        //System.out.println("rows:    "  + "cols:   " + "startRow:   " + "endRow:  ");
        //String input = myScanner.nextLine();
        MyMaze newMaze = new MyMaze(rows, cols, startRow, endRow);
        Stack1Gen<int[]> stack = new Stack1Gen<>();
        stack.push(new int[]{startRow, 0});
        newMaze.maze[startRow][0].setVisited(true);
        //Initalizing the stack within the makeMaze method
        int[] temp = new int[5];
        int[] temp1 = new int[5];
        //Creating temp variables for bounds
        stack.push(temp1);
        newMaze.maze[0][0].setVisited(true);
        while (!stack.isEmpty()) {
            temp = stack.top();
            //Creating seperate variable to check if it has been visited
            boolean isVisited = true;
            //checks to make sure not out of bounds
            if (temp[0] - 1 >= 0) {
                if (!newMaze.maze[temp[0] - 1][temp[1]].getVisited()) {
                    isVisited = false;
                }
            }
            //checks to make sure not out of bounds
            if (temp[0] + 1 <= newMaze.maze.length - 1) {
                if (!newMaze.maze[temp[0] + 1][temp[1]].getVisited()) {
                    isVisited = false;
                }
            }
            //checks to make sure not out of bounds
            if (temp[1] - 1 >= 0) {
                if (!newMaze.maze[temp[0]][temp[1] - 1].getVisited()) {
                    isVisited = false;
                }
            }
            //checks to make sure not out of bounds
            if (temp[1] + 1 <= newMaze.maze[0].length - 1) {
                if (!newMaze.maze[temp[0]][temp[1] + 1].getVisited()) {
                    isVisited = false;
                }
            }
            //creating a seperate break variable
            boolean endloop = false;
            if (isVisited) {
                stack.pop();
                endloop = true;
            }
            while (!isVisited) {
                if (endloop) //breaks loop
                    break;
                int newTemp = (int) (Math.random() * 4);
                if (newTemp == 0) {
                    //checks to make sure not out of bounds
                    if ((temp[0] - 1) < 0 || newMaze.maze[temp[0] - 1][temp[1]].getVisited()) {
                        newTemp = (int) (Math.random() * 4);
                    } else {
                        newMaze.maze[temp[0] - 1][temp[1]].setBottom(false);
                        newMaze.maze[temp[0] - 1][temp[1]].setVisited(true);
                        temp[0]--;
                        temp1 = new int[2];
                        stack.push(temp1);
                        isVisited = true;
                    }
                } else if (newTemp == 1) {
                    //checks to make sure not out of bounds
                    if ((temp[0] + 1) > newMaze.maze.length - 1 || newMaze.maze[temp[0] + 1][temp[1]].getVisited()) {
                        newTemp = (int) (Math.random() * 4);
                    } else {
                        newMaze.maze[temp[0]][temp[1]].setBottom(false);
                        newMaze.maze[temp[0] + 1][temp[1]].setVisited(true);
                        temp[0]++;
                        temp1 = new int[2];
                        stack.push(temp1);
                        isVisited = true;
                    }
                } else if (newTemp == 2) {
                    //checks to make sure not out of bounds
                    if ((temp[1] - 1) < 0 || newMaze.maze[temp[0]][temp[1] - 1].getVisited()) {
                        newTemp = (int) (Math.random() * 4);
                    } else {
                        newMaze.maze[temp[0]][temp[1] - 1].setRight(false);
                        newMaze.maze[temp[0]][temp[1] - 1].setVisited(true);
                        temp[1]--;
                        temp1 = new int[2];
                        stack.push(temp1);
                        isVisited = true;
                    }
                } else {
                    //checks to make sure not out of bounds
                    if ((temp[1] + 1) > newMaze.maze[0].length - 1 || newMaze.maze[temp[0]][temp[1] + 1].getVisited()) {
                        newTemp = (int) (Math.random() * 4);
                    } else {
                        newMaze.maze[temp[0]][temp[1]].setRight(false);
                        newMaze.maze[temp[0]][temp[1] + 1].setVisited(true);
                        temp[1]++;
                        temp1 = new int[2];
                        temp1[0] = temp[0];
                        temp1[1] = temp[1];
                        stack.push(temp1);
                        isVisited = true;
                    }
                }
            }
        }
        for (int i = 0; i < newMaze.maze.length; i++) {
            for (int j = 0; j < newMaze.maze[0].length; j++) {
                newMaze.maze[i][j].setVisited(false);

            }
        }
        return newMaze;
    }
    /* TODO: Print a representation of the maze to the terminal */
    //Creating print maze to actually print the maze
    public void printMaze(boolean path) {
        for (int i = 0; i < (maze.length * 2) + 1; i++) {
            String print = ("");
            if (i == 1) {
                print += (" ");
            }
            else
                print += ("|");
            for (int j = 0; j < maze[0].length; j++) {
                if (i == 0 || i == maze.length * 2) {
                    print += ("---|");
                } else {
                    if (i % 2 == 1) {
                        if (maze[i / 2][j].getVisited()) {
                            print += (" * ");
                        } else {
                            print +=("   ");
                        }
                        if (maze[i / 2][j].getRight())
                            print += ("|");
                        else
                            print += (" ");
                    } else {
                        if (maze[i / 2 - 1][j].getBottom())
                            print += ("---|");
                        else
                            print += ("   |");
                    }
                }
            }
            System.out.println(print);
        }
    }
    /* TODO: Solve the maze using the algorithm found in the writeup. */
    //Solving the maze using queues
    public void solveMaze() {
        Q1Gen<int[]> queue = new Q1Gen<>();
        queue.add(new int[]{startRow,0});
        //creating temp variables to help with finding bounds
        int[] current = new int[2];
        int[] temp = new int[2];
        queue.add(temp);
        while (queue.length() > 0) {
            current = queue.remove();
            maze[current[0]][current[1]].setVisited(true);
            if (current[0] == maze.length - 1 && current[1] == maze[0].length - 1) {
                break;
            } else {
                //Checking the bounds using multiple cases
                //Ensuring the maze isnt going out of bounds
                if (current[0] - 1 >= 0) {
                    if (!maze[current[0] - 1][current[1]].getVisited() && !maze[current[0] - 1][current[1]].getBottom()) {
                        temp = new int[2];
                        temp[0] = current[0] - 1;
                        temp[1] = current[1];
                        queue.add(temp);
                    }
                }
                //Ensuring the maze isnt going out of bounds
                if (current[0] + 1 <= maze.length - 1) {
                    if (!maze[current[0] + 1][current[1]].getVisited() && !maze[current[0]][current[1]].getBottom()) {
                        temp = new int[2];
                        temp[0] = current[0] + 1;
                        temp[1] = current[1];
                        queue.add(temp);
                    }
                }
                //Ensuring the maze isnt going out of bounds
                if (current[1] - 1 >= 0) {
                    if (!maze[current[0]][current[1] - 1].getVisited() && !maze[current[0]][current[1] - 1].getRight()) {
                        temp = new int[2];
                        temp[0] = current[0];
                        temp[1] = current[1] - 1;
                        queue.add(temp);
                    }
                }
                //Ensuring the maze isnt going out of bounds
                if (current[1] + 1 <= maze[0].length - 1) {
                    if (!maze[current[0]][current[1] + 1].getVisited() && !maze[current[0]][current[1]].getRight()) {
                        temp = new int[2];
                        temp[0] = current[0];
                        temp[1] = current[1] + 1;
                        queue.add(temp);
                    }
                }
            }
        }
        printMaze(true);
    }
    //Main function to print out maze, while solving maze & entering dimenensions required
    public static void main(String[] args) {
        MyMaze maze = MyMaze.makeMaze(5, 20,0,5);
        maze.printMaze(false);
        maze.solveMaze();
        //printing the maze once before solving then after solving
    }
}
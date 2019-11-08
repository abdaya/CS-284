package maze;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * 
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    public void findAllPaths() {
        System.out.println(findAllMazePaths(0, 0));
    }

    /**
     * Attempts to find a path through point (x, y).
     * 
     * @pre Possible path cells are in BACKGROUND color; barrier cells are in
     *      ABNORMAL color.
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true; otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        if (x > maze.getNCols() - 1 || x < 0 || y > maze.getNRows() - 1 || y < 0) {
            return false;
        } else if (maze.getColor(x, y) != GridColors.NON_BACKGROUND) {
            return false;
        } else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            // found solution
            maze.recolor(x, y, GridColors.PATH);
            return true;
        }

        maze.recolor(x, y, GridColors.TEMPORARY);
        if (findMazePath(x - 1, y) || findMazePath(x + 1, y) || findMazePath(x, y - 1) || findMazePath(x, y + 1)) {
            maze.recolor(x, y, GridColors.PATH);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Finds all the possible maze paths through a maze.
     * 
     * @param x      The x-coordinate of current point
     * @param y      The y-coordinate of current point
     * @param result The array list containing array lists of pairs representing
     *               each path
     * @param trace  The current path held in a stack
     */
    private void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
        if (x > maze.getNCols() - 1 || x < 0 || y > maze.getNRows() - 1 || y < 0) {
            // out of bounds
        } else if (maze.getColor(x, y) != GridColors.NON_BACKGROUND) {
            // not part of a path
        } else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            // found path
            trace.push(new PairInt(x, y));
            ArrayList<PairInt> path = new ArrayList<>();
            path.addAll(trace);
            result.add(path);
            maze.recolor(x, y, GridColors.NON_BACKGROUND);
            trace.pop();
        } else {
            maze.recolor(x, y, GridColors.TEMPORARY);
            trace.push(new PairInt(x, y));
            findMazePathStackBased(x + 1, y, result, trace);
            findMazePathStackBased(x - 1, y, result, trace);
            findMazePathStackBased(x, y + 1, result, trace);
            findMazePathStackBased(x, y - 1, result, trace);
            maze.recolor(x, y, GridColors.NON_BACKGROUND);
            trace.pop();
        }
    }

    /**
     * 
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return An array list containing all of the possible paths through a maze.
     */
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathStackBased(0, 0, result, trace);
        return result;
    }

    /**
     * 
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return An array list of the shortest path
     */
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
        ArrayList<ArrayList<PairInt>> paths = findAllMazePaths(x, y);
        if (paths.size() == 0) {
            return new ArrayList<PairInt>();
        }
        ArrayList<PairInt> shortestPath = paths.get(0);
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).size() < shortestPath.size()) {
                shortestPath = paths.get(i);
            }
        }
        return shortestPath;
    }

    /* <exercise chapter="5" section="6" type="programming" number="2"> */
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /* </exercise> */

    /* <exercise chapter="5" section="6" type="programming" number="3"> */
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /* </exercise> */
}
/* </listing> */

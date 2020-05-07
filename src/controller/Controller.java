/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import java.util.Collections;

/**
 *
 * @author DuyDL2
 */
public class Controller {

    private int row;
    private int col;
    private int[][] matrix;
    private ArrayList<ArrayList<Point>> paths = new ArrayList<ArrayList<Point>>();
    MainFrame frame;

    public Controller(MainFrame frame, int row, int col) {
        this.frame = frame;
        this.row = row;
        this.col = col;
        System.out.println(row + "," + col);
        // readFile();
        createMatrix();
        showMatrix();
    }

    // show matrix
    public void showMatrix() {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    private boolean checkLineX(int y1, int y2, int x) {
        System.out.println("check line x");
        // find point have column max and min
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);
        // run column
        for (int y = min + 1; y < max; y++) {
            if (matrix[x][y] != 0) { // if see barrier then die
                System.out.println("die: " + x + "" + y);
                return false;
            }
            System.out.println("ok: " + x + "" + y);
        }
       
        // not die -> success
        return true;
    }

    private boolean checkLineY(int x1, int x2, int y) {
        System.out.println("check line y");
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);
        for (int x = min + 1; x < max; x++) {
            if (matrix[x][y] != 0) {
                System.out.println("die: " + x + "" + y);
                return false;
            }
            System.out.println("ok: " + x + "" + y);
        }
        return true;
    }

    // check in rectangle
    private boolean checkRectX(Point p1, Point p2) {
        System.out.println("check rect x");
        // find point have y min and max
        Point pMinY = p1, pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        for (int y = pMinY.y; y <= pMaxY.y; y++) {
            if (y > pMinY.y && matrix[pMinY.x][y] != 0) {
                return false;
            }
            // check two line
            if ((matrix[pMaxY.x][y] == 0)
                    && checkLineY(pMinY.x, pMaxY.x, y)
                    && checkLineX(y, pMaxY.y, pMaxY.x)) {

                System.out.println("Rect x");
                System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
                        + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
                        + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                // if three line is true return column y
                return true;
            }
        }
        // have a line in three line not true then return -1
        return false;
    }

    private boolean checkRectY(Point p1, Point p2) {
        System.out.println("check rect y");
        // find point have y min
        Point pMinX = p1, pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }
        // find line and y begin
        for (int x = pMinX.x; x <= pMaxX.x; x++) {
            if (x > pMinX.x && matrix[x][pMinX.y] != 0) {
                return false;
            }
            if ((matrix[x][pMaxX.y] == 0)
                    && checkLineX(pMinX.y, pMaxX.y, x)
                    && checkLineY(x, pMaxX.x, pMaxX.y)) {

                System.out.println("Rect y");
                System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x
                        + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
                        + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                return true;
            }
        }
        return false;
    }

    /**
     * p1 and p2 are Points want check
     *
     * @param type : true is check with increase, false is decrease return
     * column can connect p1 and p2
     */
    private boolean checkMoreLineX(Point p1, Point p2, int type) {
        System.out.println("check chec more x");
        // find point have y min
        Point pMinY = p1, pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        // find line and y begin
        int y = pMaxY.y + type;
        int row = pMinY.x;
        int colFinish = pMaxY.y;
        if (type == -1) {
            colFinish = pMinY.y;
            y = pMinY.y + type;
            row = pMaxY.x;
            System.out.println("colFinish = " + colFinish);
        }

        // find column finish of line
        // check more
        if ((matrix[row][colFinish] == 0 || pMinY.y == pMaxY.y)
                && checkLineX(pMinY.y, pMaxY.y, row)) {
            while (matrix[pMinY.x][y] == 0
                    && matrix[pMaxY.x][y] == 0) {
                if (checkLineY(pMinY.x, pMaxY.x, y)) {

                    System.out.println("TH X " + type);
                    System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
                            + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
                            + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                    return true;
                }
                y += type;
            }
        }
        return false;
    }

    private boolean checkMoreLineY(Point p1, Point p2, int type) {
        System.out.println("check more y");
        Point pMinX = p1, pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }
        int x = pMaxX.x + type;
        int col = pMinX.y;
        int rowFinish = pMaxX.x;
        if (type == -1) {
            rowFinish = pMinX.x;
            x = pMinX.x + type;
            col = pMaxX.y;
        }
        if ((matrix[rowFinish][col] == 0 || pMinX.x == pMaxX.x)
                && checkLineY(pMinX.x, pMaxX.x, col)) {
            while (matrix[x][pMinX.y] == 0
                    && matrix[x][pMaxX.y] == 0) {
                if (checkLineX(pMinX.y, pMaxX.y, x)) {
                    System.out.println("TH Y " + type);
                    System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> ("
                            + x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
                            + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                    return true;
                }
                x += type;
            }
        }
        return false;
    }

//    private void findPaths(Point p1, Point p2, ArrayList<Point> path, boolean[][] visited, int type) {
////            if(p1.x == p2.x && p1.y == p2.y){
////                 paths.add(path);
////                 return;
////            }
////            if(p1.x==p2.x){
////                if(checkLineX(p1.y, p2.y, p1.x)){
////                    paths.add(path);
////                }
////            }
////            if(p1.y==p2.y){
////                if(checkLineY(p1.x, p2.x, p1.y)){
////                    paths.add(path);
////                }
////            }
//        int R[] = {1, -1, 0, 0};
//        int C[] = {0, 0, -1, 1};
//        int r = p1.x;
//        int c = p1.y;
//        for (int i = 0; i < 4; i++) {
//            int u = r + R[i];
//            int v = c + C[i];
//
//            if (u < 0 || u >= row || v < 0 || v >= col) {
//                continue;
//            }
//            if (matrix[r][c] == matrix[u][v] && u == p2.x && v == p2.y) {
//                paths.add(path);
//                return;
//            }
//            if (visited[u][v] == false && (matrix[u][v] == 0)) {
//                int t = u;
//                int k = v;
//                while (t >= 0 && t < row && k >= 0 && k < col && matrix[u][v] == 0) {
//                    visited[u][v] = true;
//                    u = t;
//                    v = k;
//                    t += R[i];
//                    k += C[i];
//                }
//
//                Point p3 = new Point();
//                p3.x = u;
//                p3.y = v;
//                ArrayList<Point> pathClone = (ArrayList<Point>) path.clone();
//                pathClone.add(p3);
//                findPaths(p3, p2, pathClone, visited, type);
//                visited[u][v] = false;
//            }
//        }
//    }

    public PointLine checkTwoPoint(Point p1, Point p2) {
//        paths.clear();
//        int type = matrix[p1.x][p1.y];
//        boolean[][] visited = new boolean[row][col];
//        visited[p1.x][p1.y] = true;
//        ArrayList<Point> path = new ArrayList<Point>();
//        path.add(p1);
//        findPaths(p1, p2, path, visited, type);
//        if (!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y] && paths.size() > 0) {
//            return new PointLine(p1, p2);
//        }
//        return null;
        if (!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
            // check line with x
            if (p1.x == p2.x) {
                System.out.println("line x");
                if (checkLineX(p1.y, p2.y, p1.x)) {
                    return new PointLine(p1, p2);
                }
            }
            // check line with y
            if (p1.y == p2.y) {
                System.out.println("line y");
                if (checkLineY(p1.x, p2.x, p1.y)) {
                    System.out.println("ok line y");
                    return new PointLine(p1, p2);
                }
            }


            // check in rectangle with x
            if (checkRectX(p1, p2)) {
                System.out.println("rect x");
                return new PointLine(p1, p2);
            }

            // check in rectangle with y
            if (checkRectY(p1, p2)) {
                System.out.println("rect y");
                return new PointLine(p1, p2);
            }
            // check more right
            if (checkMoreLineX(p1, p2, 1)) {
                System.out.println("more right");
                return new PointLine(p1, p2);
            }
            // check more left
            if (checkMoreLineX(p1, p2, -1)) {
                System.out.println("more left");
                return new PointLine(p1, p2);
            }
            // check more down
            if (checkMoreLineY(p1, p2, 1)) {
                System.out.println("more down");
                return new PointLine(p1, p2);
            }
            // check more up
            if (checkMoreLineY(p1, p2, -1)) {
                System.out.println("more up");
                return new PointLine(p1, p2);
            }
        }
        return null;
    }

    private void createMatrix() {
        matrix = new int[row][col];
        for (int i = 0; i < col; i++) {
            matrix[0][i] = matrix[row - 1][i] = 0;
        }
        for (int i = 0; i < row; i++) {
            matrix[i][0] = matrix[i][col - 1] = 0;
        }

        Random rand = new Random();
        int imgCount = 21;
        int max = imgCount / 2;
        int[] arr = new int[imgCount + 1];
        ArrayList<Point> listPoint = new ArrayList<Point>();
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                listPoint.add(new Point(i, j));
            }
        }
        int i = 0;
        do {
            int index = rand.nextInt(imgCount) + 1;
            if (arr[index] < max) {

                arr[index] += 2;
                for (int j = 0; j < 2; j++) {
                    try {
                        int size = listPoint.size();
                        int pointIndex = rand.nextInt(size);
                        matrix[listPoint.get(pointIndex).x][listPoint
                                .get(pointIndex).y] = index;
                        listPoint.remove(pointIndex);
                    } catch (Exception e) {
                    }
                }
                i++;
            }
        } while (i < row * col / 2);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
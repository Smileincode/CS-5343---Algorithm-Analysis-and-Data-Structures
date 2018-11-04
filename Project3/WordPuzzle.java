import java.io.*;
import java.util.*;

public class WordPuzzle {
    public static void main(String [] args) {
        setSizeOfGrid();
        setGrid(rowsOfGrid, columnsOfGrid);
        dictionaryToTable("dictionary.txt");
        selectGuess();

        long startTime = System.currentTimeMillis( );

        wordGuess(enhanceOrNot);

        long endTime = System.currentTimeMillis( );

        System.out.println( "Elapsed time: " + (endTime - startTime) + "ms");


    }

    private static void dictionaryToTable(String filePath) {
        H.makeEmpty();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                if (str.length() > 1) {
                    for (int i = 1; i < str.length(); i++){
                        H.insert(str.substring(0, i), false);
                    }
                }
                if (str.length() > maxNumOfChar) maxNumOfChar = str.length();
                H.insert(str, true);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setSizeOfGrid() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input an positive integer value for the rows of the grid：");
        while (sc.hasNext()) {
            if (sc.hasNextInt() && (rowsOfGrid = sc.nextInt()) > 0) {
                sc.nextLine();
                System.out.println("Please input an positive integer value for the columns of the grids：");
                while (sc.hasNext()) {
                    if (sc.hasNextInt() && (columnsOfGrid = sc.nextInt()) > 0) {
                        sc.nextLine();
                        break;
                    } else {
                        System.out.println("Please input a positive valid integer:");
                        sc.nextLine();
                    }
                }
            } else {
                System.out.println("Please input a positive valid integer:");
                sc.nextLine();
            }
            if (rowsOfGrid > 0 && columnsOfGrid > 0) break;
        }
    }

    private static void setGrid(int m, int n) {
        grid = new char[m][n];
        Random random = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = (char)(97 + random.nextInt(26));
            }
        }
    }

    private static void selectGuess(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input an integer to choose if using the enhancement (1.YES 0.NO)：");
        while (sc.hasNext()) {
            if (sc.hasNextInt() && (enhanceOrNot = sc.nextInt()) == 1) {
                break;
            } else if (enhanceOrNot == 0){
                break;
            } else {
                System.out.println("Please input 1 or 0:");
                sc.nextLine();
            }
        }
        sc.close();
    }

    private static void wordGuess(int option) {
        for (int i = 0; i < rowsOfGrid; i++) {
            for (int j = 0; j < columnsOfGrid; j++) {
                res = Character.toString(grid[i][j]);
                for (int m = i - 1; m >= 0; m--)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, m, j)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, m, j)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int m = i - 1, n = j + 1; m >= 0 && n < columnsOfGrid; m--, n++)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, m, n)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, m, n)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int n = j + 1; n < columnsOfGrid; n++)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, i, n)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, i, n)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int m = i + 1, n = j + 1; m < rowsOfGrid && n < columnsOfGrid; m++, n++)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, m, n)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, m, n)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int m = i + 1; m < rowsOfGrid; m++)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, m, j)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, m, j)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int m = i + 1, n = j - 1; m < rowsOfGrid && n >= 0; m++, n--)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, m, n)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, m, n)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int n = j - 1; n >= 0; n--)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, i, n)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, i, n)) break;
                    }
                res = Character.toString(grid[i][j]);
                for (int m = i + 1, n = j - 1; m < rowsOfGrid && n >= 0; m++, n--)
                    if (option == 1) {
                        if (!enhancedPrintWord(i, j, m, n)) break;
                    } else {
                        if (!ordinaryPrintWord(i, j, m, n)) break;
                    }
            }
        }
    }

    private static boolean enhancedPrintWord(int p, int q, int x, int y) {
        res = res + Character.toString(grid[x][y]);
        if (H.contains(res) && H.isWord(H.findPos(res))) {
            System.out.println("(" + p + ", " + q + ") --> (" + x + ", " + y + ") " + res);
            return false;
        } else if ((!H.contains(res)) || res.length() > maxNumOfChar) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean ordinaryPrintWord(int p, int q, int x, int y) {
        res = res + Character.toString(grid[x][y]);
        if (H.contains(res) && H.isWord(H.findPos(res))) {
            System.out.println("(" + p + ", " + q + ") --> (" + x + ", " + y + ") " + res);
            return false;
        } else if (res.length() > maxNumOfChar) {
            return false;
        } else {
            return true;
        }
    }

    static char[][] grid;
    static int rowsOfGrid, columnsOfGrid, enhanceOrNot = -1, maxNumOfChar = 0;
    static MyHashTable<String> H = new MyHashTable<>( );
    static String res;
}

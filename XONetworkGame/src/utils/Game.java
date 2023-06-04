/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * This class is responsible for the game itself. including doing actions and
 * manipulating and printing the board
 */
public class Game {

    Character board[][] = new Character[3][3];

    /**
     * Initiate with a clear board.
     */
    public Game() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Returns true if this is a valid point on the board.
     *
     * @param x the number of row.
     * @param y the number of column
     * @return true if this location is valid
     */
    Boolean isValid(int x, int y) {
        return x >= 0 && x <= 2 && y >= 0 && y <= 2;
    }

    /**
     * returns true if the set applied and no violation happened.
     *
     * @param x number of row
     * @param y number of column
     * @param c the character to be set in this location
     * @return true if this action applied.
     */
    public Boolean set(int x, int y, char c) {
        if (!isValid(x, y) || board[x][y] != ' ') {
            return false;
        }
        board[x][y] = Character.toUpperCase(c);
        return true;
    }

    /**
     * Print the current board to the stdin.
     */
    public void printBoard() {
        System.out.println("/---|---|---\\");
        System.out.println("| " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |");
        System.out.println("/---|---|---\\");
    }

    /**
     * Check the state of the board.
     *
     * @return 'X' if x won, 'O' if 'O' won, 'D' if draw happened, and ' ' if
     * the state is normal
     */
    public Character checkWinner() {
        Boolean isDraw = true;
        for (int i = 0; i < 8; i++) {
            String line = null;
            switch (i) {
                case 0:
                    line = board[0][0].toString() + board[0][1] + board[0][2];
                    break;
                case 1:
                    line = board[0][0].toString() + board[1][1] + board[2][2];
                    break;
                case 2:
                    line = board[0][0].toString() + board[1][0] + board[2][0];
                    break;
                case 3:
                    line = board[0][1].toString() + board[1][1] + board[2][1];
                    break;
                case 4:
                    line = board[0][2].toString() + board[1][2] + board[2][2];
                    break;
                case 5:
                    line = board[1][0].toString() + board[1][1] + board[1][2];
                    break;
                case 6:
                    line = board[2][0].toString() + board[2][1] + board[2][2];
                    break;
                case 7:
                    line = board[0][2].toString() + board[1][1] + board[2][0];
                    break;
            }
            if (line.equals("XXX")) {
                return 'X';
            } else if (line.equals("OOO")) {
                return 'O';
            }
            if (line.contains(" ")) {
                isDraw = false;
            }
        }
        return (isDraw) ? 'D' : ' ';
    }
}

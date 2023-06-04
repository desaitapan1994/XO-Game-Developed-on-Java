/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.Scanner;
import utils.Game;

/**
 * The main client class to perform connection with the server.
 *
 * @author Antou
 */
public class XOClient {

    private static ClientConnection conn;
    private static char myChar;
    private static int prevX = -1;
    private static int prevY = -1;
    private static Game g = null;
    private static Scanner sc = new Scanner(System.in);
    private static Integer tempArr[] = new Integer[2];
    private static Boolean YOUWON, TIE;

    /**
     * Main function that runs all what needed to make a client play.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        conn = ClientConnection.getInstance();
        System.out.println("Waiting for other player to connect ...");
        String currentMessage = "";
        while (true) {
            currentMessage = conn.recieve();
            if (currentMessage == null) {
                System.out.println("Connection with the server is lost.");
                break;
            }
            currentMessage = currentMessage.trim();
            if (currentMessage.startsWith(utils.Messages.GIVE)) {
                myChar = currentMessage.charAt(currentMessage.length() - 1);
                g = new Game();
                System.out.println("A game has started.");
                if (myChar == 'X') {
                    sendAction();
                } else {
                    g.printBoard();
                    System.out.println("Waiting for the other player to play...");
                }
            } else if (currentMessage.startsWith(utils.Messages.VALID)) {
                g.set(prevX, prevY, myChar);
                g.printBoard();
                System.out.println("Waiting for the other player to play...");
            } else if (currentMessage.startsWith(utils.Messages.ACTION)) {
                utils.Messages.action_message_interpret(currentMessage, tempArr);
                prevX = tempArr[0];
                prevY = tempArr[1];
                g.set(prevX, prevY, (myChar == 'X') ? 'Y' : 'X');
                sendAction();
            } else if (currentMessage.startsWith(utils.Messages.INVALID)) {
                System.out.println("Sorry, Invalid action.");
                sendAction();
            } else if (currentMessage.equals(utils.Messages.YOUWON)
                    || currentMessage.equals(utils.Messages.YOULOSE)
                    || currentMessage.equals(utils.Messages.TIE)) {
                YOUWON = currentMessage.equals(utils.Messages.YOUWON);
                TIE = currentMessage.equals(utils.Messages.TIE);
                currentMessage = conn.recieve();
                currentMessage = currentMessage.trim();
                utils.Messages.action_message_interpret(currentMessage, tempArr);
                prevX = tempArr[0];
                prevY = tempArr[1];
                if (prevX != -1 && prevY != -1) {
                    g.set(prevX, prevY, (myChar == 'X') ? 'Y' : 'X');
                }
                g.printBoard();
                if (YOUWON) {
                    System.out.println("You won!");
                } else if (TIE) {
                    System.out.println("TIE!");
                } else {
                    System.out.println("You Lose!");

                }
                break;
            }
        }
    }

    /**
     * Get location from the user and send actions upon it
     */
    private static void sendAction() {
        g.printBoard();
        System.out.print("Please enter x,y (or q to quit): ");
        String read = sc.nextLine();
        if (read.equals("q")) {
            conn.send(utils.Messages.IQUIT);
        } else {
            String arr[] = read.split(" ");
            if (arr.length == 1) {
                prevX = Integer.parseInt(arr[0]);
                prevY = sc.nextInt();
            } else {
                prevX = Integer.parseInt(arr[0]);
                prevY = Integer.parseInt(arr[1]);
            }
            conn.send(utils.Messages.Action_Message(prevX, prevY));
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Game;
import utils.SocketAction;

/**
 * This is the thread that runs in the server process.
 *
 * @author Antou
 */
public class XODeamon extends Thread {

    /**
     * The port used by this network game.
     */
    private final Integer PORT_NUMBER = 8080;
    private ServerSocket serverSocket;
    private ArrayList<SocketAction> players;

    /**
     * This construct the server thread. it initiates the server socket and the
     * players list.
     */
    public XODeamon() {
        try {
            this.serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException ex) {
            System.out.println("Couldn't create server socket.");
            System.exit(-1);
        }
        players = new ArrayList<>();
    }

    /**
     * The actual server thread.
     */
    @Override
    public void run() {
        Socket clientSocket;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                players.add(new SocketAction(clientSocket, true));
                // wait for the players to be 2
                if (players.size() == 2) {
                    playGame();
                    Thread.sleep(1000);
                    clientSocket.close();
                    return;
                }
            } catch (IOException ex) {
                Logger.getLogger(XODeamon.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(XODeamon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Function called by the thread run. and it holds all the logic of dealing
     * with the players.
     */
    private void playGame() {
        utils.Game g = new Game();

        // initiate each palyer with its character by giving a give message
        players.get(0).send(utils.Messages.Give_Message('X'));
        players.get(1).send(utils.Messages.Give_Message('Y'));
        // initiate some variables
        char characters[] = {'X', 'Y'};
        char winner = ' ';
        Integer x = -1, y = -1;
        String temp;
        Integer tempArr[] = new Integer[2];

        // start of the game.
        while (winner == ' ') {
            for (int i = 0; i < players.size(); i++) {
                // if at any time any of the connection has lost stop the game
                if (!players.get(i).isConnected()) {
                    System.out.println("Lost Connection to player " + i + ".");
                    players.get(1 - i).send(utils.Messages.YOUWON);
                    players.get(1 - i).send(utils.Messages.Action_Message(-1, -1));
                    return;
                }
                // get action from the user
                temp = recieveAction(players.get(i), tempArr);
                x = tempArr[0];
                y = tempArr[1];

                if (!temp.equals("ACTION")) {
                    // quit message
                    players.get(i).send(utils.Messages.YOULOSE);
                    players.get(1 - i).send(utils.Messages.YOUWON);
                    players.get(1 - i).send(utils.Messages.Action_Message(-1, -1));
                    players.get(i).send(utils.Messages.Action_Message(-1, -1));
                    System.out.println("Player "+i+" Quited.");
                    return;
                } else {
                    // normal action message
                    if (!g.set(x, y, characters[i])) {
                        // invalid move
                        players.get(i).send(utils.Messages.INVALID);
                        i--;
                        continue;
                    }
                    //valid move
                    players.get(i).send(utils.Messages.VALID);
                    winner = g.checkWinner();
                    if (winner == 'X') {
                        // X Won
                        players.get(0).send(utils.Messages.YOUWON);
                        players.get(1).send(utils.Messages.YOULOSE);
                    } else if (winner == 'O') {
                        // O Won
                        players.get(1).send(utils.Messages.YOUWON);
                        players.get(0).send(utils.Messages.YOULOSE);
                    } else if (winner == 'D') {
                        // DRAW
                        players.get(0).send(utils.Messages.TIE);
                        players.get(1).send(utils.Messages.TIE);
                    }
                    // send action to the user
                    players.get(1 - i).send(utils.Messages.Action_Message(x, y));
                    if (winner != ' ') {
                        // Game ended
                        players.get(i).send(utils.Messages.Action_Message(-1, -1));
                        break;
                    }
                }
            }
        }
        if (winner == 'D') {
            System.out.println("Draw!");
        } else {
            System.out.printf("Player %c won\n", winner);
        }
    }

    /**
     * Get action from the user according to the send message.
     *
     * @param player The player to get action from.
     * @param ptArr the array to set x and y in if needed.
     * @return "Quit" if connection lost or player surrended, "Action"
     * otherwise.
     */
    private String recieveAction(SocketAction player, Integer ptArr[]) {
        String msg = player.recieve();
        if (msg == null) {
            // if at any time any of the connection has lost stop the game
            return "QUIT";
        } else if (utils.Messages.action_message_interpret(msg, ptArr)) {
            return "ACTION";
        } else {
            return "QUIT";
        }
    }
}

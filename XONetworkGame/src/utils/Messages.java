/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * This contains all the message that could be sent through the game.
 *
 * @author Antou
 */
public class Messages {

    /**
     * The Messages that the server could send in the game.
     */
    /**
     * Give a character to play with. either X or O.
     */
    public static final String GIVE = "GIVE";
    /**
     * Returns this message to the sender if he sent a valid move.
     */
    public static final String VALID = "VALID";
    /**
     * Returns this message to the sender if he sent an invalid move.
     */
    public static final String INVALID = "NOTVALID";
    /**
     * Send to the player who wins the game.
     */
    public static final String YOUWON = "YOUWON";
    /**
     * Send to the player who loses the game.
     */
    public static final String YOULOSE = "YOULOSE";
    /**
     * Send to the player when a tie happens.
     */
    public static final String TIE = "TIE";
    /**
     * This message send from the server and used to acknowledge the other
     * player of current player move.
     */
    //public static final String ACTION = "ACTION";

    /**
     * The messages that the client can send during the game.
     */
    /**
     * This message is sent from the client to acknowledge the server with the 
     * current player move.
     */
    public static final String ACTION = "ACTION";
    /**
     * This message is sent if a quit from any of the players happens happens.
     */
    public static final String IQUIT = "IQUIT";

    /**
     * Prepare a give Message. To give the client the Token to play with
     *
     * @param x The token to play with
     * @return the message.
     */
    public final static String Give_Message(char x) {
        return GIVE + " " + x;
    }

    /**
     * Prepare an action Message. To give the server the new action from the
     * user.
     *
     * @param x the row number.
     * @param y the column number.
     * @return the message.
     */
    public final static String Action_Message(int x, int y) {
        return ACTION + " " + x + " " + y;
    }

    public final static Boolean action_message_interpret(String msg, Integer pt[]) {
        msg = msg.trim();
        String res[] = msg.split(" ");
        if (!res[0].equals(ACTION) || res.length != 3) {
            return false;
        }
        pt[0] = Integer.parseInt(res[1]);
        pt[1] = Integer.parseInt(res[2]);
        return true;
    }
}

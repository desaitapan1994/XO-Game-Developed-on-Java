/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This is a class to facilitate the usage of the socket. It has the actions of
 * initiate, send and receive.
 */
public class SocketAction extends Thread {

    private BufferedReader inStream = null;
    private PrintStream outStream = null;
    private Socket sock = null;
    private Boolean isServer;

    /**
     * Initiate input and output stream according to the socket.
     * @param s The socket to be used
     * @param _isServer 
     */
    public SocketAction(Socket s, Boolean _isServer) {
        super("SocketAction");
        isServer = _isServer;
        try {
            inStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outStream = new PrintStream(new BufferedOutputStream(s.getOutputStream(), 1024));
        } catch (IOException ex) {
            System.out.println("Couldn't init the SocketAction: " + ex);
        }
        sock = s;
    }

    /**
     * Send a message through the socket.
     * @param s the string to be sent
     */
    public void send(String s) {
        if (isServer) {
            System.out.println("Sending " + s);
        }
        outStream.println(s);
        outStream.flush();
    }

    /**
     * Receive a message through the socket.
     * @return the string received 
     */
    public String recieve() {
        try {
            String res = inStream.readLine();
            if (isServer) {
                System.out.println("recieved: " + res);
            }
            return res;
        } catch (IOException ex) {
            System.out.println("Couldn't readLine in Socket Action: " + ex);
        }
        return null;
    }

    /**
     * Close the current socket.
     */
    public void closeConnections() {
        try {
            sock.close();
            sock = null;
        } catch (IOException ex) {
            System.out.println("Couldn't Close connection: " + ex);
        }
    }

    /**
     * Returns true if the socket is still connected.
     * @return 
     */
    public Boolean isConnected() {
        return inStream != null && outStream != null && sock != null;
    }

    /**
     * Close the connection.
     * @throws Throwable 
     */
    @Override
    protected void finalize() throws Throwable {
        closeConnections();
        super.finalize();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Antou
 */
public class XOServer {

    /**
     * The main function of the server and it is used to run the thread
     * XODeamon.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("XO server is running...");
        new XODeamon().run();
    }

}

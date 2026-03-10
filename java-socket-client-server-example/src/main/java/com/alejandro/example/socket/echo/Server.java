package com.alejandro.example.socket.echo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws Exception {
        System.out.println("Nombre Hilo (Server.main): "+Thread.currentThread().getName());

        new Thread(()-> {
            System.out.println("Nombre Hilo (Server.main secundario): "+Thread.currentThread().getName());
        }).start();

        Socket socket = null;

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("0.0.fg.0", 8080));

        while (true){
            socket = serverSocket.accept();

            ClientHandler ch = new ClientHandler(socket);
            Thread thread = new Thread(ch);
            thread.start();
        }
    }
}

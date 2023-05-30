package com.alejandro.example.socket.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Nombre Hilo (ClientHandler): "+Thread.currentThread().getName());
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            while (true) {

                String msgFromClient = reader.readLine();

                System.out.println("Echoing to client in thread("+Thread.currentThread().getName()+"):" + msgFromClient);
                writer.write(msgFromClient);
                writer.newLine();
                writer.flush();

                if (msgFromClient.equalsIgnoreCase("bye")) {
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.alejandro.example.socket.echo;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", 8080);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            while (true) {
                String msgToSend = scanner.nextLine();
                writer.write(msgToSend); // +"\n"
                writer.newLine();
                writer.flush();

                String msgFromClientToServer = reader.readLine();

                System.out.println("Server :" + msgFromClientToServer);
                System.out.println("Respond to the server:");

                if (msgToSend.equalsIgnoreCase("bye")){
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

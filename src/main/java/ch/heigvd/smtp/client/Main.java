package ch.heigvd.smtp.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1025;

        try {
            Client client = new Client(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ch.heigvd.smtp.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host;
    private Integer port;
    private Socket client;
    private PrintWriter output;
    private Scanner input;

    public Client(String host, Integer port) throws IOException {
        this.host = host;
        this.port = port;

        client = new Socket(host, port);
        output = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
        input  = new Scanner(new BufferedInputStream(client.getInputStream()));
    }

    public void sendEmail(MailHeader header, MailContent) {

    }
}

package ch.heigvd.smtp.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ch.heigvd.smtp.client.SMTPMessages;

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

    public void sendEmail(MailHeader header, MailContent content) {
        while(true) {
            String response = input.nextLine();
            System.out.println(response);

            if(response.matches("^\\d{3} .*$"))
                break;
        }

        output.print(SMTPMessages.hello("local"));
        output.flush();

        while(true) {
            String response = input.nextLine();
            System.out.println(response);

            if(response.matches("^\\d{3} .*$"))
                break;
        }

        output.print(SMTPMessages.headerFrom(header.getSender()));
        output.flush();

        while(!input.hasNextLine());

        System.out.println(input.nextLine());

        for(String receiver : header.getReceivers()) {
            output.print(SMTPMessages.headerTo(receiver));
            output.flush();

            while(!input.hasNextLine());

            System.out.println(input.nextLine());
        }

        output.print(SMTPMessages.startData());
        output.flush();

        while(!input.hasNextLine());

        System.out.println(input.nextLine());

        output.print(SMTPMessages.dataFrom(header.getSender()));

        for(String receiver : header.getReceivers()) {
            output.print(SMTPMessages.dataTo(receiver));
        }

        output.print(SMTPMessages.dataSubject(content.getSubject()));

        output.println(content.getMessage());

        output.print(SMTPMessages.endData());
        output.flush();

        output.print(SMTPMessages.quit());
        output.flush();
    }
}

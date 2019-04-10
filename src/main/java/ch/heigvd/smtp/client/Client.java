package ch.heigvd.smtp.client;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Logger;

public class Client {
    static final Logger LOG = Logger.getLogger(Client.class.getName());

    private String host;
    private Integer port;

    private IOUser ioUser;
    private Socket client = null;
    private PrintWriter output = null;
    private Scanner input = null;

    public Client(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.ioUser = new IOUser();
    }

    public void connect(String as) throws IOException {
        client = new Socket(host, port);
        output = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
        input  = new Scanner(new BufferedInputStream(client.getInputStream()));

        LOG.info("Connecting...");
        readResponse("220");

        send(SMTPMessages.hello(as));

        boolean canAuth = readResponse("250", new ReponseStateHandler() {
            private boolean state = false;

            @Override
            public void onResponse(String response) {
                if(response.contains("AUTH") && response.contains("LOGIN"))
                    state = true;
            }

            @Override
            public boolean getState() {
                return state;
            }
        });

        if(!canAuth || !ioUser.wantCredential())
            return;

        Credential credential = ioUser.getCredential();

        send(SMTPMessages.login());
        readResponse("334");

        send(SMTPMessages.username(credential.getUsername()));
        readResponse("334");

        send(SMTPMessages.password(credential.getPassword()));
        readResponse("235");
    }

    public void disconnect() throws IOException {
        if(client == null) {
            LOG.severe("Need to be connect to disconnect !!");
            throw new RuntimeException("Need to be connect to disconnect !!");
        }

        LOG.info("Disconnecting...");
        send(SMTPMessages.quit());

        readResponse("221");

        output.close();
        input.close();
        client.close();
    }

    public void sendEmail(MailHeader header, MailContent content) {
        if(client == null) {
            LOG.severe("Need to be connect to send an email !!");
            throw new RuntimeException("Need to be connect to send an email !!");
        }

        LOG.info("Sending email...");
        MailData data = new MailData(header, content);

        LOG.info("Communication sequence :");
        send(SMTPMessages.headerFrom(header.getSender()));

        readResponse("250");

        for(String receiver : header.getReceivers()) {
            send(SMTPMessages.headerTo(receiver));

            readResponse("250");
        }

        send(SMTPMessages.startData());

        readResponse("354");

        send(data.dump());

        send(SMTPMessages.endData());

        readResponse("250");
    }

    private void send(String command) {
        System.out.print(command);

        output.print(command);
        output.flush();
    }

    private void readResponse(String codeAttempt) {
        StringBuilder sb = new StringBuilder();
        sb.append("^").append(codeAttempt).append(".*$");
        String regexCodeAttempt = sb.toString();

        while(true) {
            String response = input.nextLine();
            System.out.println(response);

            if(!response.matches(regexCodeAttempt)) {
                LOG.severe("Error attempt");
                throw new RuntimeException("Error attempt");
            }

            if(response.matches("^\\d{3} .*$"))
                break;
        }
    }

    private boolean readResponse(String codeAttempt, ReponseStateHandler handler) {
        StringBuilder sb = new StringBuilder();
        sb.append("^").append(codeAttempt).append(".*$");
        String regexCodeAttempt = sb.toString();

        while(true) {
            String response = input.nextLine();
            System.out.println(response);

            handler.onResponse(response);

            if(!response.matches(regexCodeAttempt)) {
                LOG.severe("Error attempt");
                throw new RuntimeException("Error attempt");
            }

            if(response.matches("^\\d{3} .*$"))
                break;
        }

        return handler.getState();
    }

    public interface ReponseStateHandler {
        public void onResponse(String response);
        public boolean getState();
    }
}

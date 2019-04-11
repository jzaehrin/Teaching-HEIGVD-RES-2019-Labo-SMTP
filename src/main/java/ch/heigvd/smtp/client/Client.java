package ch.heigvd.smtp.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    static final Logger LOG = Logger.getLogger(Client.class.getName());

    private String host;
    private Integer port;

    private IOUser ioUser;
    private Socket client = null;
    private PrintWriter output = null;
    private Scanner input = null;

    public Client(String host, Integer port, Level logLevel) {
        this.host = host;
        this.port = port;
        this.ioUser = new IOUser();

        LOG.setLevel(logLevel);
    }

    public void connect(String as) throws IOException {
        client = new Socket(host, port);
        output = new PrintWriter((new BufferedOutputStream(client.getOutputStream())));
        input  = new Scanner(new BufferedInputStream(client.getInputStream()));

        LOG.info("Connecting...");
        readResponse("220");

        send(SMTPMessages.hello(as));

        ReponseVisitor visitor = new ReponseVisitor() {
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
        };

        readResponse("250", visitor);

        if(!visitor.getState() || !ioUser.wantCredential())
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

    public void sendEmail(Mail mail) {
        if(client == null) {
            LOG.severe("Need to be connect to send an email !!");
            throw new RuntimeException("Need to be connect to send an email !!");
        }

        LOG.info("Sending email...");

        LOG.info("Communication sequence :");
        send(SMTPMessages.headerFrom(mail.getSender()));

        readResponse("250");

        for(String receiver : mail.getReceivers()) {
            send(SMTPMessages.headerTo(receiver));

            readResponse("250");
        }


        send(SMTPMessages.startData());

        readResponse("354");

        send(mail.dumpData());

        send(SMTPMessages.endData());

        readResponse("250");
    }

    private void send(String command) {
        if(LOG.getLevel() == Level.ALL)
            System.out.print(command);

        output.print(command);
        output.flush();
    }

    private void readResponse(String codeAttempt) {
        readResponse(codeAttempt, null);
    }

    private void readResponse(String codeAttempt, ReponseVisitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append("^").append(codeAttempt).append(".*$");
        String regexCodeAttempt = sb.toString();

        while(true) {
            String response = input.nextLine();

            if(LOG.getLevel() == Level.ALL)
                System.out.println(response);

            if(visitor != null)
                visitor.onResponse(response);

            if(!response.matches(regexCodeAttempt)) {
                LOG.severe("Error attempt");
                throw new RuntimeException("Error attempt");
            }

            if(response.matches("^\\d{3} .*$"))
                break;
        }
    }

    public interface ReponseVisitor {
        void onResponse(String response);
        boolean getState();
    }
}

package ch.heigvd.smtp.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1025;

        try {
            Client client = new Client(host, port);

            MailHeader mh = new MailHeader();
            mh.setSender("sender@prank.org");
            mh.addReceiver("receiver1@pranked.org");
            mh.addReceiver("receiver2@pranked.org");

            MailContent mc = new MailContent();
            mc.setSubject("Subject test");
            mc.setMessage("Message test");

            client.sendEmail(mh, mc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
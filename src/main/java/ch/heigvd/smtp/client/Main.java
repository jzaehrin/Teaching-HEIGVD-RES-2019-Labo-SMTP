package ch.heigvd.smtp.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String host = "smtp.mailtrap.io";
        int port = 25;

        try {
            Credential credential = new Credential("e8eaf81d87daad", "0ecad410504d1d");
            Client client = new Client(host, port, credential);

            MailHeader mh = new MailHeader();
            mh.setSender("sender@prank.org");
            mh.addReceiver("receiver1@pranked.org");
            mh.addReceiver("receiver2@pranked.org");

            MailContent mc = new MailContent();
            mc.setSubject("Subject test");
            mc.setMessage("Message test");

            client.connect("local");
            client.sendEmail(mh, mc);
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

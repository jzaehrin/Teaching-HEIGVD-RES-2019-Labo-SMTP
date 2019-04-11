package ch.heigvd.smtp.client;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        Client client = new Client(configLoader.getHostname(), configLoader.getPort());
        IOUser ioUser = new IOUser();
        ArrayList<Mail> mails = new ArrayList<>();
        ArrayList<MailContent> mailContents = configLoader.getMailContents();

        // iterate over each groups
        int iHeader = 1;
        for (MailHeader mailHeader : configLoader.getMailHeaders()) {
            // Display group informations
            System.out.println("Group " + iHeader);
            System.out.println(mailHeader);

            boolean wantContinue;
            // choose one or more mails to send with the current group
            do {

                // list all possible content with index (subject only)
                int iContent = 1;
                for (MailContent mailContent : mailContents) {
                    System.out.format("%d) %s\n", iContent++, mailContent.getSubject());
                }

                String msg = String.format("Please choose a mail for the group %d: ", iHeader);

                iContent = ioUser.getInt(1, mailContents.size(), msg) - 1;

                mails.add(new Mail(mailHeader, mailContents.get(iContent)));

                // continue ?
                msg = "Do you want to send an other mail with the current group ? ";
                wantContinue = ioUser.getYesNo(msg);
            } while (wantContinue);
            ++iHeader;
        }

        // send mails
        try {
            client.connect(configLoader.getDomain());

            for (Mail mail : mails)
                client.sendEmail(mail);

            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

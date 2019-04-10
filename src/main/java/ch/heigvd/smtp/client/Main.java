package ch.heigvd.smtp.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        Client client = new Client(configLoader.getHostname(), configLoader.getPort());
        IOUser ioUser = new IOUser();
        ArrayList<Mail> mails = new ArrayList<>();
        ArrayList<MailContent> mailContents = configLoader.getMailContents();

        int iHeader = 1;
        for (MailHeader mailHeader : configLoader.getMailHeaders()) {
            boolean wantContinue;
            do {
                System.out.println("Group " + iHeader);
                System.out.println(mailHeader);

                int iContent = 1;
                for (MailContent mailContent : mailContents) {
                    System.out.format("%d) %s\n", iContent++, mailContent.getSubject());
                }

                String msg = String.format("Please choose a mail for the group %d: ", iHeader);

                int choice = ioUser.getInt(1, mailContents.size(), msg);

                mails.add(new Mail(mailHeader, mailContents.get(--choice)));

                msg = "Do you want to send an other mail with the current group ?";
                wantContinue = ioUser.getYesNo(msg);
            } while (wantContinue);

            ++iHeader;
        }

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

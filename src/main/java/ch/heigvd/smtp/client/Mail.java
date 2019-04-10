package ch.heigvd.smtp.client;

import java.util.ArrayList;

public class Mail {
    private MailHeader mailHeader;
    private MailContent mailContent;

    public Mail(MailHeader mailHeader, MailContent mailContent) {
        this.mailHeader = mailHeader;
        this.mailContent = mailContent;
    }

    public String getSender() {
        return mailHeader.getSender();
    }

    public ArrayList<String> getReceivers() {
        return mailHeader.getReceivers();
    }

    public String getSubject() {
        return mailContent.getSubject();
    }

    public String getMessage() {
        return mailContent.getMessage();
    }

    public String dumpData() {
        StringBuilder sb = new StringBuilder();
        sb.append(SMTPMessages.dataFrom(mailHeader.getSender()));

        for (String receiver : mailHeader.getReceivers())
            sb.append(SMTPMessages.dataTo(receiver));

        sb.append(SMTPMessages.dataSubject(mailContent.getSubject()));
        sb.append(SMTPMessages.message(mailContent.getMessage()));

        return sb.toString();
    }
}

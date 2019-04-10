package ch.heigvd.smtp.client;

public class MailData {
    private MailHeader mailHeader;
    private MailContent mailContent;

    public MailData(MailHeader mailHeader, MailContent mailContent) {
        this.mailHeader = mailHeader;
        this.mailContent = mailContent;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(SMTPMessages.dataFrom(mailHeader.getSender()));

        for(String receiver : mailHeader.getReceivers())
            sb.append(SMTPMessages.dataTo(receiver));

        sb.append(SMTPMessages.dataSubject(mailContent.getSubject()));
        sb.append(mailContent.getMessage());

        return sb.toString();
    }
}

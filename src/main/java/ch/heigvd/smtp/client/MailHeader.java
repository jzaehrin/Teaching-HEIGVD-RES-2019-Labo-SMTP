package ch.heigvd.smtp.client;

import java.util.ArrayList;

public class MailHeader {
    private String sender;
    private ArrayList<String> receivers;

    public MailHeader() {
        receivers = new ArrayList<>();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ArrayList<String> getReceivers() {
        return receivers;
    }

    public void addReceiver(String receivers) {
        this.receivers.add(receivers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Sender   :\n\t")
                .append(sender)
                .append("\nReceivers:");

        for (String receiver : receivers)
            sb.append("\n\t").append(receiver);

        return sb.toString();
    }
}

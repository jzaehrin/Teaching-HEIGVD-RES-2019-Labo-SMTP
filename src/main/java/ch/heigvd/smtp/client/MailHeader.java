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
}

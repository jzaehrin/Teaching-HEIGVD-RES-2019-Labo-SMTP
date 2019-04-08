package ch.heigvd.smtp.client;

public class MailContent {
    private String subject;
    private String message;

    public MailContent() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package ch.heigvd.smtp.client;

public class SMTPMessages {
    private static String endline = "\r\n";

    public static String hello(String server) {
        return String.format("EHLO %s%s", server, endline);
    }

    public static String login() {
        return String.format("AUTH LOGIN%s", endline);
    }

    public static String username(String username) {
        return String.format("%s%s", username, endline);
    }

    public static String password(String password) {
        return String.format("%s%s", password, endline);
    }

    public static String headerFrom(String sender) {
        return String.format("MAIL FROM: <%s>%s", sender, endline);
    }

    public static String headerTo(String receiver) {
        return String.format("RCPT TO: <%s>%s", receiver, endline);
    }

    public static String setEncoding() {
        return String.format("Content-Type: text/plain; charset=utf-8%s", endline);
    }

    public static String startData() {
        return String.format("Data%s", endline);
    }

    public static String dataFrom(String sender) {;
        return String.format("From: %s%s", sender, endline);
    }

    public static String dataTo(String receiver) {
        return String.format("To: %s%s", receiver, endline);
    }

    public static String dataSubject(String subject) {
        return String.format("Subject: %s%s%s", subject, endline, endline);
    }

    public static String message(String message) {
        return String.format("%s%s", message, endline);
    }

    public static String endData() {
        return String.format(".%s", endline);
    }

    public static String quit() {
        return String.format("quit%s", endline);
    }
}

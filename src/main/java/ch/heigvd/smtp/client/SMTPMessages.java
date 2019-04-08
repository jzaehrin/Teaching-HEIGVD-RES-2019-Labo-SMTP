package ch.heigvd.smtp.client;

public class SMTPMessages {
    private static String endline = "\r\n";

    public static String hello(String server) {
        return "EHLO " + server + endline;
    }

    public static String headerFrom(String sender) {
        return "MAIL FROM: " + sender + endline;
    }

    public static String headerTo(String receiver) {
        return "RCPT TO: " + receiver + endline;
    }

    public static String startData() {
        return "Data" + endline;
    }

    public static String dataFrom(String sender) {
        return "From: " + sender + endline;
    }

    public static String dataTo(String receiver) {
        return "To: " + receiver + endline;
    }

    public static String dataSubject(String subject) {
        return "Subject: " + subject + endline;
    }

    public static String endData() {
        return "." + endline;
    }

    public static String quit() {
        return "quit" + endline;
    }
}

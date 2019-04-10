package ch.heigvd.smtp.client;

public class SMTPMessages {
    private static String endline = "\r\n";
    private static StringBuilder sb= new StringBuilder();

    public static String hello(String server) {
        sb.setLength(0);
        sb.append("EHLO ").append(server).append(endline);
        return sb.toString();
    }

    public static String login() {
        sb.setLength(0);
        sb.append("AUTH LOGIN").append(endline);
        return sb.toString();
    }

    public static String username(String username) {
        sb.setLength(0);
        sb.append(username).append(endline);
        return sb.toString();
    }

    public static String password(String password) {
        sb.setLength(0);
        sb.append(password).append(endline);
        return sb.toString();
    }

    public static String headerFrom(String sender) {
        sb.setLength(0);
        sb.append("MAIL FROM: <").append(sender).append(">").append(endline);
        return sb.toString();
    }

    public static String headerTo(String receiver) {
        sb.setLength(0);
        sb.append("RCPT TO: <").append(receiver).append(">").append(endline);
        return sb.toString();
    }

    public static String startData() {
        sb.setLength(0);
        sb.append("Data").append(endline);
        return sb.toString();
    }

    public static String dataFrom(String sender) {
        sb.setLength(0);
        sb.append("From: ").append(sender).append(endline);
        return sb.toString();
    }

    public static String dataTo(String receiver) {
        sb.setLength(0);
        sb.append("To: ").append(receiver).append(endline);
        return sb.toString();
    }

    public static String dataSubject(String subject) {
        sb.setLength(0);
        sb.append("Subject: ").append(subject).append(endline).append(endline);
        return sb.toString();
    }

    public static String message(String message) {
        sb.setLength(0);
        sb.append(message).append(endline);
        return sb.toString();
    }

    public static String endData() {
        sb.setLength(0);
        sb.append(".").append(endline);
        return sb.toString();
    }

    public static String quit() {
        sb.setLength(0);
        sb.append("quit").append(endline);
        return sb.toString();
    }
}

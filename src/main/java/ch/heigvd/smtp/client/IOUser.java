package ch.heigvd.smtp.client;

import java.util.Scanner;

public class IOUser {
    private Scanner scanner;

    public IOUser() {
        scanner = new Scanner(System.in);
    }

    public boolean wantCredential() {
        return getYesNo("Would you use the authentification for SMTP ?", false);
    }

    public Credential getCredential() {
        System.out.print("Username : ");
        String username = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        return new Credential(username, password);
    }

    public int getInt(int min, int max, String msg) {
        int res;

        while (true) {
            System.out.print(msg);

            while (!scanner.hasNext()) ;

            if (!scanner.hasNextInt()) {
                System.out.format("\"%s\" is not a valid integer.\n", scanner.next());
                continue;
            }

            res = scanner.nextInt();

            if (res >= min && res <= max)
                break;

            System.out.format("Please choose an integer between %d and %d.\n", min, max);
        }

        scanner.nextLine(); // flush
        return res;
    }

    public boolean getYesNo(String msg) {
        return getYesNo(msg, false);
    }

    public boolean getYesNo(String msg, boolean defaulYes) {
        String def = defaulYes ? new String(" [Y/n] ") : new String(" [y/N] ");
        msg += def;

        while (true) {
            System.out.print(msg);

            while (!scanner.hasNextLine()) ;

            String result = scanner.nextLine();

            if (result.length() == 0)
                return defaulYes;

            char firstLetter = result.charAt(0);

            if (firstLetter == 'y' || firstLetter == 'Y')
                return true;

            if (firstLetter == 'n' || firstLetter == 'N')
                return false;

            System.out.println("Please answer the question with 'y' or 'n'.");
        }
    }
}

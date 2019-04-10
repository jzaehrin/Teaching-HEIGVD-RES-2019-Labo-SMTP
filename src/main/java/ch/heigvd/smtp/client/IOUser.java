package ch.heigvd.smtp.client;

import java.util.Scanner;

public class IOUser {
    private Scanner scanner;

    public IOUser() {
        scanner = new Scanner(System.in);
    }

    public boolean wantCredential() {
        while(true) {
            System.out.println("Would you use the authentification for SMTP ? [N/y]");
            String result = scanner.nextLine();

            if (result.contains("y") || result.contains("Y"))
                return true;
            else if(result.contains("n") || result.contains("N"))
                return false;
        }
    }

    public Credential getCredential() {
        System.out.print("Username : ");
        String username = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        return  new Credential(username, password);
    }
}

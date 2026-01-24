// Main menu driven app
// uses bussness layer and connection layer for its working
// division of dutys

package crex;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== CAMPUS RESOURCE EXCHANGE =====");
            System.out.println("1. Register User");
            System.out.println("2. Add Resource");
            System.out.println("3. View Available Resources");
            System.out.println("4. Borrow Resource");
            System.out.println("5. Return Resource");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> UserDAO.registerUser(sc);
                case 2 -> ResourceDAO.addResource(sc);
                case 3 -> ResourceDAO.viewAvailable();
                case 4 -> BorrowDAO.borrowResource(sc);
                case 5 -> BorrowDAO.returnResource(sc);
                case 6 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}

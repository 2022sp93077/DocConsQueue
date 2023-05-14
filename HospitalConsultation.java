import java.io.IOException;
import java.util.Scanner;

public class HospitalConsultation {

    private static ConsultQueue consultQueue = new ConsultQueue();

    public static void main(String[] args) throws IOException {
        int inputChoice = displayMenu();
        while(inputChoice < 5){
            switch (inputChoice){
                case 1: {
                    consultQueue.readFromInputFile();
                    break;
                }
                case 2: {
                    String valueHelp = String.format("Enter the name and age in %s format","(Name,Age)");
                    System.out.println(valueHelp);
                    Scanner inputVal = new Scanner(System.in);
                    String[] patientDetails = inputVal.nextLine().split(",");
                    consultQueue.registerPatient(patientDetails[0],Integer.parseInt(patientDetails[1]));
                    break;
                }
                case 3:
                    consultQueue.nextPatient();
                    break;
                case 4:
                    consultQueue.displayQueue();
                    break;
                default:
                    System.out.println("Invalid input");
            }
            inputChoice = displayMenu();
        }
        System.out.println("Exit");
    }

    private static int displayMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("\n" +
                "Menu Options\n" +
                "1. Import patients from file\n" +
                "2. Enter new patient information\n" +
                "3. Display next patient in line\n" +
                "4. Output current patient waiting list\n" +
                "5. Exit\n");

        //returning user entered choice
        return in.nextInt();
    }

}
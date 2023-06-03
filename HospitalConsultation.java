import java.io.IOException;
import java.util.Scanner;

public class HospitalConsultation {

    private static ConsultQueue consultQueue = new ConsultQueue();

    public static void main(String[] args) throws IOException {
        int inputChoice = displayMenu();
        while(inputChoice < 6){
            switch (inputChoice){
                case 1: {
                    consultQueue.readFromInputFile();
                    break;
                }
                case 2: {
                    consultQueue.getPatientInfo();
                    break;
                }
                case 3:
                    consultQueue.nextPatient();
                    break;
                case 4:
                    consultQueue.displayQueue();
                    break;
                case 5:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Choose correct menu option");
                    break;
            }
            if(inputChoice != 5)
                inputChoice = displayMenu();
            else{
                break;
            }
        }
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
        boolean flag = in.hasNextInt();
        if(flag){
            int val = in.nextInt();
            return val < 6 ? val : 0;
        }
        return 0;
    }
}
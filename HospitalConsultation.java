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
                    consultQueue.getPatientInfo();
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
        try{
            Scanner in = new Scanner(System.in);
            System.out.println("\n" +
                    "Menu Options\n" +
                    "1. Import patients from file\n" +
                    "2. Enter new patient information\n" +
                    "3. Display next patient in line\n" +
                    "4. Output current patient waiting list\n" +
                    "5. Exit\n");
            if(in.hasNextInt()){
                return in.nextInt();
            }
            else{
                throw new Exception("Choose correct menu option");
            }
        }catch(Exception e){
            System.out.println("Choose correct menu option");
        }
        return 0;
    }

}
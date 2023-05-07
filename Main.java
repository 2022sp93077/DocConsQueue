import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final DoublyLinkedList dl = new DoublyLinkedList();
    public static void main(String[] args) throws IOException {
        int inputChoice = displayMenu();
        while(inputChoice != 5){
            switch (inputChoice){
                case 1: {
                    readFromInputFile();
                    break;
                }
                case 2: {
                    registerPatient("abc",20);
                    break;
                }
                case 3:break;
                case 4:
                    dl.iterateForward();
                    break;
                default:
                    System.out.println("Invalid input");
            }
            inputChoice = displayMenu();
        }
        System.out.println("Exit");
    }

    private static String generateId(){
        return UUID.randomUUID().toString();
    }
    private static void readFromInputFile() throws IOException {
        String fileName = "/Users/I528380/IdeaProjects/DocConsQueue/input.txt";
        Path path = Paths.get(fileName);
        Scanner fileScanner = new Scanner(path);
        System.out.println("Reading input file...");

        while(fileScanner.hasNextLine()){
            String patientRecord = fileScanner.nextLine();
            String[] patNameAge = patientRecord.split(",");

            if(patNameAge.length==2){
                //remove whitespace from both ends of a string
                patNameAge[0] = patNameAge[0].trim();
                patNameAge[1] = patNameAge[1].trim();

                registerPatient(patNameAge[0], Integer.parseInt(patNameAge[1]));
            }
            else{
                System.out.println("Invalid record found -"+patientRecord);
            }

        }
        fileScanner.close();
    }

    private static void registerPatient(String name, int age) {
        if(name.length()>0 && (age>0 && age<131)) {
            String patientId = generateId();
            dl.addNodeBack(name, age, patientId);
        }
        else{
            System.out.println("Invalid record found "+name+" "+age);
        }
    }
    private static int displayMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("""
                Menu Options
                1. Import patients from file
                2. Enter new patient information
                3. Display next patient in line
                4. Output current patient waiting list
                5. Exit""");

        //returning user entered choice
        return in.nextInt();
    }
}
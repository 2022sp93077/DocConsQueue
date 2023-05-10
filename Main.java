import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static final DoublyLinkedList dl = new DoublyLinkedList();
    static ArrayList<Integer> arr = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {
        int inputChoice = displayMenu();
        while(inputChoice != 5){
            switch (inputChoice){
                case 1: {
                    readFromInputFile();
                    for(int i=0;i< arr.size();i++)
                        System.out.println(arr.get(i));
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

    private static int generateId(){
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
        return rand_int1;
    }
    private static void readFromInputFile() throws IOException {
        String fileName = "Patient.txt";
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
            int patientId = generateId();
            dl.addNodeBack(name, age, patientId);
            enqueuePatient(patientId);
        }
        else{
            System.out.println("Invalid record found "+name+" "+age);
        }
        dl.iterateForward();
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
    private static void enqueuePatient(int id) {
        int n = arr.size() + 1; //initialise with dll size
        arr.add(id);
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr.get(0);
            arr.set(0,arr.get(i));
            arr.set(i,temp);

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    static void heapify(ArrayList arr, int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n ) { // if (l < n && arr[l] > arr[largest])
            Patient t1 = dl.head;
            Patient t2 = dl.tail;
            while ((t1!= null || t2!= null) && (!(arr.get(l).equals(t1.id)) && !(arr.get(l).equals(t2.id)))) {
                t1 = t1.next;
                t2 = t2.prev;
            }
            int age_1=0;
            if(t1!= null && arr.get(l).equals(t1.id) )
                age_1 = t1.age;
            else if(t2!= null && arr.get(l).equals(t2.id) )
                age_1 = t2.age;
            t1 = dl.head;
            t2 = dl.tail;
            while ((t1!= null || t2!= null) && (!arr.get(largest).equals(t1.id) && !arr.get(largest).equals(t2.id))) {
                t1 = t1.next;
                t2 = t2.prev;
            }
            int age_2 = 0;
            if(t1!= null && arr.get(largest).equals(t1.id) )
                age_2 = t1.age;
            else if(t2!= null && arr.get(largest).equals(t2.id) )
                age_2 = t2.age;
            if(age_1 < age_2)
                largest = l;
        }

        // If right child is larger than largest so far
        if (r < n) { // if (r < n && arr[r] > arr[largest])
            Patient t1 = dl.head;
            Patient t2 = dl.tail;
            while ((t1!= null || t2!= null) && !arr.get(r).equals(t1.id) && !arr.get(r).equals(t2.id)) {
                t1 = t1.next;
                t2 = t2.prev;
            }
            int age_1 = arr.get(r).equals(t1.id) ? t1. age : t2.age;
            t1 = dl.head;
            t2 = dl.tail;
            while ((t1!= null || t2!= null) && !arr.get(largest).equals(t1.id) && !arr.get(largest).equals(t2.id)) {
                t1 = t1.next;
                t2 = t2.prev;
            }
            int age_2 = arr.get(largest).equals(t1.id) ? t1. age : t2.age;
            if (age_1 < age_2)
                largest = r;
        }

        // If largest is not root
        if (largest != i) {
            int swap = (int) arr.get(i);
            arr.set(i,arr.get(largest));
            arr.set(largest,swap);

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}
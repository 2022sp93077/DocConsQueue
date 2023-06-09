import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsultQueue {

    private static PatientRecord patientRecordObj = new PatientRecord();
    private static ArrayList<Integer> patientList = new ArrayList<Integer>();

    private static int id_gen = 0;

    private static int generateId() {
        id_gen++;
        return id_gen;
    }

    protected void readFromInputFile() throws IOException {
        String fileName = "Input.txt";
        Path path = Paths.get(fileName);
        Scanner fileScanner = new Scanner(path);
        System.out.println("Reading input file...");
        int count = 0;
        if (patientRecordObj.head != null) {
            PatientRecord newPatientRecord = new PatientRecord();
            patientRecordObj.removeAll(patientRecordObj.head);
            ArrayList<Integer> sec_list = (ArrayList<Integer>) patientList.clone();
            patientList.removeAll(sec_list);
            patientRecordObj = newPatientRecord;
            patientList = new ArrayList<>();
            id_gen = 0;
        }
        while (fileScanner.hasNextLine()) {
            String patientRecord = fileScanner.nextLine();
            String[] patNameAge = patientRecord.split(",");

            if (patNameAge.length == 2 && patNameAge[0].matches("^[a-zA-Z]*$")) {
                // remove whitespace from both ends of a string
                patNameAge[0] = patNameAge[0].trim();
                patNameAge[1] = patNameAge[1].trim();

                registerPatient(patNameAge[0], Integer.parseInt(patNameAge[1]), false);
                count++;
            } else {
                System.out.println("Invalid record found - " + patientRecord);
            }

        }
        if (count > 0) {
            System.out.println(String.format("Imported %d patients from file", count));
        }
        fileScanner.close();
    }

    public void getPatientInfo() {
        String valueHelp = String.format("Enter the name and age in %s format", "(Name,Age)");
        System.out.println(valueHelp);
        Scanner inputVal = new Scanner(System.in);
        String[] patientDetails = new String[100];
        try {
            patientDetails = inputVal.nextLine().split(",");
            if (patientDetails.length == 2 && patientDetails[0].matches("^[a-zA-Z]*$")) {
                registerPatient(patientDetails[0].trim(), Integer.parseInt(patientDetails[1].trim()), true);
            } else {
                throw new Exception("Invalid Patient data");
            }
        } catch (Exception e) {
            System.out.println("Invalid Patient data");
        }
    }

    private static void registerPatient(String name, int age, boolean flag) {

        if (name.length() > 0 && (age > 0 && age < 131)) {
            int patientId = generateId();
            patientRecordObj.addNodeBack(name, (age * 1000) - patientId, patientId);
            if (flag) {
                System.out.print(String.format("%s added to the queue , current position is ", name));
                enqueuePatient(patientId, true);
            } else {
                enqueuePatient(patientId, false);
            }
        } else {
            System.out.println("Invalid Patient data");
        }
    }

    private static void enqueuePatient(int id, boolean flag) {
        int n = patientList.size() + 1; // initialise with dll size
        patientList.add(id);
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(patientList, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = patientList.get(0);
            patientList.set(0, patientList.get(i));
            patientList.set(i, temp);

            // call max heapify on the reduced heap
            heapify(patientList, i, 0);
        }
        if (flag) {
            System.out.println(patientList.indexOf(id) + 1);
        }
    }

    private static void heapify(ArrayList arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n) { // if (l < n && arr[l] > arr[largest])
            PatientNode head = patientRecordObj.head;
            PatientNode tail = patientRecordObj.tail;
            while ((head != null || tail != null) && (!(arr.get(l).equals(head.id)) && !(arr.get(l).equals(tail.id)))) {
                head = head.next;
                tail = tail.prev;
            }
            int age_1 = 0;
            if (head != null && arr.get(l).equals(head.id))
                age_1 = head.age;
            else if (tail != null && arr.get(l).equals(tail.id))
                age_1 = tail.age;
            head = patientRecordObj.head;
            tail = patientRecordObj.tail;
            while ((head != null || tail != null)
                    && (!arr.get(largest).equals(head.id) && !arr.get(largest).equals(tail.id))) {
                head = head.next;
                tail = tail.prev;
            }
            int age_2 = 0;
            if (head != null && arr.get(largest).equals(head.id))
                age_2 = head.age;
            else if (tail != null && arr.get(largest).equals(tail.id))
                age_2 = tail.age;
            if (age_1 < age_2)
                largest = l;
        }

        // If right child is larger than largest so far
        if (r < n) { // if (r < n && arr[r] > arr[largest])
            PatientNode head = patientRecordObj.head;
            PatientNode tail = patientRecordObj.tail;
            while ((head != null || tail != null) && !arr.get(r).equals(head.id) && !arr.get(r).equals(tail.id)) {
                head = head.next;
                tail = tail.prev;
            }
            int age_1 = 0;
            if (head != null && arr.get(r).equals(head.id))
                age_1 = head.age;
            else if (tail != null && arr.get(r).equals(tail.id))
                age_1 = tail.age;
            head = patientRecordObj.head;
            tail = patientRecordObj.tail;
            while ((head != null || tail != null) && !arr.get(largest).equals(head.id)
                    && !arr.get(largest).equals(tail.id)) {
                head = head.next;
                tail = tail.prev;
            }
            int age_2 = 0;
            if (head != null && arr.get(largest).equals(head.id))
                age_2 = head.age;
            else if (tail != null && arr.get(largest).equals(tail.id))
                age_2 = tail.age;
            if (age_1 < age_2)
                largest = r;
        }

        // If largest is not root
        if (largest != i) {
            int swap = (int) arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);

            // Recursively heapify the affected subtree
            heapify(arr, n, largest);
        }
    }

    private static void dequeuePatient() {
        int n = patientList.size();
        if (n > 0) {
            int lastElement = patientList.get(n - 1);
            PatientNode temp = patientRecordObj.head;
            while (temp != null) {
                if (temp.id == patientList.get(0)) {
                    break;
                }
                temp = temp.next;
            }

            // removing from DLL to maintain consistent search time
            patientRecordObj.removeNodeByValue(temp);

            patientList.set(0, lastElement);
            patientList.remove(n - 1);
            n = patientList.size();
            for (int i = n / 2 - 1; i >= 0; i--)
                heapify(patientList, n, i);
            for (int i = n - 1; i >= 0; i--) {
                // Move current root to end
                int tempOne = patientList.get(0);
                patientList.set(0, patientList.get(i));
                patientList.set(i, tempOne);

                // call max heapify on the reduced heap
                heapify(patientList, i, 0);
            }
        }
    }

    public void nextPatient() {
        if (patientList.size() > 0) {
            PatientNode temp = patientRecordObj.head;
            PatientNode nextPatientNode = patientRecordObj.findPatient(temp, patientRecordObj.tail, patientList.get(0));
            System.out.println(String.format("%s,%d", nextPatientNode.name, nextPatientNode.id));
            dequeuePatient();
        } else {
            System.out.println("No Patients to attend");
        }
    }

    protected static void displayQueue() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));
        if (patientList.size() > 0) {
            PatientNode temp = patientRecordObj.head;
            int seq = 1;
            for (int i = 0; i < patientList.size(); i++) {
                PatientNode nextPatientNode = patientRecordObj.findPatient(temp, patientRecordObj.tail,
                        patientList.get(i));
                // System.out.printf("%d, %s, %d, %d\n%n", seq, nextPatientNode.name,
                // nextPatientNode.id,
                // nextPatientNode.age);
                writer.write(String.format("%d, %s, %d, %d\n", seq, nextPatientNode.name, nextPatientNode.id,
                        (nextPatientNode.age + nextPatientNode.id) / 1000));
                seq++;
            }
        } else {
            writer.write("");
            System.out.println("No Patients to attend");
        }
        System.out.println("Consultation queue output to file successfully.");
        writer.close();
    }
}

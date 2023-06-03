import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsultQueue {

    private static PatientRecord dl = new PatientRecord();
    private static ArrayList<Integer> arr = new ArrayList<Integer>();
    private static int id_gen = 0;
    private static int generateId(){
        id_gen++;
        return id_gen;
    }

    protected static void readFromInputFile() throws IOException {
        String fileName = "Input.txt";
        Path path = Paths.get(fileName);
        Scanner fileScanner = new Scanner(path);
        System.out.println("Reading input file...");
        int count = 0;
        if(dl.head!=null) {
            PatientRecord newPatientRecord = new PatientRecord();
            dl.removeAll(dl.head);
            ArrayList sec_list = (ArrayList)arr.clone();
            arr.removeAll(sec_list);
            dl = newPatientRecord;
            arr = new ArrayList<>();
        }
        while(fileScanner.hasNextLine()){
            String patientRecord = fileScanner.nextLine();
            String[] patNameAge = patientRecord.split(",");

            if(patNameAge.length==2){
                //remove whitespace from both ends of a string
                patNameAge[0] = patNameAge[0].trim();
                patNameAge[1] = patNameAge[1].trim();

                registerPatient(patNameAge[0], Integer.parseInt(patNameAge[1]),false);
                count++;
            }
            else{
                System.out.println("Invalid record found -"+patientRecord);
            }

        }
        if(count>0){
            System.out.println(String.format("Imported %d patients from file",count));
        }
        fileScanner.close();
    }

     public void getPatientInfo(){
        String valueHelp = String.format("Enter the name and age in %s format","(Name,Age)");
        System.out.println(valueHelp);
        Scanner inputVal = new Scanner(System.in);
        String[] patientDetails = new String[100];
        try{
            patientDetails = inputVal.nextLine().split(",");
            if(patientDetails.length==2 && patientDetails[0].matches("^[a-zA-Z]*$")) {
                registerPatient(patientDetails[0].trim(),Integer.parseInt(patientDetails[1].trim()),true);
            }
            else{
                throw new Exception("Invalid Patient data");
            }
        }catch (Exception e) {
            System.out.println("Invalid Patient data");
        }
    }

    private static void registerPatient(String name, int age,boolean flag) {

        if(name.length()>0 && (age>0 && age<131)) {
            int patientId = generateId();
            dl.addNodeBack(name, age, patientId);
            if(flag){
                System.out.print(String.format("%s added to the queue , current position is ",name));
                enqueuePatient(patientId,true);
            }else{
                enqueuePatient(patientId,false);
            }
        }
        else{
            System.out.println("Invalid Patient data");
        }
    }
    private static void enqueuePatient(int id,boolean flag) {
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
        if(flag){
            System.out.println(arr.indexOf(id)+1);
        }
    }

    private static void heapify(ArrayList arr, int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n ) { // if (l < n && arr[l] > arr[largest])
            PatientNode t1 = dl.head;
            PatientNode t2 = dl.tail;
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
            PatientNode t1 = dl.head;
            PatientNode t2 = dl.tail;
            while ((t1!= null || t2!= null) && !arr.get(r).equals(t1.id) && !arr.get(r).equals(t2.id)) {
                t1 = t1.next;
                t2 = t2.prev;
            }
            int age_1 = 0;
            if(t1!= null && arr.get(r).equals(t1.id) )
                age_1 = t1.age;
            else if(t2!= null && arr.get(r).equals(t2.id) )
                age_1 = t2.age;
            t1 = dl.head;
            t2 = dl.tail;
            while ((t1!= null || t2!= null) && !arr.get(largest).equals(t1.id) && !arr.get(largest).equals(t2.id)) {
                t1 = t1.next;
                t2 = t2.prev;
            }
            int age_2 = 0;
            if(t1!= null && arr.get(largest).equals(t1.id) )
                age_2 = t1.age;
            else if(t2!= null && arr.get(largest).equals(t2.id) )
                age_2 = t2.age;
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

    private static void dequeuePatient(){
        int n = arr.size();
        if(n>0){
            int lastElement = arr.get(n - 1);
            PatientNode temp = dl.head;
            while(temp != null){
                if(temp.id == arr.get(0)){
                    break;
                }
                temp = temp.next;
            }
            dl.removeNodeByValue(temp);
            arr.set(0,lastElement);
            arr.remove(n-1);
            n = arr.size();
            for (int i = n / 2 - 1; i >= 0; i--)
                heapify(arr, n, i);
            for (int i = n - 1; i >= 0; i--) {
                // Move current root to end
                int tempOne = arr.get(0);
                arr.set(0,arr.get(i));
                arr.set(i,tempOne);

                // call max heapify on the reduced heap
                heapify(arr, i, 0);
            }
        }
    }

    public void nextPatient(){
        if(arr.size()>0){
            PatientNode temp = dl.head;
            PatientNode nextPatientNode = dl.findPatient(temp,arr.get(0));
            System.out.println(String.format("%s,%d",nextPatientNode.name,nextPatientNode.id));
            dequeuePatient();
        }
        else{
            System.out.println("No Patients to attend");
        }
    }

    protected static void displayQueue() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));
        PatientNode temp = dl.head;
        int seq = 1;
        for(int i=0;i<arr.size();i++){
            PatientNode nextPatientNode = dl.findPatient(temp,arr.get(i));
            System.out.printf("%d, %s, %d, %d\n%n",seq,nextPatientNode.name,nextPatientNode.id,nextPatientNode.age);
            writer.write(String.format("%d, %s, %d, %d\n",seq,nextPatientNode.name,nextPatientNode.id,nextPatientNode.age));
            seq++;
        }
        writer.close();
    }
}

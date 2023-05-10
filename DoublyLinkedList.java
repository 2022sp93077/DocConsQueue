public class DoublyLinkedList {
    Patient head = null;
    Patient tail = null;

    // Function to add a node in the front of doubly linked list
    public void addNodeFront(String name,int age,int id) {

        Patient temp = new Patient(name, age, id);

        if(head != null )
        {
            head.prev = temp;
        }
        head = temp;

        if(tail == null)
        {
            head = temp;
            tail = temp;
        }
        System.out.println("New node added");
    }

    // Function to add a node at the back of doubly linked list
    public void addNodeBack(String name,int age,int id) {

        Patient temp = new Patient(name, age, id);

        if(tail != null)
        {
            temp.prev = tail;
            tail.next = temp;
            tail = temp;
        }

        if(head == null)
        {
            head = temp;
            tail = temp;
        }
        System.out.println("New node added:");
    }

    public void iterateForward(){
        Patient temp = head;

        while(temp != null){
            System.out.println(temp.name + "," + temp.id);
            temp = temp.next;
        }
    }
    public void iterateBackward(){
        Patient temp = tail;

        while(temp != null){
            System.out.println(temp.name);
            temp = temp.prev;
        }
    }

    public String removeNodeFront() {
        Patient temp = head;

        head = head.next;
        head.prev = null;

        System.out.println("deleted: " + temp.name);
        return temp.name;
    }

    public String removeNodeBack() {

        Patient temp = tail;

        tail = tail.prev;
        tail.next = null;

        System.out.println("deleted: " + temp.name);
        return temp.name;
    }

}


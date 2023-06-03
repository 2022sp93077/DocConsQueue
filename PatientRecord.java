public class PatientRecord {
    PatientNode head = null;
    PatientNode tail = null;

    // Function to add a node in the front of doubly linked list
    public void addNodeFront(String name,int age,int id) {

        PatientNode temp = new PatientNode(name, age, id);

        if(head != null )
        {
            temp.next = head;
            head.prev = temp;
            head = temp;
        }

        if(tail == null)
        {
            head = temp;
            tail = temp;
        }
        System.out.println("New node added");
    }

    // Function to add a node at the back of doubly linked list
    public void addNodeBack(String name,int age,int id) {

        PatientNode temp = new PatientNode(name, age, id);

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
    }

    public void iterateForward(){
        PatientNode temp = head;

        while(temp != null){
            System.out.println(temp.name + "," + temp.id);
            temp = temp.next;
        }
    }
    public void iterateBackward(){
        PatientNode temp = tail;

        while(temp != null){
            System.out.println(temp.name);
            temp = temp.prev;
        }
    }

    public String removeNodeFront() {
        PatientNode temp = head;
        if(head.next != null){
            head = head.next;
            head.prev = null;
            System.out.println("deleted: " + temp.name);
            return temp.name;
        }
        else {
            head = null;
            System.out.println("deleted: " + temp.name);
            return temp.name;
        }
    }

    public String removeNodeBack() {

        PatientNode temp = tail;

        tail = tail.prev;
        tail.next = null;

        System.out.println("deleted: " + temp.name);
        return temp.name;
    }

    public void removeNodeByValue(PatientNode del){
        if (head == null || del == null) {
            return;
        }

        if (head == del) {
            head = del.next;
        }

        if(tail == del){
            tail = tail.prev;
        }

        if (del.next != null) {
            del.next.prev = del.prev;
        }

        if (del.prev != null) {
            del.prev.next = del.next;
        }
    }

    public void removeAll(PatientNode curr_node){
        while (curr_node != null) {
            PatientNode nextNode = curr_node.next;
            curr_node.next = null;
            curr_node.prev = null;
            curr_node = nextNode;
        }
    }

    public PatientNode findPatient(PatientNode node, int id){
        while(node!=null){
            if(node.id == id){
                return node;
            }
            node = node.next;
        }
        return null;
    }
}


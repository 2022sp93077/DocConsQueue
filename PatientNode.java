public class PatientNode {
    public int age, id;
    public String name;

    public PatientNode next, prev;

    public PatientNode(String name, int age, int id) {
        this.age = age;
        this.name = name;
        this.id = id;
    }
}

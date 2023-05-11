public class Patient {
    public int age, id;
    public String name;

    public Patient next, prev;

    public Patient(String name,int age,int id) {
        this.age = age;
        this.name = name;
        this.id = id;
    }
}

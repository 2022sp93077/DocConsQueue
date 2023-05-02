public class Patient {
    public int age;
    public String name,id;

    public Patient next, prev;

    public Patient(String name,int age,String id) {
        this.age = age;
        this.name = name;
        this.id = id;
    }
}

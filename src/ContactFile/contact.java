package ContactFile;

public class contact {
    String name;
    String phoneNumber;

    public contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + " | " + phoneNumber;
    }

}

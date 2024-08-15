package application.aop;

public class Customer implements CustomerInterface {
    private String name;

    public void setName(String name) {
        System.out.println("setting a name to " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

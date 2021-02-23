package model;

public class Person {
    //Variables
    private String name;
    private String address;
    private String phoneNr;


    //Constructor
    public Person() {
        //do nothing
    }

    public Person(String name, String address, String phoneNr) {
        this.name = name;
        this.address = address;
        this.phoneNr = phoneNr;
    }


    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }


    //Methods
    @Override
    public String toString() {
        return this.name + ";" + this.address + ";" + this.phoneNr;
    }
}

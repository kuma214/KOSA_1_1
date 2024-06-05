package sw1.hosp;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String name;
    private String birthDate;
    private String address;
    private String contact;

    public Patient(int id, String name, String birthDate, String address, String contact) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}

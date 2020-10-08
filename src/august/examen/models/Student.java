package august.examen.models;

public class Student {
    private String admNumber;
    private String firstname;
    private String lastname;
    private String surname;
    private String schoolEmail;
    private String phoneNumber;

    public Student(){

    }

    public Student(String admNumber, String firstname, String lastname, String surname, String schoolEmail, String phoneNumber) {
        this.admNumber = admNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.surname = surname;
        this.schoolEmail = schoolEmail;
        this.phoneNumber = phoneNumber;
    }

    public String getAdmNumber() {
        return admNumber;
    }

    public void setAdmNumber(String admNumber) {
        this.admNumber = admNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

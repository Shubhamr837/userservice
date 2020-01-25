package co.hackerscode.userservice.userservice.models;

public class User {
    private String firstname = null;
    private String lastname = null;
    private String emailId = null;
    private String password = null;
    private int userId = 0;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString(){
        return "{ ID = " + userId + "First name" + firstname + "Last name" + lastname + "Email id" + emailId + "}";
    }
}

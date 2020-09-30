package com.onethousandprojects.appoeira.serverStuff.signupTransaction;

public class ClientSignupRequest {
    private String firstName;
    private String lastName;
    private String apelhido;
    private String email;
    private String password;
    private Integer rank;

    public ClientSignupRequest(String firstName, String lastName, String apelhido,
                               String email, String password, Integer rank) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.apelhido = apelhido;
        this.email = email;
        this.password = password;
        this.rank = rank;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getApelhido() {
        return apelhido;
    }

    public void setApelhido(String apelhido) {
        this.apelhido = apelhido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}

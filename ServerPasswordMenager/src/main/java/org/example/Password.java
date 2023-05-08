package org.example;

public class Password {
    private String userName;
    private String password;
    private String nomeServizio;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Password() {}

    public Password(String userName, String password, String nomeServizio) {
        this.userName = userName;
        this.password = password;
        this.nomeServizio = nomeServizio;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNomeServizio() {
        return nomeServizio;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNomeServizio(String nomeServizio) {
        this.nomeServizio = nomeServizio;
    }

}

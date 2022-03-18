package business;

public class Client extends User{
    private String name;
    private int clientID;

    public Client(String username, String password, String role, int clientID, String name) {
        super(username, password, role);
        this.clientID = clientID;
        this.name = name;
    }

    public String toString(){
        return "Client " + name + " has ClientID " + clientID + ".";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}

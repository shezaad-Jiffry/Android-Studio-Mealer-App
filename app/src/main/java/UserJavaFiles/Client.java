package UserJavaFiles;

import java.io.Serializable;

public class Client extends User {
    private CreditCard creditCardInfo;

    //Creates Client from Super Class User, adds creditCardInfo attribute (class is set up but can be null for now
    public Client(String firstName,String lastName,String email,String password,String address, CreditCard creditCardInfo){
        super(firstName,lastName,email,password,address);
        this.creditCardInfo = creditCardInfo;
    }
    //getter and setter methods
    public CreditCard getCreditCardInfo() {
        return this.creditCardInfo;
    }

    public void setCreditCardInfo(CreditCard creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public boolean equalsTo(Client client){
        return this.getAddress() == client.getAddress() && this.getFirstName() == client.getFirstName() && this.getLastName() == client.getLastName()
                && this.getEmail() == client.getEmail() && this.getPassword() == client.getPassword() && this.creditCardInfo.equalsTo(client.getCreditCardInfo());
    }
}
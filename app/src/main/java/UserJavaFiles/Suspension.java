package UserJavaFiles;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A seperate class that represents a cooks suspension state. gives the Date till they are banned
 * or if they are perma banned. if the banned until variable is set to OKAY that means the account
 * is not banned!
 */
public class Suspension implements Serializable {
    //instance variables
    private boolean perma;
    private Date ban;
    private String bannedUntil;
    //This is a way to change the date variable into string for firebase implementation
    //make VERY sure that this variable is private static causes errors other wise (firebase moment)
    private static SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public Suspension(boolean perma, Date ban)
    {
        this.perma = perma;
        //changes the date into string
        if(perma == true){
            ban = null;
        }
        //means they are fine so set their status as OKAY
        else{
            try{
                this.bannedUntil = ISO_8601_FORMAT.format(ban);
            }catch(Exception e){
                this.bannedUntil = "OKAY";
            }
        }
    }
    //constructs with banned until instead of date
    public Suspension(boolean perma, String bannedUntil) throws ParseException, Exception {
        Exception exception;
        this.perma = perma;
        checkSuspension(bannedUntil);
        this.bannedUntil = String.valueOf(ISO_8601_FORMAT.parse(bannedUntil));
        this.ban = null;
    }
    public Suspension(){}

    /**
     * helper method for constructor to check the validity of the suspension date
     * @param bannedUntil when the user is banned until
     * @throws Exception throws this exception if it cannot parse properly or if date is older
     * than current date
     */
    private void checkSuspension( String bannedUntil) throws Exception{
        Date currentDate = new Date();
        Date suspensionDate = (ISO_8601_FORMAT.parse(bannedUntil));
        if(currentDate.after(suspensionDate)){
            throw new Exception();
        }
    }
    //getters and setters
    public String getBannedUntil() {
        return this.bannedUntil;
    }
    public  Boolean getPerma(){
        return perma;
    }
    public void setPerma(boolean perma) {
        this.perma = perma;
    }
    public void setBannedUntil(String bannedUntil) {
        this.bannedUntil = bannedUntil;
    }
    public Date getBan(){return this.ban;}
    public void setBan(Date ban){this.ban = ban;}
    //equals to method
    public boolean equalsTo(Suspension status){
        return (this.getPerma() == status.getPerma() && this.getBannedUntil() == status.getBannedUntil())
                || (this.getPerma() == status.getPerma() && this.getBan() == status.getBan());
    }
}

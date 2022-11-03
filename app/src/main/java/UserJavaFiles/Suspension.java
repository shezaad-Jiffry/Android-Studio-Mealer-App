package UserJavaFiles;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Suspension {
    private boolean perma;
    private Date ban;
    private String bannedTill;
    //This is a way to change the date variable into string for firebase implementation
    private SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");

    public Suspension(boolean perma, Date ban)
    {
        this.perma = perma;
        //changes the date into string
        this.bannedTill = ISO_8601_FORMAT.format(ban);
    }

    public String getBannedTill() {
        return this.bannedTill;
    }

    //returns true if the cook is permanently banned false otherwise
    public boolean getPerma()
    {
        return this.perma;
    }
}

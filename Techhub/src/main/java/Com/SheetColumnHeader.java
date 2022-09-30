package Com;
import java.util.Date;

public class SheetColumnHeader {

    String firstname;
    String lastname;
    String emailId;
    String gender;
    String number;
    Date date;
    String expectedResult;

    public SheetColumnHeader() {
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) { this.firstname = firstname; }
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
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getExpectedResult() {
        return expectedResult;
    }
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }
    @Override
    public String toString() {
        return "SheetColumnHeader{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailId='" + emailId + '\'' +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                ", Date='" + date + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                '}';
    }
}

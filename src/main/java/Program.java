import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        var leaveRepo = new LeavesRepository();

        var leaveManager = new LeaveManager(leaveRepo);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = parseDate("2021-02-02");

        leaveManager.applyLeave("Nibin", "Manav", dateObject, dateObject);

        System.out.println("Pending leaves: " + leaveManager.getTotalPendingLeaves("Manav"));
        System.out.println("Approved Leaves: " + leaveManager.getTotalApprovedLeaves("Manav"));
        System.out.println("Cancelled Leaves: " + leaveManager.getTotalCancelledLeaves("Manav"));

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}

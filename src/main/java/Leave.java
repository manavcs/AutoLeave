import java.time.LocalDate;
import java.util.Date;

class Leave {
    String manager;
    String employee;
    String reason;
    LocalDate date;
    LeaveStatus status;

    Leave(String manager, String employee, LocalDate date, String reason)
    {
        this.manager = manager;
        this.employee = employee;
        this.date = date;
        this.reason = reason;
    }
}

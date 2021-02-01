import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

class LeavesRepository {
    private ArrayList<Leave> leaves;

    LeavesRepository()
    {
        leaves = new ArrayList<Leave>();
    }

    Leave[] getLeaves(String employee)
    {
        return (Leave[])this.leaves.stream().filter(l->l.employee == employee).toArray();
    }

    void addLeave(Leave leave)
    {

        leaves.add(leave);
    }

    Leave[] getLeaves(String employee, LeaveStatus leaveStatus, Date afterDate)
    {
        LocalDate afDate = LocalDate.parse(afterDate.toString());

        return (Leave[]) leaves.stream().filter(l->l.employee == employee
                && l.status == leaveStatus
                && (l.date.isAfter(afDate))).toArray();
    }

    Leave[] getLeaves(String employee, LeaveStatus leaveStatus)
    {
        return (Leave[]) leaves.stream().filter(l->l.employee == employee
                && l.status == leaveStatus).toArray();
    }

}

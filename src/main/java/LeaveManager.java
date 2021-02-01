import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

class LeaveManager {
    private  LeavesRepository leavesRepository;

    LeaveManager(LeavesRepository leavesRepository)
    {
        this.leavesRepository = leavesRepository;
    }

    void applyLeave(String manager, String employee, Date fromDate, Date toDate)
    {
        var noOfDays = Utils.getNoOfDays(fromDate, toDate);

        System.out.println(String.format("Applying %d day leave by %s to %s",
                noOfDays, manager, employee));

        var valid = validateLeaves(manager, employee, fromDate, toDate);

        if(!valid)
        {
            return;
        }

        var leaveStatus = getLeaveStatus(fromDate, toDate);

        for(int i = 0; i< noOfDays; i++)
        {
            LocalDate dt1 = Utils.getLocalDate(fromDate);

            var leaveDate = dt1.plusDays(i);

            var leave = new Leave(manager, employee, leaveDate, "");

            leave.status = leaveStatus;

            System.out.println(String.format("Leave is %s", leave.status.toString()));

            this.leavesRepository.addLeave(leave);

            System.out.println("Leave applied successfully");
        }

    }

    void cancelLeaves(String employee, Date fromDate, Date toDate)
    {
        LocalDate frmDate = Utils.getLocalDate(fromDate);
        LocalDate tDate = Utils.getLocalDate(toDate);

        if(fromDate.before(new Date()))
        {
            System.out.println("Can not cancel the leaves with past date");
            return;
        }

        System.out.println(String.format("Cancelling leave for %s from %s to %s ", employee,
                fromDate.toString(), toDate.toString()));

        var leaves = this.leavesRepository.getLeaves(employee);

        var appliedLeaves = (Leave[]) Arrays.stream(leaves)
                .filter(l-> (l.date.isAfter(frmDate) || l.date.equals(frmDate))
                            && (l.date.isBefore(tDate) || l.date.equals(tDate))).toArray();

        if(appliedLeaves == null || appliedLeaves.length == 0)
        {
            System.out.println("Error! Leave does not exists");
            return;
        }
        else {

            for(Leave lv : appliedLeaves)
            {
                if(lv.status == LeaveStatus.Cancelled)
                {
                    System.out.println("Leave is already cancelled");
                    return;
                }
                else{
                    lv.status = LeaveStatus.Cancelled;
                }
            }

        }
    }

    void cancelLeave(String employee, Date date)
    {
        if(date.before(new Date()))
        {
            System.out.println("Can not cancel the leaves with past date");
            return;
        }

        System.out.println(String.format("Cancelling leave for %s on %s", employee, date.toString()));

        var leaves = this.leavesRepository.getLeaves(employee);

        var leaveResult = Arrays.stream(leaves).filter(l->l.date.equals(date)).findFirst();

        if(!leaveResult.isPresent())
        {
            System.out.println("Error! Leave does not exists");
            return;
        }
        else {
            var leave = leaveResult.get();

            if(leave.status == LeaveStatus.Cancelled)
            {
                System.out.println("Leave is already cancelled");
                return;
            }
            else{
                leave.status = LeaveStatus.Cancelled;
            }
        }
    }

    int getTotalPendingLeaves(String employee)
    {
        var leaves = this.leavesRepository.getLeaves(employee, LeaveStatus.PendingForApproval);

        return leaves.length;
    }

    int getTotalCancelledLeaves(String employee)
    {
        var leaves = this.leavesRepository.getLeaves(employee, LeaveStatus.Cancelled);

        return leaves.length;
    }

    int getTotalApprovedLeaves(String employee)
    {
        var leaves = this.leavesRepository.getLeaves(employee, LeaveStatus.Approved);

        return leaves.length;
    }

    private LeaveStatus getLeaveStatus (Date fromDate, Date toDate) {
        if(Utils.getNoOfDays(fromDate, toDate) >= 7)
        {
            return LeaveStatus.Approved;
        }
        else{
            return LeaveStatus.PendingForApproval;
        }
    }

    private boolean validateLeaves(String manager, String employee, Date fromDate, Date toDate) {
        if(manager == null || manager.isEmpty() ||
                employee == null || employee.isEmpty()
                || fromDate == null || toDate == null)
        {
            System.out.println("Invalid leave!");
            return false;
        }
        if(fromDate.after(toDate))
        {
            System.out.println("from date in a leave can not be greater than to date");
            return false;
        }
        if(fromDate.before(new Date()))
        {
            System.out.println("Leaves can not be applied to past dates");
            return false;
        }

        return true;
    }
}

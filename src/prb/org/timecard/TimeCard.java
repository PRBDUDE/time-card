package prb.org.timecard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class TimeCard {

    private int employeeId = 0;
    private LocalDateTime punchIn = null;
    private LocalDateTime punchOut = null;
    private boolean isOverlapped = false;

    public TimeCard() {

    }

    public TimeCard(int empId) {
        this.employeeId = empId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getYear() {
        return punchIn.getYear();
    }

    public int getMonth() {
        return punchIn.getMonthValue();
    }

    /**
     *
     * @param employeeId int
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getPunchIn() {
        return punchIn;
    }

    /**
     *
     * @param punchIn LocalDateTime
     */
    public void setPunchIn(LocalDateTime punchIn) {
        if (this.punchOut != null) {
            if (this.punchOut.isBefore(punchIn)) {
                throw new RuntimeException("Punch In time must be before Punch Out time - Employee ID: " + this.employeeId);
            }
        }
        this.punchIn = punchIn;
    }

    public LocalDateTime getPunchOut() {
        return punchOut;
    }

    /**
     *
     * @param punchOut LocalDateTime
     */
    public void setPunchOut(LocalDateTime punchOut) {
        if (this.punchIn != null) {
            if (this.punchIn.isAfter(punchOut)) {
                throw new RuntimeException("Punch In time must be before Punch Out time - Employee ID: " + this.employeeId);
            }
        }
        this.punchOut = punchOut;
    }

    public boolean isOverlapped() {
        return this.isOverlapped;
    }

    /**
     *
     * @param isOverlapped boolean
     */
    public void setOverlapped(boolean isOverlapped) {
        this.isOverlapped = isOverlapped;
    }

    /**
     *
     * @param tcard TimeCard
     */
    public void compareOverlap(TimeCard tcard) {
        if (this == tcard || this.employeeId != tcard.employeeId) {
            if (this.employeeId != tcard.employeeId) {
                System.err.println("Different Employee IDs in TimeCard");
            }
            if (this == tcard) {
                System.err.println("Same TimeCard");
            }
            return;
        }

        if (this.employeeId == tcard.getEmployeeId()) {
            boolean isOverlapFound;
            isOverlapFound = this.isPunchInOverlapped(tcard) || this.isPunchOutOverlapped(tcard);
            if (isOverlapFound) {
                this.isOverlapped = true;
                tcard.setOverlapped(true);
            }
        }
    }

    /**
     *
     * @param tcard TimeCard
     * @return boolean
     */
    private boolean isPunchInOverlapped(TimeCard tcard) {
        return ((this.punchIn.isBefore(tcard.getPunchIn()) &&
                (this.punchOut.isAfter(tcard.getPunchIn())))
                || this.punchIn.isEqual(tcard.getPunchIn()));
    }

    /**
     *
     * @param tcard TimeCard
     * @return boolean
     */
    private boolean isPunchOutOverlapped(TimeCard tcard) {
        return ((this.punchIn.isBefore(tcard.getPunchOut()) &&
                (this.punchOut.isAfter(tcard.getPunchOut())))
                || this.punchOut.isEqual(tcard.getPunchOut()));
    }

    public int getHoursWorked() {
        return this.punchOut.getHour() - this.punchIn.getHour();
    }

    public int getMinutesWorked() {
        return this.punchOut.getMinute() - this.punchIn.getMinute();
    }

    public Double getTotalHoursWorked() {
        double totalMinutes = (this.getHoursWorked() * 60) + this.getMinutesWorked();
        int hours = (int) totalMinutes / 60;
        int minutesLeftOver = (int) totalMinutes - (hours * 60);
        int beyond = minutesLeftOver % 15;
        BigDecimal bd = BigDecimal.valueOf(((totalMinutes - beyond) / 60));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

package prb.org.timecard;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class SortByPunchInTest {

    final SortByPunchIn comp = new SortByPunchIn();

    @Test
    public void testAYearBeforeBYear() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2024, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(-1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAYearAfterBYear() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2024, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAEqualsB() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(0, comp.compare(tcA, tcB));
    }

    @Test
    public void testAMonthBeforeBMonth() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 3, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(-1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAMonthAfterBMonth() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 3, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(1, comp.compare(tcA, tcB));
    }

    @Test
    public void testADayBeforeBDay() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 3, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(-1, comp.compare(tcA, tcB));
    }

    @Test
    public void testADayAfterBDay() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 3, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAHourBeforeBHour() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 7, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(-1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAHourAfterBHour() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 7, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAMinuteBeforeBMinute() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 1));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(-1, comp.compare(tcA, tcB));
    }

    @Test
    public void testAMinuteAfterBMinute() {
        TimeCard tcA = new TimeCard(24);
        TimeCard tcB = new TimeCard(24);
        try {
            tcA.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 1));
        } catch (Exception e) {
            System.err.println("tcA Weekend Date");
        }
        try {
            tcB.setPunchIn(LocalDateTime.of(2025, 4, 4, 8, 0));
        } catch (Exception e) {
            System.err.println("tcB Weekend Date");
        }
        assertEquals(1, comp.compare(tcA, tcB));
    }

}

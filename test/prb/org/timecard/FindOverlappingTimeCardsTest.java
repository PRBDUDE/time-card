package prb.org.timecard;

import org.junit.Test;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FindOverlappingTimeCardsTest {
    DecimalFormat commaFormat = new DecimalFormat("#,###,###,###");

    @Test
    public void testFindManyOverlappingTimeCards() {
        int year = 2025;

        ArrayList<TimeCard> inputList = new ArrayList<>(
                (new TimeCardBuilder(year)).buildTimeCards(23_257));

        ArrayList<TimeCard> expectedOverlappedCards = new ArrayList<>();
        OverlappedCardBuilder overlappedBuilder = new OverlappedCardBuilder(expectedOverlappedCards);

        overlappedBuilder.add(9_037,
                LocalDateTime.of(year, 1, 9, 12, 10),
                LocalDateTime.of(year, 1, 9, 17, 7));

        overlappedBuilder.add(2_135,
                LocalDateTime.of(year, 4, 9, 17, 1),
                LocalDateTime.of(year, 4, 10, 2, 45));

        overlappedBuilder.add(4,
                LocalDateTime.of(year, 10, 1, 12, 15),
                LocalDateTime.of(year, 10, 1, 17, 5));

        overlappedBuilder.add(1_356,
                LocalDateTime.of(year, 7, 1, 17, 0),
                LocalDateTime.of(year, 7, 2, 3, 39));

        overlappedBuilder.add(854,
                LocalDateTime.of(year, 2, 3, 8, 45),
                LocalDateTime.of(year, 2, 3, 12, 6));

        overlappedBuilder.add(945,
                LocalDateTime.of(year, 9, 2, 9, 45),
                LocalDateTime.of(year, 9, 2, 17, 6));

        overlappedBuilder.add(400,
                LocalDateTime.of(year, 6, 9, 8, 45),
                LocalDateTime.of(year, 6, 9, 12, 59));

        overlappedBuilder.add(698,
                LocalDateTime.of(year, 3, 7, 12, 40),
                LocalDateTime.of(year, 3, 7, 17, 6));

        overlappedBuilder.add(446,
                LocalDateTime.of(year, 6, 9, 8, 45),
                LocalDateTime.of(year, 6, 9, 13, 9));

        overlappedBuilder.add(45,
                LocalDateTime.of(year, 5, 8, 10, 45),
                LocalDateTime.of(year, 5, 8, 17, 6));

        overlappedBuilder.add(235,
                LocalDateTime.of(year, 11, 10, 8, 0),
                LocalDateTime.of(year, 11, 10, 12, 50));

        overlappedBuilder.add(823,
                LocalDateTime.of(year, 12, 9, 10, 4),
                LocalDateTime.of(year, 12, 9, 17, 6));

        overlappedBuilder.add(4_035,
                LocalDateTime.of(year, 5, 9, 7, 45),
                LocalDateTime.of(year, 5, 9, 17, 6));

        overlappedBuilder.add(8_465,
                LocalDateTime.of(year, 7, 10, 1, 45),
                LocalDateTime.of(year, 7, 10, 17, 6));

        inputList.addAll(expectedOverlappedCards);

        System.out.println(commaFormat.format(inputList.size()) + " time cards were generated");

        FindOverlappingTimeCards overlapped = new FindOverlappingTimeCards(inputList);

        ArrayList<TimeCard> overlappedCards = overlapped.overLappingTimeCards();

        assertTrue(overlappedCards.containsAll(expectedOverlappedCards));
    }

    private record TimeCardBuilder(int year) {

        public ArrayList<TimeCard> buildTimeCards(int maxEmployees) {
            ArrayList<TimeCard> timeCardList = new ArrayList<>();

            for (int employeeID = 1; employeeID < maxEmployees; employeeID++) {
                for (int month = 1; month <= 12; month++) {
                    timeCardList.addAll(buildMonth(employeeID, month));
                }
            }

            return timeCardList;
        }

        private ArrayList<TimeCard> buildMonth(int empID, int month) {
            ArrayList<TimeCard> cards = new ArrayList<>();
            int daysInMonth = (month == 2) ? 28 : 30;
            for (int monthDay = 1; monthDay < daysInMonth; monthDay++) {
                LocalDateTime checkIn = LocalDateTime.of(this.year, month, monthDay, 8, 0);
                if (checkIn.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        checkIn.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                }
                try {
                    cards.add(createTimeCard(empID, checkIn, LocalDateTime.of(this.year, month, monthDay, 11, 45)));
                    cards.add(createTimeCard(empID, LocalDateTime.of(this.year, month, monthDay, 12, 45), LocalDateTime.of(this.year, month, monthDay, 17, 6)));
                } catch (Exception e) {
                    System.err.println("ERROR: " + e);
                }
            }
            return cards;
        }

        public static TimeCard createTimeCard(int empID, LocalDateTime timeIn, LocalDateTime timeOut) throws Exception {
            if (timeIn.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    timeIn.getDayOfWeek() == DayOfWeek.SUNDAY) {
                throw new Exception(timeIn.getDayOfWeek() + " Unable to create weekend time card!");
            }
            TimeCard newTimeCard = new TimeCard(empID);
            newTimeCard.setPunchIn(timeIn);
            newTimeCard.setPunchOut(timeOut);
            return newTimeCard;
        }
    }

    private record OverlappedCardBuilder(ArrayList<TimeCard> timeCards) {

        public void add(int empID, LocalDateTime timeIn, LocalDateTime timeOut) {
            try {
                this.timeCards.add(TimeCardBuilder.createTimeCard(empID, timeIn, timeOut));
            } catch (Exception e) {
                System.err.println("ERROR: " + empID + " - " + e);
            }
        }
    }
}

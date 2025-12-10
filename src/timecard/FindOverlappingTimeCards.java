package timecard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class FindOverlappingTimeCards {

    private final TreeMap<Integer, TreeMap<LocalDateTime, TimeCard>> empTimeCards = new TreeMap<>();

    public FindOverlappingTimeCards(ArrayList<TimeCard> timeCards) {
        timeCards.forEach(timeCard -> {
            if (!empTimeCards.containsKey(timeCard.getEmployeeId())) {
                TreeMap<LocalDateTime, TimeCard> tCards = new TreeMap<>();
                empTimeCards.put(timeCard.getEmployeeId(), tCards);
            }
            empTimeCards.get(timeCard.getEmployeeId()).put(timeCard.getPunchIn(), timeCard);
        });
    }

    public ArrayList<TimeCard> overLappingTimeCards() {
        ArrayList<TimeCard> overlapped = new ArrayList<>();

        this.empTimeCards.forEach((employeeId, timeCards) -> {
            findOverlappingTimeCards(timeCards);
            timeCards.forEach((key, value) -> {
                if (value.isOverlapped()) {
                    overlapped.add(value);
                }
            });
        });

        return overlapped;
    }

    private void findOverlappingTimeCards(
            TreeMap<LocalDateTime, TimeCard> tCards) {

        tCards.forEach((key, value) -> {
            SortedMap<LocalDateTime, TimeCard> workCards =
                    tCards.subMap(key, value.getPunchOut());

            if (workCards.size() > 1) {
                workCards.forEach((wKey,
                                   wValue) -> {
                    if (key != wKey) {
                        value.compareOverlap(wValue);
                    }
                });
            }
        });
    }
}

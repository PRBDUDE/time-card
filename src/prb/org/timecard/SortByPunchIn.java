package prb.org.timecard;

import java.util.Comparator;

public class SortByPunchIn implements Comparator<TimeCard> {
    public int compare(TimeCard card1, TimeCard card2) {
        if (card1.getPunchIn().isBefore(card2.getPunchIn())) return -1;
        if (card1.getPunchIn().isAfter(card2.getPunchIn())) return 1;
        return 0;
    }
}

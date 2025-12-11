package prb.org.timecard;

import java.util.Comparator;

public class SortByPunchIn implements Comparator {
    public int compare(Object obj1, Object obj2) {
        TimeCard card1 = (TimeCard) obj1;
        TimeCard card2 = (TimeCard) obj2;

        if (card1.getPunchIn().isBefore(card2.getPunchIn())) return -1;
        if (card1.getPunchIn().isAfter(card2.getPunchIn())) return 1;
        return 0;
    }
}

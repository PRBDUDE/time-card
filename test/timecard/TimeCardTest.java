package timecard;

import java.time.LocalDateTime;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeCardTest {

	@Test
	public void testEmployeeId() {
		TimeCard card = new TimeCard();
		card.setEmployeeId(45);
        assertEquals(45, card.getEmployeeId());
	}

	@Test
	public void testPunchIn() {
		TimeCard card = new TimeCard(34);
		LocalDateTime in = LocalDateTime.of(2024, 10, 15, 8, 2);
		card.setPunchIn(in);
		assertEquals(in, card.getPunchIn());
	}

	@Test
	public void testPunchOut() {
		TimeCard card = new TimeCard();
		LocalDateTime out = LocalDateTime.of(2019, 10, 15, 13, 20);
		card.setPunchOut(out);
		assertEquals(out, card.getPunchOut());
	}

	@Test
	public void compareOverlappedPunchIn() {
		TimeCard card1 = new TimeCard(33);
		TimeCard card2 = new TimeCard(33);
		card1.setPunchIn(LocalDateTime.of(2017, 10, 15, 8, 0));
		card1.setPunchOut(LocalDateTime.of(2017, 10, 15, 17, 30));
		card2.setPunchIn(LocalDateTime.of(2017, 10, 15, 8, 7));
		card2.setPunchOut(LocalDateTime.of(2017, 10, 15, 17, 30));

		card1.compareOverlap(card2);
		assertTrue(card1.isOverlapped());
		assertTrue(card2.isOverlapped());
	}

	@Test
	public void compareOverlappedPunchOut() {
		TimeCard card1 = new TimeCard(33);
		TimeCard card2 = new TimeCard(33);
		card1.setPunchIn(LocalDateTime.of(2017, 10, 15, 8, 0));
		card1.setPunchOut(LocalDateTime.of(2017, 10, 15, 17, 30));
		card2.setPunchIn(LocalDateTime.of(2017, 10, 15, 7, 0));
		card2.setPunchOut(LocalDateTime.of(2017, 10, 15, 16, 0));

		card1.compareOverlap(card2);
		assertTrue(card1.isOverlapped());
		assertTrue(card2.isOverlapped());
	}

	@Test
	public void compareOverlappedSameEmployee() {
		TimeCard card1 = new TimeCard(45);
		TimeCard card2 = new TimeCard(45);
		TimeCard card3 = new TimeCard(45);
		card1.setPunchIn(LocalDateTime.of(2017, 10, 15, 8, 0));
		card1.setPunchOut(LocalDateTime.of(2017, 10, 15, 13, 30));
		card2.setPunchIn(LocalDateTime.of(2017,  10, 15, 12, 4));
		card2.setPunchOut(LocalDateTime.of(2017, 10, 15, 17, 5));
		card3.setPunchIn(LocalDateTime.of(2017,  10, 16, 8, 4));
		card3.setPunchOut(LocalDateTime.of(2017, 10, 16, 11, 50));

		card1.compareOverlap(card2);
		assertTrue(card1.isOverlapped());
		assertTrue(card2.isOverlapped());
		assertFalse(card3.isOverlapped());
		
		card2.compareOverlap(card1);
		assertTrue(card1.isOverlapped());
		assertTrue(card2.isOverlapped());
		assertFalse(card3.isOverlapped());

		card2.compareOverlap(card3);
		assertTrue(card1.isOverlapped());
		assertTrue(card2.isOverlapped());
		assertFalse(card3.isOverlapped());
	}
	
	@Test
	public void testNonOverlappedSameEmployee() {
		TimeCard card1 = new TimeCard(45);
		TimeCard card2 = new TimeCard(45);
		card1.setPunchIn(LocalDateTime.of(2017, 10, 15, 8, 0));
		card1.setPunchOut(LocalDateTime.of(2017, 10, 15, 11, 30));
		card2.setPunchIn(LocalDateTime.of(2017,  10, 15, 12, 4));
		card2.setPunchOut(LocalDateTime.of(2017, 10, 15, 17, 5));

		card1.compareOverlap(card2);
		assertFalse(card1.isOverlapped());
		assertFalse(card2.isOverlapped());
		
		card2.compareOverlap(card1);
		assertFalse(card1.isOverlapped());
		assertFalse(card2.isOverlapped());
	}
	
	@Test
	public void compareOverlappedDifferentEmployee() {
		TimeCard card1 = new TimeCard(45);
		TimeCard card2 = new TimeCard(46);
		card1.setPunchIn(LocalDateTime.of(2017, 10, 15, 8, 0));
		card1.setPunchOut(LocalDateTime.of(2017, 10, 15, 13, 30));
		card2.setPunchIn(LocalDateTime.of(2017,  10, 15, 12, 4));
		card2.setPunchOut(LocalDateTime.of(2017, 10, 15, 17, 5));

		card1.compareOverlap(card2);
		assertFalse(card1.isOverlapped());
		assertFalse(card2.isOverlapped());
		
		card2.compareOverlap(card1);
		assertFalse(card1.isOverlapped());
		assertFalse(card2.isOverlapped());
	}

	@Test
	public void testExceptionWhenPunchOutBeforePunchIn() {
		TimeCard card = new TimeCard(55);
		card.setPunchIn(LocalDateTime.of(2018, 7, 5, 8, 30));
		try {
			card.setPunchOut(LocalDateTime.of(2018, 7, 5, 8, 0));
			fail("Should not get here");
		} catch (Exception error) {
			assertEquals("Punch In time must be before Punch Out time - Employee ID: 55", error.getMessage());
		}
	}

	@Test
	public void testExceptionWhenPunchInAfterPunchOut() {
		TimeCard card = new TimeCard(99);
		card.setPunchOut(LocalDateTime.of(2019, 3, 15, 15, 0));
		try {
			card.setPunchIn(LocalDateTime.of(2019, 3, 16, 8, 0));
		} catch (Exception error) {
			assertEquals("Punch In time must be before Punch Out time - Employee ID: 99", error.getMessage());
		}
	}

	@Test
	public void testMultipleCheckInCheckOut() {
		int year = 2020;
		int month = 5;
		TimeCard card = new TimeCard(53);
		card.setPunchIn(LocalDateTime.of(year, month, 2, 8, 15, 56));
		card.setPunchOut(LocalDateTime.of(year, month, 2, 17, 40, 32));
		try {
			card.setPunchIn(LocalDateTime.of(year, month, 3, 8, 10, 42));
		} catch (Exception error) {
			assertEquals("Punch In time must be before Punch Out time - Employee ID: 53", error.getMessage());
		}
	}

	@Test
	public void testHoursWorkedSameDay0Minutes() {
		TimeCard card = new TimeCard(42);
		card.setPunchIn(LocalDateTime.of(2020, 1, 3, 8, 0));
		card.setPunchOut(LocalDateTime.of(2020, 1, 3, 17, 0));
		assertEquals(9, card.getHoursWorked());
	}

	@Test
	public void testMinutesWorkedSameDay5Minutes() {
		TimeCard card = new TimeCard(42);
		card.setPunchIn(LocalDateTime.of(2020, 1, 3, 8, 0));
		card.setPunchOut(LocalDateTime.of(2020, 1, 3, 17, 5));
		assertEquals(5, card.getMinutesWorked());
	}

	@Test
	public void testMinutesWorkedSameDay8LessMinutesThanStartMinutes() {
		TimeCard card = new TimeCard(42);
		card.setPunchIn(LocalDateTime.of(2020, 1, 3, 8, 13));
		card.setPunchOut(LocalDateTime.of(2020, 1, 3, 17, 5));
		assertEquals(-8, card.getMinutesWorked());
	}

	@Test
	public void testTotalHoursWorked() {
		TimeCard card = new TimeCard(2);
		card.setPunchIn(LocalDateTime.of(2020, 1, 3, 8, 13));
		card.setPunchOut(LocalDateTime.of(2020, 1, 3, 17, 5));
		assertEquals(9, card.getHoursWorked());
		assertEquals(8.75, card.getTotalHoursWorked(), 0.00);
	}
}

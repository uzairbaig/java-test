package co.uk.henry.grocery.pojo;

import static java.time.LocalDate.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class OfferPercentageTest {

	@Test
	public void testCalculateDiscountNullItem() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.3"));

		try {
			percentageOffer.calculateDiscount();
			fail();
		} catch (IllegalStateException e) {
			// expected
		}
	}

	@Test
	public void testCalculateDiscountItemNullPrice() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.3"));

		Item item = new Item("testitem", "unit", null);
		percentageOffer.setItem(item);

		try {
			percentageOffer.calculateDiscount();
			fail();
		} catch (IllegalStateException e) {
			// expected
		}
	}

	@Test
	public void testCalculateDiscountItemNullDiscount() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");

		Item item = new Item("testitem", "unit", new BigDecimal("3.00"));
		percentageOffer.setItem(item);

		try {
			percentageOffer.calculateDiscount();
			fail();
		} catch (IllegalStateException e) {
			// expected
		}
	}

	@Test
	public void testCalculateDiscount() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));

		Item item = new Item("testitem", "unit", new BigDecimal("4.00"));
		percentageOffer.setItem(item);

		assertEquals(new BigDecimal("1.0000"),
				percentageOffer.calculateDiscount());
	}

	@Test
	public void testIsApplicableNullTallies() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));

		try {
			percentageOffer.isApplicableForOffer(null, now());
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testIsApplicableNoExpiryOrReqItems() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));
		percentageOffer.setStartDate(now().minusDays(4));
		percentageOffer.setExpiryDate(now().plusDays(3));

		Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();

		assertTrue(percentageOffer.isApplicableForOffer(itemTallies, now()));
	}

	@Test
	public void testIsApplicableBeforeExpiry() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));

		percentageOffer.setStartDate(now().minusDays(4));
		percentageOffer.setExpiryDate(now().plusDays(3));

		Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();

		assertTrue(percentageOffer.isApplicableForOffer(itemTallies, now()));
	}

	@Test
	public void testIsApplicableAfterExpiry() {
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));

		percentageOffer.setStartDate(now().minusDays(7));
		percentageOffer.setExpiryDate(now().minusDays(1));

		Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();

		assertFalse(percentageOffer.isApplicableForOffer(itemTallies, now()));
	}

	@Test
	public void testIsApplicableWithRequiredItems() {
		Item requiredItem1 = new Item("reqitem", "unit", new BigDecimal("1.00"));
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));
		percentageOffer.setStartDate(now().minusDays(4));
		percentageOffer.setExpiryDate(now().plusDays(3));
		Map<Item, Integer> requiredItems = new HashMap<Item, Integer>();
		requiredItems.put(requiredItem1, 3);
		percentageOffer.setRequiredItems(requiredItems);

		Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();
		itemTallies.put(requiredItem1, 5);

		assertTrue(percentageOffer.isApplicableForOffer(itemTallies, now()));
		// assert that the item tally has been updated
		assertEquals(new Integer(2), itemTallies.get(requiredItem1));
	}

	@Test
	public void testIsApplicableWithInsufficientRequiredItems() {
		Item requiredItem1 = new Item("reqitem", "unit", new BigDecimal("1.00"));
		OfferPercentage percentageOffer = new OfferPercentage();
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));
		percentageOffer.setStartDate(now().minusDays(4));
		percentageOffer.setExpiryDate(now().plusDays(3));
		Map<Item, Integer> requiredItems = new HashMap<Item, Integer>();
		requiredItems.put(requiredItem1, 3);
		percentageOffer.setRequiredItems(requiredItems);

		Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();
		itemTallies.put(requiredItem1, 2);

		assertFalse(percentageOffer.isApplicableForOffer(itemTallies, now()));
		// assert that the item tally has not been changed
		assertEquals(new Integer(2), itemTallies.get(requiredItem1));
	}

	@Test
	public void testIsApplicableWithExpiryAndRequiredItems() {
		Item requiredItem1 = new Item("reqitem", "unit", new BigDecimal("1.00"));
		OfferPercentage percentageOffer = new OfferPercentage() ;
		percentageOffer.setId("testItemOffer");
		percentageOffer.setDiscount(new BigDecimal("0.25"));
		percentageOffer.setStartDate(now().minusDays(4));
		percentageOffer.setExpiryDate(now().plusDays(3));

		Map<Item, Integer> requiredItems = new HashMap<Item, Integer>();
		requiredItems.put(requiredItem1, 3);
		percentageOffer.setRequiredItems(requiredItems);

		Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();
		itemTallies.put(requiredItem1, 5);

		assertTrue(percentageOffer.isApplicableForOffer(itemTallies, now()));
		// assert that the item tally has not been changed
		assertEquals(new Integer(2), itemTallies.get(requiredItem1));
	}

}

package co.uk.henry.grocery.service.impl;

import static java.util.Collections.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.henry.grocery.pojo.Item;
import co.uk.henry.grocery.pojo.Offer;
import co.uk.henry.grocery.pojo.OfferPercentage;
import org.junit.Before;
import org.junit.Test;

public class ItemServiceTest {

	private ItemService itemService;

	@Before
	public void setup() {
		itemService = new ItemService();
		itemService.init();
	}

	@Test
	public void testGetItemNull() {
		try {
			itemService.getItem(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testGetItemEmpty() {
		assertEquals(null, itemService.getItem("apple"));
	}

	@Test
	public void testGetItem() {
		Item testItem = new Item("testitem", "unit", new BigDecimal("12"));
		itemService.setItems(singletonMap("testitem", testItem));
		assertEquals(null, itemService.getItem("apple"));
		// getItem method is case insensitive
		assertEquals(testItem, itemService.getItem("testItem"));
	}

	@Test
	public void testGetOffersNull() {
		try {
			itemService.getOffers(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testGetOffersEmpty() {
		List<Offer> offers = itemService.getOffers(new Item("testitem",
				"unit", new BigDecimal("12.00")));
		assertTrue(offers.isEmpty());
	}

	@Test
	public void testGetOffers() {

		Item testItem1 = new Item("testitem1", "unit", new BigDecimal("12.00"));
		Item testItem2 = new Item("testitem2", "unit", new BigDecimal("13.00"));
		Item testItem3 = new Item("testitem3", "unit", new BigDecimal("14.00"));

		OfferPercentage testOffer1 = createOffer("testOffer1", testItem1);
		OfferPercentage testOffer2 = createOffer("testOffer2", testItem1);
		OfferPercentage testOffer3 = createOffer("testOffer3", testItem2);
		final Map<String, Offer> availableOffers = new HashMap<>();
		availableOffers.put("testOffer1", testOffer1);
		availableOffers.put("testOffer2", testOffer2);
		availableOffers.put("testOffer3", testOffer3);
		itemService.setOffers(availableOffers);

		// testItem1 should have 2 matching Offers
		List<Offer> offers = itemService.getOffers(testItem1);
		assertEquals(2, offers.size());
//		assertEquals(testOffer1, offers.get(0));
//		assertEquals(testOffer2, offers.get(1));

		// testItem2 should have 1 matching Offer
		offers = itemService.getOffers(testItem2);
		assertEquals(1, offers.size());
		assertEquals(testOffer3, offers.get(0));

		// testItem3 should have no matching offers
		offers = itemService.getOffers(testItem3);
		assertTrue(offers.isEmpty());
	}

	private OfferPercentage createOffer(String id, Item testItem1) {
		OfferPercentage offer = new OfferPercentage();
		offer.setId(id);
		offer.setItem(testItem1);
		return offer;
	}

}

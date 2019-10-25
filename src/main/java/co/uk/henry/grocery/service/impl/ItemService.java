package co.uk.henry.grocery.service.impl;

import static co.uk.henry.grocery.util.ValidateUtil.validateNotNull;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static java.util.Collections.singletonMap;

import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import co.uk.henry.grocery.pojo.Item;
import co.uk.henry.grocery.pojo.Offer;
import co.uk.henry.grocery.pojo.OfferPercentage;
import co.uk.henry.grocery.service.IItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements IItemService {

	private Map<String, Item> items;
	private Map<String, Offer> offers;

	@PostConstruct
	public void init() {
		items = new HashMap<>();
		offers = new HashMap<>();
		items.put("soup", new Item("soup","tin", valueOf(0.65)));
		items.put("bread", new Item("bread","load",valueOf(0.80)));
		items.put("milk", new Item("milk","bottle", valueOf(1.30)));
		items.put("apples", new Item("apples","single",valueOf(0.10)));

		offers.put("breadOffer", new OfferPercentage("breadOffer", valueOf(0.5), items.get("bread"), now().minusDays(1), now().minusDays(1).plusDays(7), singletonMap(items.get("soup"), 2)));
		offers.put("applesOffer", new OfferPercentage("applesOffer", valueOf(.10), items.get("apples"), now().plusDays(3), now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()), null));
	}

	@Override
	public Item getItem(String name) {
		validateNotNull(name, "name");
		return items.get(name.toLowerCase());
	}

	@Override
	public List<Offer> getOffers(Item item) {
		validateNotNull(item, "item");
		final List<Offer> offersForItem = new ArrayList<>();
		for (Offer offer : offers.values()) {
			if (item.equals(offer.getItem())) {
				offersForItem.add(offer);
			}
		}
		return offersForItem;
	}

	public void setItems(final Map<String, Item> items) {
		this.items = items;
	}

	public void setOffers(final Map<String, Offer> offers) {
		this.offers = offers;
	}
}

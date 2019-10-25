package co.uk.henry.grocery.pojo;

import static co.uk.henry.grocery.util.ValidateUtil.validateNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

public class OfferPercentage implements Offer {

	private String id;
	private BigDecimal discount;
	private Item item;
	private LocalDate startDate;
	private LocalDate expiryDate;
	private Map<Item, Integer> requiredItems;

	public OfferPercentage() {
	}

	public OfferPercentage(final String id, final BigDecimal discount, final Item item,
						   final LocalDate startDate, final LocalDate expiryDate,
						   final Map<Item, Integer> requiredItems) {
		this.id = id;
		this.discount = discount;
		this.item = item;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		this.requiredItems = requiredItems;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Override
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Map<Item, Integer> getRequiredItems() {
		return requiredItems;
	}

	public void setRequiredItems(Map<Item, Integer> requiredItems) {
		this.requiredItems = requiredItems;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiry) {
		this.expiryDate = expiry;
	}

	@Override
	public BigDecimal calculateDiscount() {
		if (item == null) {
			throw new IllegalStateException("[item] should not be null");
		}
		if (item.getCost() == null) {
			throw new IllegalStateException("[item price] should not be null");
		}
		if (discount == null) {
			throw new IllegalStateException("[discount] should not be null");
		}
		return item.getCost().multiply(discount);
	}

	@Override
	public boolean isApplicableForOffer(final Map<Item, Integer> itemTallies, final LocalDate purchaseDate) {
		validateNotNull(itemTallies, "itemTallies");
		final boolean isBeforeExpired = isOfferDatesAreValid(purchaseDate);
		final boolean hasApplicableRequiredItems = hasApplicableRequiredItems(itemTallies);
		return isBeforeExpired && hasApplicableRequiredItems;
	}

	private boolean isOfferDatesAreValid(final LocalDate purchaseDate) {
		return purchaseDate.isAfter(startDate) && purchaseDate.isBefore(expiryDate);
	}

	private boolean hasApplicableRequiredItems(Map<Item, Integer> itemTallies) {
		if (requiredItems == null || requiredItems.isEmpty()) {
			return true;
		}
		for (Entry<Item, Integer> requiredItemEntry : requiredItems.entrySet()) {
			final Item requiredItem = requiredItemEntry.getKey();
			final Integer qty = requiredItemEntry.getValue();
			final Integer itemTally = itemTallies.get(requiredItem);
			if (itemTally != null && itemTally >= qty) {
				itemTallies.put(requiredItem, itemTally - qty);
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}

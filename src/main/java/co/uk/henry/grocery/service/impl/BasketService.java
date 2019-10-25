package co.uk.henry.grocery.service.impl;

import static co.uk.henry.grocery.util.ValidateUtil.validateNotNull;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.henry.grocery.pojo.Basket;
import co.uk.henry.grocery.pojo.Item;
import co.uk.henry.grocery.pojo.Offer;
import co.uk.henry.grocery.service.IBasketService;
import co.uk.henry.grocery.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService implements IBasketService {

	private IItemService itemService;

	@Autowired
	public BasketService(final IItemService itemService) {
		this.itemService = itemService;
	}

	@Override
	public BigDecimal calculateBasketTotalCost(Basket basket) {
		validateNotNull(basket, "basket");

		final Map<Item, Integer> itemTallies = new HashMap<Item, Integer>();

		final BigDecimal subTotal = calculateSubTotal(basket, itemTallies);
		final Map<Offer, BigDecimal> offerTotals = calculateOfferTotals(basket,
				itemTallies);
		return  calculateTotal(subTotal, offerTotals);

	}

	private BigDecimal calculateTotal(final BigDecimal subTotal,
			final Map<Offer, BigDecimal> offerTotals) {
		BigDecimal discounts = ZERO;
		for (final BigDecimal total : offerTotals.values()) {
			discounts = discounts.add(total);
		}
		return subTotal.subtract(discounts);
	}

	private Map<Offer, BigDecimal> calculateOfferTotals(final Basket basket,
			final Map<Item, Integer> itemTallies) {
		final Map<Offer, BigDecimal> offerTotals = new HashMap<Offer, BigDecimal>();
		for (final Item item : basket.getItems()) {
			final List<Offer> offers = itemService.getOffers(item);
			for (final Offer offer : offers) {
				if (offer.isApplicableForOffer(itemTallies, basket.getPurchaseDate())) {
					final BigDecimal offerDiscount = offer.calculateDiscount();
					final BigDecimal totalOfferDiscount = offerTotals.get(offer);
					if (totalOfferDiscount == null) {
						offerTotals.put(offer, offerDiscount);
					} else {
						offerTotals.put(offer,
								totalOfferDiscount.add(offerDiscount));
					}
				}
			}
		}
		return offerTotals;
	}

	private BigDecimal calculateSubTotal(final Basket basket,
			final Map<Item, Integer> itemTallies) {
		BigDecimal subtotal = ZERO;
		for (final Item item : basket.getItems()) {
			subtotal = subtotal.add(item.getCost());
			final Integer tally = itemTallies.get(item);
			if (tally == null) {
				itemTallies.put(item, 1);
			} else {
				itemTallies.put(item, tally + 1);
			}
		}
		return subtotal;
	}

}

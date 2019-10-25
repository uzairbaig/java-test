package co.uk.henry.grocery;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.time.LocalDate;

import co.uk.henry.grocery.pojo.Basket;
import co.uk.henry.grocery.pojo.Item;
import co.uk.henry.grocery.service.IBasketService;
import co.uk.henry.grocery.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketScanner {

	private IBasketService basketService;
	private IItemService itemService;

	private String[] items;
	private LocalDate purchaseDate;

	@Autowired
	public BasketScanner(final IBasketService basketService, final IItemService itemService) {
		this.basketService = basketService;
		this.itemService = itemService;
	}
	
	public void scan() {
		if (items == null) {
			throw new IllegalStateException("items array should not be null");
		}
		final Basket basket = new Basket();
		basket.setPurchaseDate(purchaseDate);
		for (final String itemName : items) {
			final Item item = itemService.getItem(itemName);
			if (item == null) {
				continue;
			}
			basket.addItem(item);
		}

		final BigDecimal basketTotalCost = basketService.calculateBasketTotalCost(basket);
		System.out.print(format("Total cost: %1$,.2f", basketTotalCost.doubleValue()));
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public void setPurchaseDate(final LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}

package co.uk.henry.grocery.pojo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Basket {

	private List<Item> items = new LinkedList<Item>();

	private LocalDate purchaseDate;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(final List<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(final LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}

package co.uk.henry.grocery.pojo;

import java.math.BigDecimal;

public class Item {

	private String product;
	private String unit;
	private BigDecimal cost;
	
	public Item() {
	}
	
	public Item(String product, final String unit, BigDecimal cost) {
		this.product = product;
		this.unit = unit;
		this.cost = cost;
	}

	public String getProduct() {
		return product;
	}


	public String getUnit() {
		return unit;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public String getDisplayName() {
		return product;
	}

	@Override
	public String toString() {
		return "Item [product=" + product + ", unit=" + unit + ",cost=" + cost + "]";
	}

}

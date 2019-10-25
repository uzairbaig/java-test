package co.uk.henry.grocery.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface Offer {

	String getId();

	Item getItem();

	BigDecimal calculateDiscount();

	boolean isApplicableForOffer(Map<Item, Integer> itemTallies, LocalDate purchaseDate);
}

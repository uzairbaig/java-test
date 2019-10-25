package co.uk.henry.grocery.service;

import java.util.List;

import co.uk.henry.grocery.pojo.Item;
import co.uk.henry.grocery.pojo.Offer;


public interface IItemService {

	Item getItem(final String name);

	List<Offer> getOffers(final Item item);

}

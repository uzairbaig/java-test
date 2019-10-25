package co.uk.henry.grocery.service;

import java.math.BigDecimal;

import co.uk.henry.grocery.pojo.Basket;

public interface IBasketService {

	BigDecimal calculateBasketTotalCost(Basket basket);

}

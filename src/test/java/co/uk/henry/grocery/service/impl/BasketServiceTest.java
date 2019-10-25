package co.uk.henry.grocery.service.impl;

import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import co.uk.henry.grocery.pojo.Basket;
import co.uk.henry.grocery.pojo.Item;
import org.junit.Before;
import org.junit.Test;

public class BasketServiceTest {

	private BasketService basketService;
	private ItemService itemService;

	@Before
	public void setup() {
		itemService = new ItemService();
		itemService.init();
		basketService = new BasketService(itemService);
	}

	@Test
	public void test3soupAnd2BreadBoughtToday() {
		final Basket basket = new Basket();
		final List<Item> items = Arrays.asList(itemService.getItem("soup"), itemService.getItem("soup")
				, itemService.getItem("soup"), itemService.getItem("bread"), itemService.getItem("bread"));
		basket.setItems(items);
		basket.setPurchaseDate(now());
		final BigDecimal bigDecimal = basketService.calculateBasketTotalCost(basket);
		assertThat(bigDecimal.doubleValue(), is(3.15));
	}

	@Test
	public void test6ApplesAndBottleOfMilkBoughtToday() {
		final Basket basket = new Basket();
		final List<Item> items = Arrays.asList(itemService.getItem("apples"), itemService.getItem("apples")
				, itemService.getItem("apples"), itemService.getItem("apples"), itemService.getItem("apples")
				, itemService.getItem("apples"), itemService.getItem("milk"));
		basket.setItems(items);
		basket.setPurchaseDate(now());
		final BigDecimal bigDecimal = basketService.calculateBasketTotalCost(basket);
		assertThat(bigDecimal.doubleValue(), is(1.90));
	}

	@Test
	public void test6ApplesAndBottleOfMilkBoughtInFiveDays() {
		final Basket basket = new Basket();
		final List<Item> items = Arrays.asList(itemService.getItem("apples"), itemService.getItem("apples")
				, itemService.getItem("apples"), itemService.getItem("apples"), itemService.getItem("apples")
				, itemService.getItem("apples"), itemService.getItem("milk"));
		basket.setItems(items);
		basket.setPurchaseDate(now().plusDays(5));
		final BigDecimal bigDecimal = basketService.calculateBasketTotalCost(basket);
		assertThat(bigDecimal.doubleValue(), is(1.84));
	}

	@Test
	public void test3Apples2TinOfSoupsAndALoafOfBreadBoughtInFiveDays() {
		final Basket basket = new Basket();
		final List<Item> items = Arrays.asList(itemService.getItem("apples"), itemService.getItem("apples")
				, itemService.getItem("apples"), itemService.getItem("soup"), itemService.getItem("soup")
				, itemService.getItem("bread"));
		basket.setItems(items);
		basket.setPurchaseDate(now().plusDays(5));
		final BigDecimal bigDecimal = basketService.calculateBasketTotalCost(basket);
		assertThat(bigDecimal.doubleValue(), is(1.97));
	}

	
}

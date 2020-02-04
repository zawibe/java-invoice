package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity == 0 || quantity < 0) throw new IllegalArgumentException();
		products.put(product, quantity);
	}

	public BigDecimal getNetPrice() {
		BigDecimal sum = new BigDecimal(0);

		for (Product product : this.products.keySet()) {
			Integer quantity = this.products.get(product);
			sum = sum.add(product.getPrice().multiply(new BigDecimal(quantity)));
		}
		return sum;
	}

	public BigDecimal getTax() {
		return getGrossPrice().subtract(this.getNetPrice());
	}

	public BigDecimal getGrossPrice() {
		BigDecimal sum = new BigDecimal(0);
		for (Product product : this.products.keySet()) {
			Integer quantity = this.products.get(product);
			sum = sum.add(product.getPriceWithTax().multiply(new BigDecimal(quantity)));
		}
		return sum;
	}
}

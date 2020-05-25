package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();
    private static int nextNumber = 0;
    private final int number = ++nextNumber;
	public OtherProduct addProduct;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        	if (products.containsKey(product)) {
			
			Integer newQuantity = products.get(product);
			products.put(product, newQuantity + quantity);
		} 
        	else {products.put(product, quantity);}
    }

	public int productQuantity() {
        int qty = 0;
        for (Product product : products.keySet()) {
            qty++;
        }
        return qty;
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
    }

	public String print() {
		String invoicePrint = String.valueOf(getNumber());
		for (Product product: products.keySet()) {
			invoicePrint += "\n";
			invoicePrint += product.getName();
			invoicePrint += "";
			invoicePrint += product.getPrice();
		}
		invoicePrint+=" Liczba pozycji: " + products.size();
				
		return invoicePrint;
	}
}



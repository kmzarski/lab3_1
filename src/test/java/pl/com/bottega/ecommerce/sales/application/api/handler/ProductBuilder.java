package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.apache.commons.lang3.RandomStringUtils;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;

public class ProductBuilder {
    private Product product;

    public ProductBuilder() {
        this.product = new Product(Id.generate(), MoneyBuilder.random().build(), " ", ProductType.STANDARD);
    }

    public static ProductBuilder clone(Product toClone) {
        ProductBuilder builder = defaultValues();
        builder.setMoney(toClone.getPrice());
        builder.setName(toClone.getName());
        builder.setProductType(toClone.getProductType());
        return builder;
    }

    public static ProductBuilder random() {
        ProductBuilder builder = defaultValues();
        builder.setMoney(MoneyBuilder.random().build());
        builder.setName(RandomStringUtils.randomAlphabetic(10));
        builder.setProductType(ProductType.STANDARD);
        return builder;
    }

    public static ProductBuilder defaultValues() {
        return new ProductBuilder();
    }

    public Product build() {
        return product;
    }

    public ProductBuilder setMoney(Money money) {
        product.setPrice(money);
        return this;
    }

    public ProductBuilder setName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder setProductType(ProductType productType) {
        product.setProductType(productType);
        return this;
    }
}

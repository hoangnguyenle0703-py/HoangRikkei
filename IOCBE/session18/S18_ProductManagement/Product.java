import java.util.Date;

public class Product {
    private int productId;
    private String productName;
    private float productPrice;
    private String productTitle;
    private Date productCreated;
    private String productCatalog;
    private String productStatus; // Representing BIT as String ("0" or "1") for simplicity

    // Default Constructor
    public Product() {}

    // Constructor without ID (for adding new products)
    public Product(String productName, float productPrice, String productTitle, Date productCreated, String productCatalog, String productStatus) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productCreated = productCreated;
        this.productCatalog = productCatalog;
        this.productStatus = productStatus;
    }

    // Getters and Setters (Omitted for brevity, generate them in your IDE)
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public float getProductPrice() { return productPrice; }
    public void setProductPrice(float productPrice) { this.productPrice = productPrice; }
    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }
    public Date getProductCreated() { return productCreated; }
    public void setProductCreated(Date productCreated) { this.productCreated = productCreated; }
    public String getProductCatalog() { return productCatalog; }
    public void setProductCatalog(String productCatalog) { this.productCatalog = productCatalog; }
    public String getProductStatus() { return productStatus; }
    public void setProductStatus(String productStatus) { this.productStatus = productStatus; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Price: %.2f | Catalog: %s | Status: %s",
                productId, productName, productPrice, productCatalog, productStatus);
    }
}
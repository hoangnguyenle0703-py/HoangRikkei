package model;

public class InvoiceDetails {
    private int id;
    private int invoice_id;
    private int product_id;
    private int quantity;
    private double unit_price;

    public InvoiceDetails(){}

    public InvoiceDetails(int id, int invoice_id, int product_id, int quantity, double unit_price) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInvoiceId() { return invoice_id; }
    public void setInvoiceId(int invoice_id) { this.invoice_id = invoice_id; }

    public int getProductId() { return product_id; }
    public void setProductId(int product_id) { this.product_id = product_id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unit_price; }
    public void setUnitPrice(double unit_price) {this.unit_price = unit_price;}
}

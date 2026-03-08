package model;

import java.time.LocalDateTime;

public class Invoice {
    private int id;
    private int customer_id;
    private LocalDateTime created_at;
    private double total_amount;

    public Invoice(){}

    public Invoice(int id, int customer_id, LocalDateTime created_at, double total_amount) {
        this.id = id;
        this.customer_id = customer_id;
        this.created_at = created_at;
        this.total_amount = total_amount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customer_id; }
    public void setCustomerId(int customer_id) { this.customer_id = customer_id; }

    public LocalDateTime getCreatedAt() { return created_at; }
    public void setCreatedAt(LocalDateTime created_at) { this.created_at = created_at; }

    public double getTotalAmount() { return total_amount; }
    public void setTotalAmount(double total_amount) { this.total_amount = total_amount; }
}

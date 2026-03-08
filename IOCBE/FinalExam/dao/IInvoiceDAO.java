package dao;

import model.Invoice;
import model.InvoiceDetails;
import java.util.List;

public interface IInvoiceDAO {
    List<Invoice> getAllInvoices();
    List<Invoice> searchByCustomerName(String customerName);
    boolean addInvoice(Invoice invoice, List<InvoiceDetails> details);
    List<Invoice> searchByDate(String dateStr);
}
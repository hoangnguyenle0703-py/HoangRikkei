package business.impl;

import business.IInvoiceService;
import dao.IInvoiceDAO;
import dao.impl.InvoiceDAOImpl;
import model.Invoice;
import model.InvoiceDetails;

import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {

    private final IInvoiceDAO invoiceDAO = new InvoiceDAOImpl();

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    @Override
    public List<Invoice> searchByCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            System.err.println("Tên khách hàng tìm kiếm không hợp lệ.");
            return null;
        }
        return invoiceDAO.searchByCustomerName(customerName);
    }

    @Override
    public boolean addInvoice(Invoice invoice, List<InvoiceDetails> details) {
        if (details == null || details.isEmpty()) {
            System.err.println("Không thể tạo hóa đơn trống (không có sản phẩm nào).");
            return false;
        }
        return invoiceDAO.addInvoice(invoice, details);
    }

    @Override
    public List<Invoice> searchByDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            System.err.println("Ngày tìm kiếm không hợp lệ.");
            return null;
        }

        // Gọi thẳng xuống hàm searchByDate của InvoiceDAOImpl mà bạn đã viết lúc nãy
        return invoiceDAO.searchByDate(dateStr);
    }
}

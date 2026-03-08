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
            System.err.println("Logic lỗi: Tên khách hàng tìm kiếm không hợp lệ.");
            return null;
        }
        return invoiceDAO.searchByCustomerName(customerName);
    }

    @Override
    public boolean addInvoice(Invoice invoice, List<InvoiceDetails> details) {
        // Logic kiểm tra cực kỳ quan trọng: Hóa đơn phải có ít nhất 1 sản phẩm mới được lưu
        if (details == null || details.isEmpty()) {
            System.err.println("Logic lỗi: Không thể tạo hóa đơn trống (không có sản phẩm nào).");
            return false;
        }
        // Gọi xuống DAO để thực hiện Transaction lưu vào database
        return invoiceDAO.addInvoice(invoice, details);
    }

    @Override
    public List<Invoice> searchByDate(String dateStr) {
        // Có thể thêm logic kiểm tra định dạng ngày tháng ở đây trước khi đẩy xuống DAO nếu muốn
        if (dateStr == null || dateStr.trim().isEmpty()) {
            System.err.println("Logic lỗi: Ngày tìm kiếm không hợp lệ.");
            return null;
        }

        // Gọi thẳng xuống hàm searchByDate của InvoiceDAOImpl mà bạn đã viết lúc nãy
        return invoiceDAO.searchByDate(dateStr);
    }
}
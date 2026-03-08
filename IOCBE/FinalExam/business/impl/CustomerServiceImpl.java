package business.impl;

import business.ICustomerService;
import dao.ICustomerDAO;
import dao.impl.CustomerDAOImpl;
import model.Customer;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public boolean addCustomer(Customer customer) {
        // Logic kiểm tra: Không cho phép thêm khách hàng nếu tên trống
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            System.err.println("Logic lỗi: Tên khách hàng không được để trống.");
            return false;
        }
        return customerDAO.addCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int id) {
        return customerDAO.deleteCustomer(id);
    }
}
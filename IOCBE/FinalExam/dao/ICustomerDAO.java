package dao;

import model.Customer;
import java.util.List;

public interface ICustomerDAO {
    List<Customer> getAllCustomers();
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
}
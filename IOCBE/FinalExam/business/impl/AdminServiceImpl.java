package business.impl;

import business.IAdminService;
import dao.IAdminDAO;
import dao.impl.AdminDAOImpl;

public class AdminServiceImpl implements IAdminService {

    private final IAdminDAO adminDAO = new AdminDAOImpl();

    @Override
    public boolean login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.err.println("Logic lỗi: Tài khoản và mật khẩu không được để trống.");
            return false;
        }
        return adminDAO.login(username, password);
    }
}
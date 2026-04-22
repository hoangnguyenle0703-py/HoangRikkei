package com.example.hrm.security;

import com.example.hrm.model.User;
import com.example.hrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Yêu cầu 2: Đánh dấu là một Bean bằng @Service
public class UserDetailServiceCustom implements UserDetailsService { // Yêu cầu 1: Implement UserDetailsService

    @Autowired // Yêu cầu 3: Tiêm (Inject) UserRepository
    private UserRepository userRepository;

    // Yêu cầu 4: Ghi đè phương thức loadUserByUsername
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Bước 1: Tìm User trong DB bằng username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với username: " + username));

        // Bước 2: Trả về đối tượng UserPrincipal (đã chứa user entity và quyền hạn)
        return new UserPrincipal(user);
    }
}
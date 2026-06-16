package com.ecommerce.orderservice;

import com.ecommerce.orderservice.controller.OrderController;
import com.ecommerce.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Kiểm thử tầng Controller cho API health-check.
 * <p>
 * Dùng {@code @WebMvcTest} để chỉ nạp tầng web, và mock {@link OrderService}
 * — minh họa lợi ích của việc tách interface giúp dễ test.
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void healthCheck_shouldReturnServiceIsUp() throws Exception {
        when(orderService.healthCheck()).thenReturn("Order Service is Up");

        mockMvc.perform(get("/api/v1/orders/health-check"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order Service is Up"));
    }
}

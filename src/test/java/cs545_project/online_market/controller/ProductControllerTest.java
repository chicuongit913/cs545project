package cs545_project.online_market.controller;

import cs545_project.online_market.builder.ProductResponseListBuilder;
import cs545_project.online_market.service.ProductService;
import cs545_project.online_market.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    ProductService productServiceMock;

    @Mock
    UserService userServiceMock;

    @InjectMocks
    ProductController ProductController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ProductController).build();
    }

    @Test
    public void productDetailsTest() throws Exception {

        ProductResponseListBuilder listBuilder = new ProductResponseListBuilder();
        when(productServiceMock.getProductById(1L)).thenReturn(listBuilder.getProductResponseBuilderOne().build());
        when(userServiceMock.isUserFollowSeller(1L)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("isFollow", true))
                .andExpect(model().attribute("product", listBuilder.getProductResponseBuilderOne().build()))
                .andExpect(MockMvcResultMatchers.view().name("views/product/product-details"));
    }
}

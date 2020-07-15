package cs545_project.online_market.controller.buyer;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.AddressService;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AddressControllerTest {
    @Mock
    private AddressService addressServiceMock;

    @Mock
    private Util utilMock;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @MockBean
    private BindingResult bindingResult;

    private MockMvc mockMvc;

    @InjectMocks
    private AddressController addressController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    public void getCreateBillingAddress() {
        String viewName = addressController.getCreateAddress(new AddressRequest(), "billing", model);
        assertEquals("views/buyer/address", viewName);
        verify(model, times(1)).addAttribute(eq("type"), eq("billing"));
    }

    @Test
    public void getCreateShippingAddress() {
        String viewName = addressController.getCreateAddress(new AddressRequest(), "shipping", model);
        assertEquals("views/buyer/address", viewName);
        verify(model, times(1)).addAttribute(eq("type"), eq("shipping"));
    }
}

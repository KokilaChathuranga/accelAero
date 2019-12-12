package com.hellokoding.auth.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hellokoding.auth.Response.Response;
import com.hellokoding.auth.Response.ResponseStatus;
import com.hellokoding.auth.kafka.model.Message;
import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.Order;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.service.CustomerService;
import com.hellokoding.auth.service.KafkaService;
import com.hellokoding.auth.service.OrderService;
import com.hellokoding.auth.service.SecurityService;
import com.hellokoding.auth.service.UserService;
import com.hellokoding.auth.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private KafkaService kafkaService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping({"/customer"})
    public Response<Customer> getCustomer() {
        return new Response<>(customerService.findByUser(getUser()), ResponseStatus.SUCCESS, "Customer get ok");
    }

    @PostMapping("/customer/update")
    public Response<Customer> updateCustomer(@Validated @RequestBody Customer customer) {
        User user = getUser();
        Customer existingCustomer = customerService.findByUser(user);
        if (existingCustomer == null) {
            return new Response<>(null, ResponseStatus.INVALID_CUSTOMER, "Customer id is invalid");
        }
        return new Response<>(customerService.update(user, customer, existingCustomer), ResponseStatus.SUCCESS, "Customer updated successfully");
    }

    @GetMapping("/restaurant")
    public Response<String> getRestaurantAndFoods(
            @RequestParam(value = "restaurant") String restaurant,
            @RequestParam(value = "food", required = false) String food
    ) {
        Message message = new Message();
        message.setField1(restaurant);
        message.setField2(food);
        message = kafkaService.getReplyFromConsumer(message);
        if (message == null)
            return new Response<>(null, ResponseStatus.ERROR_FROM_CONSUMER, "Error occurred while getting response from consumer");
        System.out.println(message.getField1());
        System.out.println(message.getField2());
        System.out.println(message.getAdditionalProperties().get("result"));
        System.out.println(message.getRestaurants().iterator().next().getName());
        System.out.println(message.getFoods().iterator().next().getName());
        return new Response<>((String) message.getAdditionalProperties().get("result"), ResponseStatus.SUCCESS, "restaurant get ok");
    }

    @PostMapping("/order/create")
    public Response<Order> createOrder(
            @RequestParam(value = "restaurant_id") long restaurant_id,
            @RequestParam(value = "food_id") long food_id,
            @RequestParam(value = "quantity") long quantity,
            @RequestParam(value = "order_date") String order_date,
            @RequestParam(value = "quantity") long unit_price,
            @RequestParam(value = "quantity") long total_price
    ) {
        Customer customer = customerService.findByUser(getUser());
        try {
            Date orderDate = parseDate("dd/MM/yyyy", order_date);
            return new Response<>(orderService.create(customer, restaurant_id, food_id, quantity, orderDate, unit_price, total_price), ResponseStatus.SUCCESS, "Order created successfully");
        } catch (ParseException e) {
            return new Response<>(null, ResponseStatus.ERROR, "Invalid date format");
        }
    }

    @GetMapping("/order/{order_id}")
    public Response<Order> getOrder(
            @PathVariable long order_id
    ) {
        Customer customer = customerService.findByUser(getUser());
        orderService.getOrderByCustomerAndId(customer, order_id);
        return new Response<>(orderService.getOrderByCustomerAndId(customer, order_id), ResponseStatus.SUCCESS, "Customer get ok");
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(authentication.getName());
    }

    private Date parseDate(String format, String value) throws ParseException {
        return (value == null || value.isEmpty()) ? null : new SimpleDateFormat(format).parse(value);
    }
}

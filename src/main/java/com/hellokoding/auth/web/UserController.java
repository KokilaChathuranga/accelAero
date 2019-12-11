package com.hellokoding.auth.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
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
import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.Order;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.service.CustomerService;
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
    ReplyingKafkaTemplate<String, com.hellokoding.auth.kafka.model.Model, com.hellokoding.auth.kafka.model.Model> kafkaTemplate;
    @Value("${kafka.topic.request-topic}")
    String requestTopic;
    @Value("${kafka.topic.requestreply-topic}")
    String requestReplyTopic;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

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

    @GetMapping(value = "/sum")
    public Object sum() throws InterruptedException, ExecutionException {
        com.hellokoding.auth.kafka.model.Model request = new  com.hellokoding.auth.kafka.model.Model();
        request.setFirstNumber(12);
        request.setSecondNumber(12);
        // create producer record
        ProducerRecord<String, com.hellokoding.auth.kafka.model.Model> record = new ProducerRecord<String, com.hellokoding.auth.kafka.model.Model>(requestTopic, request);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, com.hellokoding.auth.kafka.model.Model, com.hellokoding.auth.kafka.model.Model> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        // confirm if producer produced successfully
        SendResult<String, com.hellokoding.auth.kafka.model.Model> sendResult = sendAndReceive.getSendFuture().get();

        //print all headers
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        // get consumer record
        ConsumerRecord<String, com.hellokoding.auth.kafka.model.Model> consumerRecord = sendAndReceive.get();
        // return consumer value
        System.out.println(consumerRecord.value().getFirstNumber());
        System.out.println(consumerRecord.value().getSecondNumber());
        System.out.println(consumerRecord.value().getAdditionalProperties().get("sum"));
        return consumerRecord.value();
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
    public Response<Customer> getRestaurantAndFoods(
            @RequestParam(value = "restaurant") String restaurant,
            @RequestParam(value = "food", required = false) String food
    ) {
        // TODO: 12/10/19 implement
        return new Response<>(customerService.findByUser(getUser()), ResponseStatus.SUCCESS, "Customer get ok");
    }

    @PostMapping("/order/create")
    public Response<Order> createOrder(
            @RequestParam(value = "restaurant_id") long restaurant_id,
            @RequestParam(value = "food_id") long food_id,
            @RequestParam(value = "quantity") long quantity,
            @RequestParam(value = "order_date") String order_date
    ) {
        Customer customer = customerService.findByUser(getUser());
        try {
            Date orderDate = parseDate("dd/MM/yyyy", order_date);
            return new Response<>(orderService.create(customer, restaurant_id, food_id, quantity, orderDate), ResponseStatus.SUCCESS, "Order created successfully");
        } catch (ParseException e) {
            return new Response<>(null, ResponseStatus.ERROR, "Invalid date format");
        }
    }

    @GetMapping("/order/{order_id}")
    public Response<Customer> getOrder(
            @PathVariable long order_id
    ) {
        // TODO: 12/10/19 implement
        return new Response<>(customerService.findByUser(getUser()), ResponseStatus.SUCCESS, "Customer get ok");
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(authentication.getName());
    }

    private Date parseDate(String format, String value) throws ParseException {
        return (value == null || value.isEmpty()) ? null : new SimpleDateFormat(format).parse(value);
    }
}

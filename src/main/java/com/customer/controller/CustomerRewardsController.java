package com.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.domain.Customer;
import com.customer.domain.Rewards;
import com.customer.service.CustomerRewardsService;

@RestController
@RequestMapping("/api")
public class CustomerRewardsController {
	
	@Autowired
	private CustomerRewardsService customerRewardsService;

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomerOrders() {
		try {
			List<Customer> customerOrders = customerRewardsService.getAllCustomers();
			
			return new ResponseEntity<>(customerOrders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers/upload")
	public ResponseEntity<List<Customer>> uploadCustomerJsonData(@RequestBody String customers) {
		try {
			List<Customer> customerOrders = customerRewardsService.saveCustomerData(customers);
			
			return new ResponseEntity<>(customerOrders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers/loadFromFile")
	public ResponseEntity<List<Customer>> loadCustomerDataFromFile() {
		try {
			List<Customer> customerOrders = customerRewardsService.loadCustomerDataFromFile();
			
			return new ResponseEntity<>(customerOrders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> createNewCustomerOrder(@RequestBody Customer customer) {
		try {
			Customer customerOrder = customerRewardsService.saveCustomer(customer);
			
			return new ResponseEntity<>(customerOrder, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/customer/rewards")
	public ResponseEntity<List<Rewards>> getAllCustomerRewards(@RequestParam(required = true) String startDate, @RequestParam(required = true) String endDate) {
		try {
			List<Rewards> customerRewards = customerRewardsService.getAllCustomerRewards(startDate, endDate);
			
			return new ResponseEntity<>(customerRewards, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

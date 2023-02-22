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

import com.customer.data.domain.Customer;
import com.customer.data.domain.Rewards;
import com.customer.service.RewardsService;

@RestController
@RequestMapping("/api")
public class RewardsController {
	
	@Autowired
	private RewardsService rewardsService;

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomerOrders() {
		try {
			List<Customer> customerOrders = rewardsService.getAllCustomers();
			
			return new ResponseEntity<>(customerOrders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers/upload")
	public ResponseEntity<List<Customer>> uploadCustomerJsonData(@RequestBody List<Customer> customers) {
		try {
			List<Customer> customerOrders = rewardsService.saveCustomerData(customers);
			
			return new ResponseEntity<>(customerOrders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers/loadFromFile")
	public ResponseEntity<List<Customer>> loadCustomerDataFromFile() {
		try {
			List<Customer> customerOrders = rewardsService.loadCustomerDataFromFile();
			
			return new ResponseEntity<>(customerOrders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> createNewCustomerOrder(@RequestBody Customer customer) {
		try {
			Customer customerOrder = rewardsService.saveCustomer(customer);
			
			return new ResponseEntity<>(customerOrder, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/customer/rewards")
	public ResponseEntity<List<Rewards>> getAllCustomerRewards(@RequestParam(required = true) String startDate, @RequestParam(required = true) String endDate) {
		try {
			List<Rewards> customerRewards = rewardsService.getAllCustomerRewards(startDate, endDate);
			
			return new ResponseEntity<>(customerRewards, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

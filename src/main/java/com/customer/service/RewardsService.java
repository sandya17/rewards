package com.customer.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.customer.data.domain.Customer;
import com.customer.data.domain.Rewards;
import com.customer.data.domain.Transactions;
import com.customer.data.domain.repo.CustomerRepository;
import com.customer.data.domain.repo.TransactionsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class RewardsService {

	private static final int REWARDS_POINT_1X = 1;

	private static final int REWARDS_POINT_2X = 2;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionsRepository transactionsRepository;

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> {
			List<Transactions> t = customer.getTransactions();
			customer.setTransactions(t);
			customers.add(customer);
		});
		return customers;
	}

	//String
	public List<Customer> saveCustomerData(List<Customer> customers) {
		List<Customer> trasactions = new ArrayList<Customer>();
		customers.forEach(c -> {
			Customer customerOrder = saveCustomer(c);
			trasactions.add(customerOrder);
		});
		return trasactions;
	}

	//String
	public List<Customer> loadCustomerDataFromFile() {
		List<Customer> trasactions = new ArrayList<Customer>();
		List<Customer> customersFromJson = convertJsonToObjects(getCustomersFromFile());
		customersFromJson.forEach(c -> {
			Customer customerOrder = saveCustomer(c);
			trasactions.add(customerOrder);
		});
		return trasactions;
	}

	public Customer saveCustomer(Customer customer) {
		Customer c = customerRepository.save(customer);
		List<Transactions> t = customer.getTransactions();
		t.forEach(tr -> {
			tr.setCustomerId(customer.getCustomerId());
			transactionsRepository.save(tr);
		});
		return c;
	}
	
	public List<Rewards> getAllCustomerRewards(String startDate, String endDate) {
		List<Customer> trasactions = new ArrayList<Customer>();

		List<Rewards> groupedCustomerRewards = new ArrayList<Rewards>();

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date sDate = format.parse(startDate);
			Date eDate = format.parse(endDate);
			customerRepository.findAll().forEach(customer -> {
				List<Transactions> t = customer.getTransactions();
				customer.setTransactions(t);
				trasactions.add(customer);
			});
			Map<String, Double> sum = null;
			List<Rewards> customerRewards = new ArrayList<Rewards>();
			for (Customer customer : trasactions) {
				customer.getTransactions().stream()
						.filter(dates -> dates.getOrderDate().after(sDate) && dates.getOrderDate().before(eDate))
						.collect(Collectors.toList()).forEach(order -> {

							Double amt = order.getAmount();

							Double points = calculatePoints(amt);

							Rewards r = new Rewards();
							r.setName(customer.getCustomerName());
							r.setEmail(customer.getCustomerEmail());
							r.setPhno(customer.getCustomerPhno());
							r.setRewards(points);
							r.setMonth(getMonthName(order.getOrderDate().getMonth()));
							r.setOrderDate(order.getOrderDate());
							customerRewards.add(r);

						});

				// group by month
				sum = customerRewards.stream().collect(
						Collectors.groupingBy(Rewards::getMonth, Collectors.summingDouble(Rewards::getRewards)));

				for (String key : sum.keySet()) {
					Rewards r = new Rewards();
					r.setName(customer.getCustomerName());
					r.setEmail(customer.getCustomerEmail());
					r.setPhno(customer.getCustomerPhno());
					r.setRewards(sum.get(key));
					r.setMonth(key);
					groupedCustomerRewards.add(r);
				}

				customerRewards.clear();
				sum.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return groupedCustomerRewards;
	}

	private Double calculatePoints(Double amt) {
		Double points = 0d;
		if (amt > 50 && amt <= 100) {
			points = (amt - 50) * REWARDS_POINT_1X;
		} else if (amt > 100) {
			points = (amt - 100) * REWARDS_POINT_2X + 50;
		}
		return points;
	}

	private String getCustomersFromFile() {
		File file = null;
		String content = "";
		try {
			file = ResourceUtils.getFile("classpath:customer_orders.json");
			content = new String(Files.readAllBytes(file.toPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	private String getMonthName(int m) {
		String[] mons = new DateFormatSymbols(Locale.ENGLISH).getMonths();
		String mName = mons[m];
		return mName;
	}

	private List<Customer> convertJsonToObjects(String customers) {
		List<Customer> trasactions = new ArrayList<Customer>();

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Type orderListType = new TypeToken<ArrayList<Customer>>() {
		}.getType();

		try {
			trasactions = gson.fromJson(customers, orderListType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return trasactions;
	}

}

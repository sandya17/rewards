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

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.customer.data.Customer;
import com.customer.data.Rewards;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class CustomerRewardsService {
	
	private static final int REWARDS_POINT_1X = 1;
	
	private static final int REWARDS_POINT_2X = 2;

	public List<Customer> getAllCustomers() {

		return getCustomersFromFile();

	}

	public List<Rewards> getAllCustomerRewards(String startDate, String endDate) {
		List<Customer> customerOrders = new ArrayList<Customer>();

		List<Rewards> groupedCustomerRewards = new ArrayList<Rewards>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date sDate = format.parse(startDate);
			Date eDate = format.parse(endDate);
			customerOrders = getCustomersFromFile();
			Map<String, Double> sum = null;
			List<Rewards> customerRewards = new ArrayList<Rewards>();
			for (Customer customer : customerOrders) {
				customer.getOrders().stream()
						.filter(dates -> dates.getOrderDate().after(sDate) && dates.getOrderDate().before(eDate))
						.collect(Collectors.toList()).forEach(order -> {

							Double amt = order.getOrderAmt();
							Double points = 0d;
							if (amt > 50 && amt <= 100) {
								points = (amt - 50) * REWARDS_POINT_1X;
							} else if (amt > 100) {
								points = (amt - 100) * REWARDS_POINT_2X + 50;
							}

							Rewards r = new Rewards();
							r.setName(customer.getName());
							r.setEmail(customer.getEmail());
							r.setPhno(customer.getPhno());
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
					r.setName(customer.getName());
					r.setEmail(customer.getEmail());
					r.setPhno(customer.getPhno());
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

	private List<Customer> getCustomersFromFile() {
		List<Customer> customerOrders = new ArrayList<Customer>();
		File file = null;
		String content = "";

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Type orderListType = new TypeToken<ArrayList<Customer>>() {
		}.getType();

		try {
			file = ResourceUtils.getFile("classpath:customer_orders.json");
			content = new String(Files.readAllBytes(file.toPath()));
			customerOrders = gson.fromJson(content, orderListType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return customerOrders;
	}

	private String getMonthName(int m) {
		String[] mons = new DateFormatSymbols(Locale.ENGLISH).getMonths();
		String mName = mons[m];
		return mName;
	}

}

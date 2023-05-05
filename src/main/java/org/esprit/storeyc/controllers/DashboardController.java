package org.esprit.storeyc.controllers;

import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.services.Task.ProductScheduler;
import org.esprit.storeyc.services.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:8075"})
@CrossOrigin("*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ProductScheduler productScheduler;

	@GetMapping("/top-selling")
	public List<Product> getTopSellingProducts() {
		List<Product> products = productService.getAllP();
		return productScheduler.getTopSellingProducts(products);
	}

	@GetMapping("/top-worst")
	public List<Product> getTopWorstProducts() {
		List<Product> products = productService.getAllP();
		return productScheduler.getTopWorstProducts(products);
	}

	@GetMapping("/low-stock")
	public List<Product> getLowStockProducts() {
		List<Product> products = productService.getAllP();
		return productScheduler.getLowStockProducts(products);
	}
	@GetMapping("/revenue")
	public ResponseEntity<BigDecimal> getTotalRevenue() {
		List<Product> products = productService.getAllP();
		BigDecimal totalRevenue = calculateTotalRevenue(products);
		return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
	}

	private BigDecimal calculateTotalRevenue(List<Product> products) {
		BigDecimal totalRevenue = BigDecimal.ZERO;
		for (Product product : products) {
			totalRevenue = totalRevenue.add(product.getPrice().multiply(BigDecimal.valueOf(product.getSales())));
		}
		return totalRevenue;
	}
//	@GetMapping("/low-stock")
//	public BigDecimal getTotalRevenue() {
//		List<Product> products = productService.getAllP();
//		return productScheduler.getTotalRevenue(products);
//	}

//	@GetMapping("/salesAndStockReport")
//	public ResponseEntity<Map<String, Object>> getSalesAndStockReportForDashboard() {
//		Map<String, Object> report = productScheduler.getSalesAndStockReportForDashboard();
//		return ResponseEntity.ok(report);
//	}
}

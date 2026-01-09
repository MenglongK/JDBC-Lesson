package co.istad.jdbc;

import co.istad.jdbc.config.DatabaseConfig;
import co.istad.jdbc.model.Product;
import co.istad.jdbc.service.ProductService;
import co.istad.jdbc.service.ProductServiceImpl;
import co.istad.jdbc.util.InputUtil;
import co.istad.jdbc.view.View;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class jdbcApp {
    static void main(String[] args) {

        // Initialize connection with database
        DatabaseConfig.init();

        ProductService productService = new ProductServiceImpl();

        do {
            // Print all menu
            View.printMenu();

            // Start operation
            int menu = InputUtil.getInteger("Enter menu option: ");
            switch (menu) {
                case 0 -> System.exit(0);
                case 1 -> {
                    try {
                        List<Product> productList = productService.findAll();
                        View.printProductList(productList);
                    }catch (RuntimeException e){
                        View.printHeader(e.getMessage());
                    }
                }
                case 2 -> {}
                case 3 -> {
                    String code = InputUtil.getText("Enter product code: ");
                    String name = InputUtil.getText("Enter product name: ");
                    BigDecimal price = InputUtil.getMoney("Enter product price: ");
                    Integer qty = InputUtil.getInteger("Enter product qty: ");

                    Product product = new Product(code, name, price, qty, true);

                    try{
                        productService.save(product);
                        View.printHeader("Product save successfully..!");
                    }catch (RuntimeException e){
                        View.printHeader(e.getMessage());
                    }
                }
                case 4 -> {
                    View.printHeader("Update a product by code");
                    String code = InputUtil.getText("Enter product code: ");
                    String name = InputUtil.getText("Enter product name: ");
                    BigDecimal price = InputUtil.getMoney("Enter product price: ");
                    Integer qty = InputUtil.getInteger("Enter product qty: ");

                    Product product = new Product();
                    product.setName(name);
                    product.setPrice(price);
                    product.setQty(qty);

                    try {
                        productService.updateByCode(code,product);
                        View.printHeader("Product update successfully..!");
                    }catch (RuntimeException e){
                        View.printHeader(e.getMessage());
                    }
                }
                case 5 -> {
                    View.printHeader("Delete a product by code");
                    String code = InputUtil.getText("Enter code: ");

                    String confirmation = InputUtil.getText("Are you sure to delete? [y/N]");
                    if (confirmation.equalsIgnoreCase("y"))
                        try {
                            productService.deleteByCode(code);
                            View.printHeader("Product deleted successfully..!");
                        } catch (RuntimeException e) {
                            View.printHeader(e.getMessage());
                        }
                    else
                        View.printHeader("Delete operation cancelled..!");

                }
                default -> System.out.println("Invalid menu..!");
            }

        }while (true);

    }
}

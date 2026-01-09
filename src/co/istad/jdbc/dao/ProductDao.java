package co.istad.jdbc.dao;

import co.istad.jdbc.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    // 1. Find a product by code
    // 2. Expected return value -> Optional<Product>
    // 3. Parameters -> code
    Optional<Product> findByCode(String code) throws SQLException;


    // 1. Update an existing record by product code
    // 2. Expected return value -> affected row number
    // 3. Parameters -> code, new product info
    int updateByCode(String code, Product product) throws SQLException;

    // 1. Create a new record
    // 2. Expected return value -> return type
    // 3. Parameter
    int save(Product product) throws SQLException;

    // 1. Read all records
    // 2. Expected return value -> return type
    // 3. Parameters
    List<Product> findAll() throws SQLException;

    // Update

    // Delete
}

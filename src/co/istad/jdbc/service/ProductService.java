package co.istad.jdbc.service;

import co.istad.jdbc.model.Product;

import java.util.List;

public interface ProductService {

    void deleteByCode(String code);

    void updateByCode(String code, Product product);

    void save(Product product);

    List<Product> findAll();

}

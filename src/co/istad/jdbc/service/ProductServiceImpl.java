package co.istad.jdbc.service;

import co.istad.jdbc.dao.ProductDao;
import co.istad.jdbc.dao.ProductDaoImpl;
import co.istad.jdbc.model.Product;

import javax.management.RuntimeMBeanException;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    public ProductServiceImpl(){
        productDao = new ProductDaoImpl();
    }

    @Override
    public void updateByCode(String code, Product product) {
        // validate product code
        try{
            Product foundProduct = productDao.findByCode(code).orElseThrow(()-> new RuntimeException("Product code doesn't exist..!"));

            // Patially update: name, price = null, qty = null
            if (!foundProduct.getName().isBlank())
                foundProduct.setName(product.getName());
            if (foundProduct.getPrice() != null)
                foundProduct.setPrice(product.getPrice());
            if (foundProduct.getQty() != null)
                foundProduct.setQty(product.getQty());

            int affectedRows = productDao.updateByCode(code, foundProduct);
            if (affectedRows < 1 )
                throw new RuntimeException("Update operation failed!");

        } catch (SQLException e){
            System.out.println("SQL Errored: "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        try {
            int affectedRows = productDao.save(product);
            if (affectedRows < 1 )
                throw new RuntimeException("Insert new record failed!");
        } catch (SQLException e) {
            System.out.println("SQL Errored: "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            return productDao.findAll();
        } catch (SQLException e) {
            System.out.println("SQL Errored: "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteByCode(String code) {

    }
}

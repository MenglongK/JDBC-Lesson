package co.istad.jdbc.dao;

import co.istad.jdbc.config.DatabaseConfig;
import co.istad.jdbc.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    private final Connection conn;

    public ProductDaoImpl() {
        conn = DatabaseConfig.getConn();
    }


    @Override
    public Optional<Product> findByCode(String code) throws SQLException {
        String sql = """
                SELECT *
                FROM products
                WHERE code = ?
                """;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, code);

        ResultSet rs = pstmt.executeQuery();
        Product product;

        if (rs.next()) {
            product = new Product();
            product.setId(rs.getInt("id"));
            product.setCode(rs.getString("code"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setQty(rs.getInt("qty"));
            product.setStatus(rs.getBoolean("status"));
            return Optional.of(product);
        }

        return Optional.empty();
    }

    @Override
    public int updateByCode(String code, Product product) throws SQLException {
        String sql = """
                UPDATE products
                SET name = ?, price = ?, qty = ?
                WHERE code = ?
                """;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, product.getName());
        pstmt.setBigDecimal(2, product.getPrice());
        pstmt.setInt(3, product.getQty());
        pstmt.setString(4, product.getCode());

        return pstmt.executeUpdate();
    }

    @Override
    public int save(Product product) throws SQLException {
        String sql = """
                INSERT INTO products (code, name, price, qty, status)
                VALUES (?, ?, ?, ?, ?)
        """;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, product.getCode());
        pstmt.setString(2, product.getName());
        pstmt.setBigDecimal(3, product.getPrice());
        pstmt.setInt(4, product.getQty());
        pstmt.setBoolean(5, product.getStatus());

        return pstmt.executeUpdate();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = """
                SELECT *
                FROM products
        """;
        ResultSet rs = stmt.executeQuery(sql);
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setCode(rs.getString("code"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setQty(rs.getInt("qty"));
            product.setStatus(rs.getBoolean("status"));

            products.add(product);
        }
        return products;
    }
}

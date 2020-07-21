package dao;

import models.Customer;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCustomerDao implements CustomerDao {
    private final Sql2o sql2o;

    public Sql2oCustomerDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Customer customer) {
        String sql = "INSERT INTO customers (name, location, email) VALUES (:name, :location, :email);";
        try (Connection conn = sql2o.open()) {
            int id = (int) conn.createQuery(sql, true)
                    .bind(customer)
                    .executeUpdate()
                    .getKey();
            customer.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT * FROM customers;";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Customer.class);
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = :id;";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Customer.class);
        }
    }

    @Override
    public void update(int id, String name, String location, String email) {
        String sql = "UPDATE customers SET (name, location, email) = (:name, :location, :email) WHERE id = :id; ";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("location", location)
                    .addParameter("email", email)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM customers WHERE id = :id ;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM customers;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        }
    }

}

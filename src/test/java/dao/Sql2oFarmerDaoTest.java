package dao;

import models.Farmer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oFarmerDaoTest {
    private static Sql2oFarmerDao farmerDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/agric_hub_test";
        Sql2o sql2o = new Sql2o(connectionString, "maureenbett", "kenyan082bett");
        farmerDao = new Sql2oFarmerDao(sql2o);
        conn = (Connection) sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        farmerDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }
    @Test
    public void addingFarmerSetsId() {
        Farmer farmer= setFarmer();
        int originalFarmerId = farmer.getId();
        farmerDao.add(farmer);
        assertNotEquals(originalFarmerId, farmer. getId());
    }

    @Test
    public void add_individualFarmerCanBeFoundById() {
        Farmer farmer = setFarmer();
        farmerDao.add(farmer);
        Farmer foundFarmer = farmerDao.getId(farmer.getId());
        assertEquals(farmer, foundFarmer);
    }

    @Test
    public void findById_individualFarmerCanBeFoundById() {
        Farmer farmer = setFarmer();
        farmerDao.add(farmer);
        Farmer foundCustomer = farmerDao.getId(farmer.getId());
        assertEquals(farmer, foundCustomer);
    }

    @Test
    public void getAll_allFarmersAreReturnedCorrectly() {
        Farmer farmer = setFarmer();
        farmerDao.add(farmer);
        assertEquals(1, farmerDao.getAll().size());
    }

    @Test
    public void getAll_nothingIsReturnedFromAnEmptyDatabase() {
        assertEquals(0, farmerDao.getAll().size());
    }

    @Test
    public void update_farmerIsUpdatedCorrectly() {
        Farmer farmer = setFarmer();
        farmerDao.add(farmer);
        int currentId = farmer.getId();
        farmerDao.update(currentId, "kevin", "UG", "kipkev@gmail.com");
        Farmer updatedFarmer = farmerDao.getId(currentId);
        assertNotEquals(farmer, updatedFarmer);
    }

    @Test
    public void deleteById_individualFarmerIsDeletedCorrectlyByItsId() {
        Farmer farmer = setFarmer();
        Farmer otherFarmer = setFarmer();
        farmerDao.add(farmer);
        farmerDao.add(otherFarmer);
        farmerDao.deleteById(farmer.getId());
        assertEquals(1, farmerDao.getAll().size());
    }

    @Test
    public void clearAll_allAddedFarmersCanBeCleared() {
        Farmer farmer = setFarmer();
        Farmer otherFarmer = setFarmer();
        farmerDao.add(farmer);
        farmerDao.add(otherFarmer);
        farmerDao.clearAll();
        assertEquals(0, farmerDao.getAll().size());
    }

    public Farmer setFarmer(){
        return new Farmer("henry", "nike", "hen@gmail.com");
    }
}
package cz.muni.fi.pa165.smartphonEShop.dao;

import cz.muni.fi.pa165.smartphonEShop.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.smartphonEShop.entity.Phone;
import cz.muni.fi.pa165.smartphonEShop.entity.Stock;
import cz.muni.fi.pa165.smartphonEShop.enums.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by Roman Nahalka
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class StockDaoTest extends AbstractTestNGSpringContextTests 
{
    @Autowired
    private StockDao stock;

    @PersistenceContext
    private EntityManager em;

    private Stock stock1;
    private Stock stock2;

    @BeforeMethod
    void setUp()
    {
        stock1 = new Stock();
        stock2 = new Stock();
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        stock1.setName("Hlavni");
        stock2.setName("Vedlejsi");
        phone1.setModelName("S6");
        phone2.setModelName("S7");

        phone1.setPrice(123);
        phone2.setPrice(345);

        phone1.setTechnicalInfo("info1");
        phone2.setTechnicalInfo("info2");

        phone1.setManufacturer(Manufacturer.APPLE);
        phone2.setManufacturer(Manufacturer.HTC);

        stock1.addPhone(phone1);
        stock2.addPhone(phone2);


    }

    @Test
    public void findAll()
    {
        stock.create(stock1);
        stock.create(stock2);

        List<Stock> stocks = stock.findAll();
        Assert.assertEquals(stocks.size(), 2);

        Assert.assertTrue(stocks.contains(stock1));
        Assert.assertTrue(stocks.contains(stock2));
    }

    @Test
    public void findById()
    {
        stock.create(stock1);
        stock.create(stock2);

        Assert.assertEquals("Hlavni", stock.findById(stock1.getId()).getName());
        Assert.assertEquals("Vedlejsi", stock.findById(stock2.getId()).getName());
        Assert.assertNotEquals("Vedlejsi", stock.findById(stock1.getId()).getName());
        Assert.assertNotEquals("Hlavni", stock.findById(stock2.getId()).getName());
    }

    @Test
    public void delete()
    {
        stock.create(stock1);
        stock.create(stock2);

        List<Stock> stocks = stock.findAll();
        Assert.assertEquals(stocks.size(), 2);

        stock.delete(stock1);
    }

    @Test
    public void update()
    {
        stock.create(stock1);

        stock1.setName("Vedlejsi");
        stock.update(stock1);

        Assert.assertNotEquals("Hlavni", stock.findById(stock1.getId()).getName());
        Assert.assertEquals("Vedlejsi", stock.findById(stock1.getId()).getName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNull()
    {
       stock.create(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNull()
    {
        stock.update(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNull()
    {
        stock.delete(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdNull()
    {
        stock.findById(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdNegative()
    {
        stock.findById((long)-1);
    }
}

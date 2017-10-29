/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;
import cz.fi.muni.pa165.library.persistance.config.PersistenceApplicationContext;
import cz.fi.muni.pa165.library.persistance.*;
import cz.fi.muni.pa165.library.persistance.dao.LoanItemDao;
import cz.fi.muni.pa165.library.persistance.entity.Book;
import cz.fi.muni.pa165.library.persistance.entity.Loan;
import cz.fi.muni.pa165.library.persistance.entity.LoanItem;
import cz.fi.muni.pa165.library.persistance.entity.Member;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 *
 * @author xchomo
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LoanItemDaoTest extends AbstractTestNGSpringContextTests{
    
        
    @Autowired
    private LoanItemDao loanItemDao;
    
    @PersistenceContext
    private EntityManager em;
    
    private Loan l1;
    private Loan l2;
    
    private Book b1;
    private Book b2;
    
    private LoanItem l1i1;
    private Member member;
    
    @BeforeMethod
    public void setUp() {
        l1 = new Loan();
        
        b1 = new Book("author1", "title1");
        b2 = new Book("author2", "title2");
        
        
        
        member = new Member();
        member.setAddress("address");
        member.setEmail("daas@gmail.com");
        member.setFirstName("firstname");
        member.setSurname("surname");
        member.setPhone("+420700000000");
        member.setJoinedDate(new Date());
        
        
        l1i1 = new LoanItem();
        
        l1i1.setBook(b1);
        l1i1.setLoan(l1);
        
        l1.addLoanItem(l1i1);
        
        Date loanCreatedl1 = new Date();
        Set<LoanItem> loanItems = new HashSet<>();
        loanItems.add(l1i1);
        
        l1.setLoanCreated(loanCreatedl1);
        l1.setLoanItems(loanItems);
        l1.setMember(member);
        
        em.persist(b1);
        em.persist(b2);
        em.persist(member);
        em.persist(l1);
    }
   
    @Test(expectedExceptions = {org.springframework.dao.InvalidDataAccessApiUsageException.class})
    public void createNull(){
        loanItemDao.create(null);
    }
    
    @Test
    public void create(){
        
        loanItemDao.create(l1i1);
       
        List<LoanItem> loanItems = em.createQuery("select li from LoanItem li where li.id=:id", LoanItem.class).setParameter("id", l1i1.getId()).getResultList();
        Assert.assertEquals(l1i1, loanItems.get(0));
       
    }
    
    @Test
    public void delete(){
        List<LoanItem> loanItemsMock = new ArrayList<>();

        em.persist(l1i1);
        
        loanItemsMock.add(l1i1);
        
        List<LoanItem> loanItems = em.createQuery("select b from LoanItem b", LoanItem.class).getResultList();
        Assert.assertEquals(loanItems, loanItemsMock);
        loanItemDao.delete(l1i1);
        loanItems = em.createQuery("select b FROM LoanItem b", LoanItem.class).getResultList();
        Assert.assertEquals(loanItems.size(), 0);
    }
    
    @Test(expectedExceptions = {org.springframework.dao.InvalidDataAccessApiUsageException.class})
    public void deleteNotExisting(){
        loanItemDao.delete(l1i1);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void deleteNull(){
        loanItemDao.delete(null);
    }
    
    //TODO: update
    
//    @Test
//    public void update(){
//
//        em.persist(l1);
//        
//        List<Loan> loans = em.createQuery("select l from Loan l where l.id=:id", Loan.class).setParameter("id", l1.getId()).getResultList();
//        Assert.assertEquals(loans.size(), 1);
//        
//        LoanItem l1i2 = new LoanItem();
//        
//        l1i2.setBook(b2);
//        l1i2.setLoan(l1);
//        em.persist(l1i2);
//        l1.addLoanItem(l1i1);
//       
//        loanDao.update(l1);
//        
//        loans = em.createQuery("select l from Loan l where l.id=:id", Loan.class).setParameter("id", l1.getId()).getResultList();
//        Assert.assertEquals(loans.size(), 1);
//        Assert.assertEquals(loans.get(0), l1);
//        
//    }
    
    @Test
    public void findAll(){
        List<LoanItem> loanItemsMock = new ArrayList<>();

        
        em.persist(l1i1);
        
        loanItemsMock.add(l1i1);
        
        Assert.assertEquals(loanItemDao.findAll(), loanItemsMock);
    }
    
    @Test
    public void findById(){
       em.persist(l1i1);
        
        List<LoanItem> loanItems = em.createQuery("select l from LoanItem l where l.id=:id", LoanItem.class).setParameter("id", l1i1.getId()).getResultList();
        Assert.assertEquals(loanItems.size(), 1);
        LoanItem loanFound = loanItemDao.findById(l1i1.getId());
        Assert.assertEquals(loanFound, l1i1);
    }
    
    @Test
    public void findByLoan(){
       em.persist(l1i1);
       LoanItem loanFound = loanItemDao.findById(l1i1.getId());
       Assert.assertEquals(loanFound, l1i1);
    }
}

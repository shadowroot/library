/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.library.persistance.config.PersistenceApplicationContext;
import cz.fi.muni.pa165.library.persistance.dao.LoanDao;
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
import javax.persistence.PersistenceContext;
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
 *  Unit tests for LoanDao for implementation of basic CRUD operations.
 * 
 * @author Jan Tlamicha(xtlamich)
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LoanDaoTest extends AbstractTestNGSpringContextTests{
    
    @Autowired
    private LoanDao loanDao;
    
    
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
        
        em.persist(b1);
        em.persist(b2);
        
        member = new Member();
        member.setAddress("address");
        member.setEmail("daas@gmail.com");
        member.setFirstName("firstname");
        member.setSurname("surname");
        member.setPhone("+420700000000");
        member.setJoinedDate(new Date());
        
        em.persist(member);
        
        l1i1 = new LoanItem();
        
        l1i1.setBook(b1);
        l1i1.setLoan(l1);
        em.persist(l1i1);
        l1.addLoanItem(l1i1);
        
        Date loanCreatedl1 = new Date();
        Set<LoanItem> loanItems = new HashSet<>();
        loanItems.add(l1i1);
        
        l1.setLoanCreated(loanCreatedl1);
        l1.setLoanItems(loanItems);
        l1.setMember(member);
    }
    
    
    @Test(expectedExceptions = {org.springframework.dao.InvalidDataAccessApiUsageException.class})
    public void createNull(){
        loanDao.create(null);
    }
    
    
    @Test
    public void create(){
        
        loanDao.create(l1);
        
        List<Loan> loans = em.createQuery("select b from Loan b where b.id=:id", Loan.class).setParameter("id", l1.getId()).getResultList();
        Assert.assertEquals(l1, loans.get(0));
    }
    
    
    @Test
    public void delete(){
        List<Loan> loansMock = new ArrayList<>();

        em.persist(l1);
        
        loansMock.add(l1);
        
        List<Loan> loans = em.createQuery("select b from Loan b", Loan.class).getResultList();
        Assert.assertEquals(loans, loansMock);
        em.remove(l1i1);
        loanDao.delete(l1);
        loans = em.createQuery("select b FROM Loan b", Loan.class).getResultList();
        Assert.assertEquals(loans.size(), 0);
    }
    
    
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void deleteNotExisting(){
        loanDao.delete(l1);
    }
    
    
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void deleteNull(){
        loanDao.delete(null);
    }
    
    @Test
    public void findById(){
        em.persist(l1);
        
        List<Loan> loans = em.createQuery("select l from Loan l where l.id=:id", Loan.class).setParameter("id", l1.getId()).getResultList();
        Assert.assertEquals(loans.size(), 1);
        Loan loanFound = loanDao.findById(l1.getId());
        Assert.assertEquals(loanFound, l1);
    }
    
    @Test
    public void update(){

        em.persist(l1);
        
        List<Loan> loans = em.createQuery("select l from Loan l where l.id=:id", Loan.class).setParameter("id", l1.getId()).getResultList();
        Assert.assertEquals(loans.size(), 1);
        
        LoanItem l1i2 = new LoanItem();
        
        l1i2.setBook(b2);
        l1i2.setLoan(l1);
        
        Set<LoanItem> checkedLoanItems = new HashSet<>();
        checkedLoanItems.add(l1i1);
        checkedLoanItems.add(l1i2);
        
        em.persist(l1i2);
        l1.addLoanItem(l1i1);
        l1.addLoanItem(l1i2);
       
        loanDao.update(l1);
        
        loans = em.createQuery("select l from Loan l where l.id=:id", Loan.class).setParameter("id", l1.getId()).getResultList();
        Assert.assertEquals(loans.size(), 1);
        Assert.assertEquals(loans.get(0), l1);
        Loan checkedLoan = loans.get(0);
        Assert.assertEquals(checkedLoan.getLoanItems(), checkedLoanItems);
    }

    @Test
    public void findAll(){
        List<Loan> loansMock = new ArrayList<>();

        em.persist(l1);
        
        loansMock.add(l1);
        
        Assert.assertEquals(loanDao.findAll(), loansMock);
    }
    
    @Test
    public void allLoansOfMember(){
        em.persist(l1);
        
        List<Loan> loansMember = loanDao.allLoansOfMember(member);
        List<Loan> loans = em.createQuery("select l from Loan l where l.member = :member", Loan.class).setParameter("member", l1.getMember()).getResultList();
      
        Assert.assertEquals(loansMember, loans);
    }
    
    @Test(expectedExceptions = {org.springframework.dao.InvalidDataAccessApiUsageException.class})
    public void allLoansOfMemberNoneExisting(){
        loanDao.allLoansOfMember(null);
    }
    
    
}

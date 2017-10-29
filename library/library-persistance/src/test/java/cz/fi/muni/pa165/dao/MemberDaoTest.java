/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;
import cz.fi.muni.pa165.library.persistance.config.PersistenceApplicationContext;
import cz.fi.muni.pa165.library.persistance.dao.BookDao;
import cz.fi.muni.pa165.library.persistance.dao.MemberDao;
import cz.fi.muni.pa165.library.persistance.entity.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class MemberDaoTest extends AbstractTestNGSpringContextTests{
    
        
    @Autowired
    private MemberDao memberDao;
    
    @PersistenceContext
    private EntityManager em;
    
    private Member m1;
    private Member m2;
    
    
    
    
    @BeforeMethod
    public void setUp() {
        m1 = new Member();
        m1.setAddress("address");
        m1.setEmail("daas@gmail.com");
        m1.setFirstName("firstname");
        m1.setSurname("surname");
        m1.setPhone("+420700000000");
        m1.setJoinedDate(new Date());
        
        m2 = new Member();
        m2.setAddress("address2");
        m2.setEmail("daas2@gmail.com");
        m2.setFirstName("firstname2");
        m2.setSurname("surname2");
        m2.setPhone("+420700000001");
        m2.setJoinedDate(new Date());
    }
    
    @Test(expectedExceptions = {org.springframework.dao.InvalidDataAccessApiUsageException.class})
    public void createNull(){
        memberDao.create(null);
    }
    
    @Test
    public void create(){
        memberDao.create(m1);
        List<Member> members = em.createQuery("select m from Member m where m.id=:id", Member.class).setParameter("id", m1.getId()).getResultList();
        Assert.assertEquals(m1, members.get(0));
    }
    
    @Test
    public void delete(){

        List<Member> membersMock = new ArrayList<>();

        em.persist(m1);
        em.persist(m2);
        
        membersMock.add(m1);
        membersMock.add(m2);
        
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        Assert.assertEquals(members, membersMock);
        memberDao.delete(m1);
        memberDao.delete(m2);
        members = em.createQuery("select m from Member m", Member.class).getResultList();
        Assert.assertEquals(members.size(), 0);
    }
    
    @Test(expectedExceptions = {org.springframework.dao.InvalidDataAccessApiUsageException.class})
    public void deleteNotExisting(){
        memberDao.delete(m1);
    }
    
    @Test
    public void findById(){

        em.persist(m1);
        em.persist(m2);
        
        List<Member> members = em.createQuery("select m from Member m where m.id=:id", Member.class).setParameter("id", m1.getId()).getResultList();
        Member memberFound = memberDao.findById(m1.getId());
        Assert.assertEquals(memberFound, m1);
        
        members = em.createQuery("select m from Member m where m.id=:id", Member.class).setParameter("id", m2.getId()).getResultList();
        
        memberFound = memberDao.findById(m2.getId());
        Assert.assertEquals(memberFound, m2);
    }
    
    @Test
    public void update(){

        em.persist(m1);
        em.persist(m2);
        
        List<Member> members = em.createQuery("select m from Member m where m.id=:id", Member.class).setParameter("id", m1.getId()).getResultList();
        Assert.assertEquals(members.size(), 1);
        Assert.assertEquals(members.get(0), m1);
        
        m1.setAddress("New address");
        m1.setFirstName("New firstname");
        memberDao.update(m1);
        
        members = em.createQuery("select m from Member m where m.id=:id", Member.class).setParameter("id", m1.getId()).getResultList();
        Assert.assertEquals(members.size(), 1);
        Assert.assertEquals(members.get(0), m1);
    }
    
     @Test
    public void findAll(){
        List<Member> membersMock = new ArrayList<>();
        em.persist(m1);
        em.persist(m2);
        
        membersMock.add(m1);
        membersMock.add(m2);
        
        Assert.assertEquals(memberDao.findAll(), membersMock);
    }
}

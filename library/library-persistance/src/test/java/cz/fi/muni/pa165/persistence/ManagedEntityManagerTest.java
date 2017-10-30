/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.persistence;

import cz.fi.muni.pa165.library.persistance.config.PersistenceApplicationContext;
import cz.fi.muni.pa165.library.persistance.entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.junit.Assert;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Entity manager test persistence
 * @author Jan Tlamicha (xtlamich)
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ManagedEntityManagerTest extends AbstractTransactionalTestNGSpringContextTests{
   
	
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Test
    public void context(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book b1 = new Book("asdasd", "sfasfas");
        em.persist(b1);
        em.getTransaction().commit();
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        Assert.assertEquals(books.get(0), b1);
        em.close();
    }
    
}

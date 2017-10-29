
package cz.fi.muni.pa165.library.persistance.dao;

import cz.fi.muni.pa165.library.persistance.entity.Loan;
import cz.fi.muni.pa165.library.persistance.entity.LoanItem;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.springframework.stereotype.Repository;
/**
 *
 * @author xchomo
 */
@Repository
public class LoanItemDaoImpl implements LoanItemDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(LoanItem loanItem) {
       em.persist(loanItem);
    }

    @Override
    public void delete(LoanItem loanItem) {
        Objects.requireNonNull(loanItem, "null argument loanItem");
        em.remove(findById(loanItem.getId()));
    }

    
    
    @Override
    public void update(LoanItem loanItem) {
        em.merge(loanItem);
    }

    @Override
    public LoanItem findById(Long id) {
        return em.find(LoanItem.class, id);
    }

    @Override
    public List<LoanItem> findByLoan(Loan loan) {
        return em.createQuery("SELECT l FROM LoanItem l WHERE l.loan = :loanid", LoanItem.class)
                                                                    .setParameter("loanid", loan)
                                                                    .getResultList();
    }

    @Override
    public List<LoanItem> findAll() {
        return em.createQuery("select l from LoanItem l", LoanItem.class).getResultList();
    }
    
}

package cz.fi.muni.pa165.library.persistance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entity representing loan 
 *
 * @author xchomo
 */
@Entity
public class Loan{

    public Loan() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanReturned;
    
    @NotNull
    @ManyToOne
    private Member member;
    
    @NotNull
    @Column(nullable = false)
    @OneToMany(mappedBy = "loan")
    private Set<LoanItem> loanItems = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLoanCreated() {
        return loanCreated;
    }

    public void setLoanCreated(Date loanCreated) {
        this.loanCreated = loanCreated;
    }

    public Date getLoanReturned() {
        return loanReturned;
    }

    public void setLoanReturned(Date loanReturned) {
        this.loanReturned = loanReturned;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<LoanItem> getLoanItems() {
        return loanItems;
    }

    public void setLoanItems(Set<LoanItem> loanItems) {
        this.loanItems = loanItems;
    }
    
    public void addLoanItem(LoanItem item){
        if(loanItems == null){
            loanItems = new HashSet<>();
        }
        loanItems.add(item);
    }
    
    public void removeLoanItem(LoanItem item){
        loanItems.remove(item);
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj != null && obj.getClass() == getClass()){
            Loan loan = (Loan)obj;
            if(loan.getLoanCreated() == getLoanCreated() &&
                    loan.getLoanItems() == getLoanItems() && 
                    loan.getLoanReturned() == getLoanReturned() &&
                    loan.getMember() == getMember()){
                return true;
            }
        }
        return false;
    }
    
    
    
}

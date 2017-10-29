    package cz.fi.muni.pa165.library.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity representing relationship between Loan and Item entities
 * 
 * @author xchomo
 */
@Entity
public class LoanItem{
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    private Book book;
    
    @ManyToOne
    private Loan loan;
    
    
//    @Enumerated(EnumType.STRING)
//    private String condition;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    
}

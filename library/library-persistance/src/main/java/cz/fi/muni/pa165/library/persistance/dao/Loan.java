package cz.fi.muni.pa165.library.persistance.dao;

import cz.fi.muni.pa165.library.persistance.entity.Member;
import java.util.List;

/**
 *
 * @author xchomo
 */
public interface Loan {
    
    /**
     * Create loan in database
     *
     * @param loan to be created
     */
    public void create(Loan loan);

    /**
     * Delete loan from database
     *
     * @param loan to be deleted
     */
    public void delete(Loan loan);

    /**
     * Find a loan with specific id from the database
     *
     * @param id of loan to be returned
     * @return loan with specified Id 
     */
    public Loan findById(Long id);

    /**
     * Returns all loans for the member 
     *
     * @param member for witch loans should be returned
     * @return all loans for specified member 
     */
    
    public List<Loan> allLoansOfMember(Member member);

    /**
     * Returns all  loans of the member
     *
     * @return all loans 
     */
    public List<Loan> findAll();

    /**
     * Updates specified loan
     *
     * @param loan to be updated
     */
    public void update(Loan loan);
}

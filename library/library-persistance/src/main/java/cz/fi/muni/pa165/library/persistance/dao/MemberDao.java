package cz.fi.muni.pa165.library.persistance.dao;
import  cz.fi.muni.pa165.library.persistance.entity.Member;
import java.util.List;
/**
 *  Data access object (DAO) Interface for Member class
 * 
 * @author Tomas Chomo (xchomo)
 */
public interface MemberDao {
 
    /**
     * Create a member in the database
     *
     * @param member to be created
     */
    public void create(Member member);
    
    /**
     * Delete the member from the database
     *
     * @param member to be deleted
     */
    public void delete(Member member);
    
    /**
     * Update a member information in the database
     *
     * @param member to be created
     */
    public void update(Member member);
    
    /**
     * Find all members in the database
     *
     * @return List of members
     */
    public List<Member> findAll();
    
    /**
     * Find a member with specific id from the database
     *
     * @param id of the member
     * @return member with the given id
     */
    public Member findById(Long id); 
}

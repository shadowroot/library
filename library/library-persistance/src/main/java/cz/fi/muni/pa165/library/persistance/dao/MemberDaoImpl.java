/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.library.persistance.dao;

import cz.fi.muni.pa165.library.persistance.entity.Member;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Member member) {
        em.persist(member);
    }

    @Override
    public void delete(Member member) {
        Objects.requireNonNull(member, "null argument member");
         em.remove(findById(member.getId()));
    }

    @Override
    public void update(Member member) {
         em.merge(member);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }
    
}

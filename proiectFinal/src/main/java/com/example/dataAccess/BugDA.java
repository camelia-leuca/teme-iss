package com.example.dataAccess;

import com.example.models.Bug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
public class BugDA {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bugtrackerPU");

    public void addBug(Bug bug) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(bug);
        em.getTransaction().commit();
        em.close();
    }

    public void updateBug(Bug bug) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(bug);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteBug(Long bugId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Bug bug = em.find(Bug.class, bugId);
        if (bug != null) {
            em.remove(bug);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Bug getBug(Long bugId) {
        EntityManager em = emf.createEntityManager();
        Bug bug = em.find(Bug.class, bugId);
        em.close();
        return bug;
    }

    public List<Bug> getAllBugs() {
        EntityManager em = emf.createEntityManager();
        List<Bug> bugs = em.createQuery("from Bug", Bug.class).getResultList();
        em.close();
        return bugs;
    }
}

package com.example.dataAccess;

import com.example.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class UserDA {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bugtrackerPU");

    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void updateUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public User getUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        em.close();
        return user;
    }

    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        List<User> users = em.createQuery("from User", User.class).getResultList();
        em.close();
        return users;
    }
}

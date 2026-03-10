package com.alejandro.jpaexample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class DemoTeamPlayerApp {
    public static void main(String[] args) throws SQLException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-javaSE-example");
        EntityManager em = emf.createEntityManager();

//        createTeam(em);
//        createPlayers(em);

        List<Team> teams = em.createQuery("Select t From Team t").getResultList();
        System.out.println(teams.stream().map(t->t.toString()).collect(Collectors.joining("\n","\nLista de teams\n", "")));

        List<Player> players = em.createQuery("Select p From Player p").getResultList();
        System.out.println(players.stream().map(p->p.toString()).collect(Collectors.joining("\n", "\nLista de players\n", "")));

        Player p = players.get(players.size()-1);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();


        emf.close();
        emf.close();
    }
    public static void createTeam(EntityManager em){
        em.getTransaction().begin();

        Team team1 = new Team();
        team1.setTeamname("AguilasVerdes");
        team1.setLeague("Libertadores");
        em.persist(team1);

        Team team2 = new Team();
        team2.setTeamname("Mbeju Lovers");
        team2.setLeague("Local");
        em.persist(team2);

        Team team3 = new Team();
        team3.setTeamname("Harmony Listers");
        team3.setLeague("Mundial");
        em.persist(team3);

        em.getTransaction().commit();

    }
    public static void createPlayers(EntityManager em){
        em.getTransaction().begin();

        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        player1.setFirstname("Fury");
        player1.setLastname("Fast");
        player1.setJerseynumber(234544);
        player1.setLastspokenwords("I'll be back");
        em.persist(player1);

        player2.setFirstname("Blaster");
        player2.setLastname("Blast");
        player2.setJerseynumber(234544);
        player2.setLastspokenwords("YEAAAHH!");
        em.persist(player2);

        player3.setFirstname("Michael");
        player3.setLastname("dude");
        player3.setJerseynumber(234544);
        player3.setLastspokenwords("Thank you all!");
        em.persist(player3);

        em.getTransaction().commit();
    }

}

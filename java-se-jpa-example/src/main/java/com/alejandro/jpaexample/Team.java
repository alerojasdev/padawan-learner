package com.alejandro.jpaexample;

import javax.persistence.*;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private int teamId;
    @Column(name = "teamname")
    private String teamname;
    @Column(name = "league")
    private String league;

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamname='" + teamname + '\'' +
                ", league='" + league + '\'' +
                '}';
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamname() {
        return teamname;
    }

    public String getLeague() {
        return league;
    }
}
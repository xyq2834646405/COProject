package com.xyq.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups", catalog = "office")
public class Groups implements Serializable,Comparable<Groups> {
    private int gid;
    private String title;
    private String note;
    private Set<Action> actions = new HashSet<Action>(0);
    private Set<Role> roles = new HashSet<Role>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "groups")
    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "groups")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "gid=" + gid +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public int compareTo(Groups o) {
        if (this.gid < o.gid) {
            return -1;
        } else if (this.gid > o.gid) {
            return 1;
        }
        return 0;
    }
}
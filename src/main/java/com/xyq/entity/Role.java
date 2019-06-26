package com.xyq.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", catalog = "office")
public class Role implements Serializable {
    private int rid;
    private String title;
    private String note;
    private Set<User> users = new HashSet<User>(0);
    private Set<Groups> groups = new HashSet<Groups>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinTable(name = "role_groups",catalog = "office",
        joinColumns = {@JoinColumn(name = "rid",referencedColumnName = "rid",updatable = false)},
        inverseJoinColumns = {@JoinColumn(name = "gid",referencedColumnName = "gid",updatable = false)}
    )
    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
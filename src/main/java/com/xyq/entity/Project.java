package com.xyq.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project", catalog = "office")
public class Project implements Serializable {
    private Integer proid;
    private User userByCreid;
    private User userByMgr;
    private String title;
    private String name;
    private String note;
    private Date pubdate;
    private Set<Task> tasks = new HashSet<Task>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proid")
    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creid")
    public User getUserByCreid() {
        return userByCreid;
    }

    public void setUserByCreid(User userByCreid) {
        this.userByCreid = userByCreid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mgr")
    public User getUserByMgr() {
        return userByMgr;
    }

    public void setUserByMgr(User userByMgr) {
        this.userByMgr = userByMgr;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "pubdate")
    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}

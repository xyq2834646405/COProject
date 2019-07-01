package com.xyq.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", catalog = "office")
public class User implements Serializable {
    private String userid;
    private String password;
    private String name;
    private int level;
    private String phone;
    private String email;
    private String photo;
    private Date lastlogin;
    private Integer lockflag;
    private Role role;
    private Set<Document> documents = new HashSet<Document>(0);
    private Set<Notice> notices = new HashSet<Notice>(0);
    private Set<Task> tasksForReceiver = new HashSet<Task>(0);
    private Set<Project> projectsForMgr = new HashSet<Project>(0);
    private Set<Project> projectsForCreid = new HashSet<Project>(0);
    private Set<Task> tasksForCanceler = new HashSet<Task>(0);
    private Set<Task> tasksForCreator = new HashSet<Task>(0);

    public User() {

    }

    public User(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    @Id
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Column(name = "lastlogin")
    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    @Column(name = "lockflag")
    public Integer getLockflag() {
        return lockflag;
    }

    public void setLockflag(Integer lockflag) {
        this.lockflag = lockflag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rid")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Notice> getNotices() {
        return notices;
    }

    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByReceiver")
    public Set<Task> getTasksForReceiver() {
        return tasksForReceiver;
    }

    public void setTasksForReceiver(Set<Task> tasksForReceiver) {
        this.tasksForReceiver = tasksForReceiver;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByMgr")
    public Set<Project> getProjectsForMgr() {
        return projectsForMgr;
    }

    public void setProjectsForMgr(Set<Project> projectsForMgr) {
        this.projectsForMgr = projectsForMgr;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByCreid")
    public Set<Project> getProjectsForCreid() {
        return projectsForCreid;
    }

    public void setProjectsForCreid(Set<Project> projectsForCreid) {
        this.projectsForCreid = projectsForCreid;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByCanceler")
    public Set<Task> getTasksForCanceler() {
        return tasksForCanceler;
    }

    public void setTasksForCanceler(Set<Task> tasksForCanceler) {
        this.tasksForCanceler = tasksForCanceler;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByCreator")
    public Set<Task> getTasksForCreator() {
        return tasksForCreator;
    }

    public void setTasksForCreator(Set<Task> tasksForCreator) {
        this.tasksForCreator = tasksForCreator;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", lastlogin=" + lastlogin +
                ", lockflag=" + lockflag +
                '}';
    }
}

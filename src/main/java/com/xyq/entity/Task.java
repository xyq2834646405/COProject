package com.xyq.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task", catalog = "office")
public class Task implements Serializable {
    private Integer tid;
    private User userByCanceler;
    private Tasktype tasktype;
    private User userByReceiver;
    private User userByCreator;
    private Project project;
    private String title;
    private Date expiredate;
    private Date createdate;
    private Date finishdate;
    private Integer status;
    private Date lastupdatedate;
    private Integer priority;
    private Integer expend;
    private Integer estimate;
    private String note;
    private String rnote;
    private String cnote;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canceler")
    public User getUserByCanceler() {
        return userByCanceler;
    }

    public void setUserByCanceler(User userByCanceler) {
        this.userByCanceler = userByCanceler;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ttid")
    public Tasktype getTasktype() {
        return tasktype;
    }

    public void setTasktype(Tasktype tasktype) {
        this.tasktype = tasktype;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    public User getUserByReceiver() {
        return userByReceiver;
    }

    public void setUserByReceiver(User userByReceiver) {
        this.userByReceiver = userByReceiver;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    public User getUserByCreator() {
        return userByCreator;
    }

    public void setUserByCreator(User userByCreator) {
        this.userByCreator = userByCreator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proid")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "expiredate")
    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    @Column(name = "createdate")
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Column(name = "finishdate")
    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "lastupdatedate")
    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column(name = "expend")
    public Integer getExpend() {
        return expend;
    }

    public void setExpend(Integer expend) {
        this.expend = expend;
    }

    @Column(name = "estimate")
    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "rnote")
    public String getRnote() {
        return rnote;
    }

    public void setRnote(String rnote) {
        this.rnote = rnote;
    }

    @Column(name = "cnote")
    public String getCnote() {
        return cnote;
    }

    public void setCnote(String cnote) {
        this.cnote = cnote;
    }
}

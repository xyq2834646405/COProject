package com.xyq.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "notice", catalog = "office")
public class Notice implements Serializable {
    private Integer snid;
    private User user;
    private String title;
    private Date pubdate;
    private String content;
    private Integer level;
    private Integer rnum;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snid")
    public Integer getSnid() {
        return snid;
    }

    public void setSnid(Integer snid) {
        this.snid = snid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "pubdate")
    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "rnum")
    public Integer getRnum() {
        return rnum;
    }

    public void setRnum(Integer rnum) {
        this.rnum = rnum;
    }
}

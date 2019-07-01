package com.xyq.entity;

import javax.persistence.*;

@Entity
@Table(name = "tasktype", catalog = "office")
public class Tasktype {
    private Integer ttid;
    private String title;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ttid")
    public Integer getTtid() {
        return ttid;
    }

    public void setTtid(Integer ttid) {
        this.ttid = ttid;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

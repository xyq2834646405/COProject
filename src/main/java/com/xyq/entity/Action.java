package com.xyq.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "action", catalog = "office")
public class Action implements Serializable {
    private int actid;
    private String title;
    private String url;
    private Groups groups;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actid")
    public int getActid() {
        return actid;
    }

    public void setActid(int actid) {
        this.actid = actid;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gid")
    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actid=" + actid +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

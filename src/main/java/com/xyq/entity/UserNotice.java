package com.xyq.entity;

import java.io.Serializable;
import java.util.Date;

public class UserNotice implements Serializable {
    private User user;
    private Notice notice;
    private Date rdate ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }
}

package com.xyq.service.admin.impl;

import com.xyq.dao.INoticeDao;
import com.xyq.dao.IUserDao;
import com.xyq.entity.Notice;
import com.xyq.entity.User;
import com.xyq.service.admin.INoticeServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class NoticeServiceAdminImpl implements INoticeServiceAdmin {

    @Autowired
    private INoticeDao noticeDao;

    @Autowired
    private IUserDao userDao;

    public boolean insert(Notice vo) throws Exception {
        User user = userDao.findById(vo.getUser().getUserid());
        if(user.getLevel() == 0 || user.getLevel()==1){//只有管理员才可以发布公告
            if(vo.getLevel()==2 || vo.getLevel()==3){
                vo.setPubdate(new Date());
                return noticeDao.doCreate(vo);
            }
        }
        return false;
    }

    public Notice updatePre(Integer snid) throws Exception {
        return noticeDao.findById(snid);
    }

    public boolean update(Notice vo) throws Exception {
        User user = userDao.findById(vo.getUser().getUserid());
        if(user.getLevel() == 0 || user.getLevel()==1){//只有管理员才可以发布公告
            if(vo.getLevel()==2 || vo.getLevel()==3) {
                return noticeDao.doUpdate(vo);
            }
        }
        return false;
    }

    public boolean delete(Set<Integer> ids) throws Exception {
        if (ids.size()>0){
            return noticeDao.doRemoveBatch(ids);
        }
        return false;
    }

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allNotices",noticeDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("noticeCount",noticeDao.getAllCount(column,keyWord));
        return null;
    }

    public boolean updateLevel(int snid, int level) throws Exception {
        if (level==2 || level==3){
            return noticeDao.doUpdateLevel(snid,level);
        }
        return false;
    }
}

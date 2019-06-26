package com.xyq.service.admin.impl;

import com.xyq.dao.IActionDao;
import com.xyq.dao.IGroupsDao;
import com.xyq.dao.IRoleDao;
import com.xyq.dao.IUserDao;
import com.xyq.entity.Action;
import com.xyq.entity.Groups;
import com.xyq.entity.Role;
import com.xyq.entity.User;
import com.xyq.service.admin.IUserServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceAdminImpl implements IUserServiceAdmin {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IGroupsDao groupsDao;

    @Autowired
    private IActionDao actionDao;

    public boolean insert(User vo, String userid) throws Exception {
        User user = userDao.findById(userid);
        if(user.getLevel()<=1){
            if(userDao.findById(vo.getUserid())==null){
                //根据级别来确定用户角色
                Role role = new Role();
                if (vo.getLevel() == 2){
                    role.setRid(4);
                }
                if(vo.getLevel()==3){
                    role.setRid(5);
                }
                vo.setRole(role);
                vo.setLockflag(0);
                vo.setPhoto("nophoto.jpg");
                return userDao.doCreate(vo);
            }
        }
        return false;
    }

    public Map<String, Object> updatePre(String userid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user",userDao.findById(userid));
        return map;
    }

    public boolean update(User vo) throws Exception {
        if(userDao.findById(vo.getUserid()).getLevel() > 1){
            Role role = new Role();
            if (vo.getLevel() == 2){
                role.setRid(4);
            }
            if(vo.getLevel()==3){
                role.setRid(5);
            }
            vo.setRole(role);
            return userDao.doUpdateInfo(vo);
        }
        return false;
    }

    public boolean updateLock(Set<String> ids, int lock) throws Exception {
        if(ids.size()==0)
            return false;
        return userDao.doUpdateLock(ids,lock);
    }

    public boolean updateRole(User vo) throws Exception {
        if (userDao.findById(vo.getUserid()).getLevel() > 1){
            if(vo.getRole().getRid()==4){
                vo.setLevel(2);
            }
            if(vo.getRole().getRid()==5){
                vo.setLevel(3);
            }
            return userDao.doUpdateRole(vo);
        }
        return false;
    }

    public boolean updatePassword(User vo) throws Exception {
        if (userDao.findById(vo.getUserid()).getLevel() > 1){
            return userDao.doUpdatePassword(vo);
        }
        return false;
    }

    public boolean checkUser(String userid) throws Exception {
        return userDao.findById(userid) == null;
    }

    public Map<String, Object> list(int lockflag, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allUsers",userDao.findAllUserByLock(lockflag,currentPage,lineSize,column,keyWord));
        map.put("userCount",userDao.getAllUserCountByLock(lockflag,column,keyWord));
        return map;
    }

    public User show(String id) throws Exception {
        User retObject = new User();
        User user = userDao.findById(id);
        if(user!=null){
            //保存用户的基础信息
            retObject.setUserid(id);
            retObject.setLastlogin(user.getLastlogin());
            retObject.setLevel(user.getLevel());
            retObject.setName(user.getName());
            retObject.setPhoto(user.getPhoto());
            retObject.setPhone(user.getPhone());
            retObject.setEmail(user.getEmail());
            retObject.setLockflag(user.getLockflag());
            //保存一个用户的角色信息
            retObject.setRole(roleDao.findById(user.getRole().getRid()));
            //保存角色对应的所有权限组
            Role role = roleDao.findById(user.getRole().getRid());
            List<Groups> allGroups = groupsDao.findAllByRole(user.getRole().getRid());
            Iterator<Groups> iter = allGroups.iterator();
            while (iter.hasNext()){
                Groups gup = iter.next();
                Set<Action> set = new HashSet<Action>();
                set.addAll(actionDao.findAllByGroups(gup.getGid()));
                gup.setActions(set);
            }
            Set<Groups> gset = new TreeSet<Groups>();
            gset.addAll(allGroups);
            role.setGroups(gset);
            retObject.setRole(role);
        }
        return retObject;
    }
}

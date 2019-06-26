package com.xyq.service.common.impl;

import com.xyq.dao.IActionDao;
import com.xyq.dao.IGroupsDao;
import com.xyq.dao.IUserDao;
import com.xyq.entity.Action;
import com.xyq.entity.Groups;
import com.xyq.entity.Role;
import com.xyq.entity.User;
import com.xyq.service.common.IUserServiceCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceCommonImpl implements IUserServiceCommon {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IGroupsDao groupsDao;

    @Autowired
    private IActionDao actionDao;

    public User login(String username, String password) throws Exception {
        User user = userDao.findLogin(username, password);
        if(user != null){//用户已经成功登陆,取出所有权限组
            //根据用户的角色编号查询出用户的所有权限组信息
            List<Groups> allGroups = groupsDao.findAllByRole(user.getRole().getRid());
            //根据权限组的编号取出每一个权限组对应的权限数据
            for (Groups groups:allGroups) {
                Set<Action> set = new HashSet<Action>();
                set.addAll(actionDao.findAllByGroups(groups.getGid()));
                groups.setActions(set);
            }
            //设置用户角色和权限组的关系
            Set<Groups> set = new TreeSet<Groups>();
            set.addAll(allGroups);
            user.getRole().setGroups(set);
            //设置最后一个登陆日期时间
            user.setLastlogin(new Date());
        }
        return user;
    }

    public boolean updatePassword(String userid, String oldpass, String newpass) throws Exception {
        if(userDao.findLogin(userid,oldpass)!=null){//原始密码验证通过
            User user = new User();
            user.setUserid(userid);
            user.setPassword(newpass);
            return userDao.doUpdatePassword(user);
        }
        return false;
    }

    public User updatePre(String userid) throws Exception {
        return userDao.findById(userid);
    }

    public boolean update(User vo) throws Exception {
        return userDao.doUpdate(vo);
    }

    public User show(String id) throws Exception {
        return userDao.findById(id);
    }
}

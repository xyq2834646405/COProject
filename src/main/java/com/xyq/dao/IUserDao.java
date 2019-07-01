package com.xyq.dao;

import com.xyq.entity.Task;
import com.xyq.entity.User;
import com.xyq.util.dao.IDao;

import java.util.List;
import java.util.Set;

public interface IUserDao extends IDao<String, User> {
    /**
     * 用户登陆检查,如果用户名或者密码输入正确则用户以实体对象返回,否则返回null
     * @param username 用户名
     * @param password 密码
     * @return 登陆成功返回对象,登陆失败返回null
     * @throws Exception
     */
    public User findLogin(String username,String password) throws Exception;

    /**
     * 进行密码的修改操作
     * @param vo 包含了用户名和密码信息
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdatePassword(User vo) throws Exception;

    /**
     * 修改用户角色使用
     * @param vo 包含了用户编号和角色编号
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdateRole(User vo) throws Exception;

    /**
     * 更新基础信息操作,不维护照片
     * @param vo 包含了要修改用户的基础信息对象
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdateInfo(User vo) throws Exception;

    /**
     * 进行批量用户的锁定标记操作
     * @param ids 要批量操作的用户编号集合
     * @param lock 锁定的状态
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdateLock(Set<String> ids,Integer lock) throws Exception;

    /**
     * 查询出所有哦的用户信息
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @return
     * @throws Exception
     */
    public List<User> findAllUser(Integer currentPage, Integer lineSize, String column, String keyWord)  throws Exception;

    /**
     * 查询出所有管理员的信息
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @return
     * @throws Exception
     */
    public List<User> findAllAdmin(Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception;

    /**
     * 更具用户的锁定状态,来查询所有的用户信息
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @param lockflag 用户的锁定状态
     * @return
     * @throws Exception
     */
    public List<User> findAllUserByLock(Integer lockflag,Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception;

    /**
     * 查询所有的用户数量
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @param lockflag 用户的锁定状态
     * @return
     * @throws Exception
     */
    public Integer getAllUserCountByLock(Integer lockflag,String column,String keyWord) throws Exception;

    /**
     * 查询所有的用户数量
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @return
     * @throws Exception
     */
    public Integer getAllUserCount(String column,String keyWord) throws Exception;

    /**
     * 查询所有的管理员数量
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @return
     * @throws Exception
     */
    public Integer getAllAdminCount(String column,String keyWord) throws Exception;

    /**
     * 根据级别查询出用户的信息
     * @param level
     * @return
     * @throws Exception
     */
    public List<User> findAllByLevel(Integer level) throws Exception;
}

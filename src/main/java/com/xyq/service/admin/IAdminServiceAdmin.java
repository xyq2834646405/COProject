package com.xyq.service.admin;

import com.xyq.entity.User;

import java.util.Map;
import java.util.Set;

public interface IAdminServiceAdmin {
    /**
     * 表示在管理员增加前取出所有管理员的角色,执行如下操作:
     * <li>调用IRoleDao.findAllAdmin()</li>
     * @return 返回的结果以Map形式取得, 包含的数据如下:
     * <li>key = allRoles、value = IRoleDao.findAllAdmin()</li>
     * @throws Exception
     */
    public Map<String, Object> insertPre() throws Exception;

    /**
     * 管理员的增加操作,在本操作之中必须要注意以下的情况
     * <li>只有超级管理员可以增加新的管理员,那么必须根据用户的编号判断当前操作的用户是否为超级管理员,调用IUser.findById()确定</li>
     * <li>保证用户名称不能够重复,所以需要使用IUserDao.findById()判断用户ID是否已存在</li>
     * <li>如果用户不存在，则进行用户信息保存,但是这个级别只能是1</li>
     * @param vo     包含有新管理员信息对象
     * @param userid 当前操作的管理员编号
     * @return 增加成功返回true, 否则返回false
     * @throws Exception
     */
    public boolean insert(User vo, String userid) throws Exception;

    /**
     * 表示在管理员修改前取出所有相关信息,执行如下操作:
     * <li>调用IRoleDao.findAllAdmin()</li>
     * <li>调用IUserDao.findById()</li>
     * @param userid 管理员的编号
     * @return 返回的结果以Map形式取得, 包含的数据如下:
     * <li>key = allRoles、value = IRoleDao.findAllAdmin()</li>
     * <li>key = user、value = IUserDao.findById()</li>
     * @throws Exception
     */
    public Map<String, Object> updatePre(String userid) throws Exception;

    /**
     * 修改管理员信息,本功能执行的步骤如下:
     * <li>如果要修改的数据是超级管理员,那么不允许修改,调用IUserDao.findById()</li>
     * <li>如果不是超级管理员,是普通管理员,则执行修改操作,调用IUserDao.doUpdateInfo()</li>
     * @param vo 要修改的用户数据
     * @return 修改成功返回true, 否则返回false
     * @throws Exception
     */
    public boolean update(User vo) throws Exception;

    /**
     * 用户数据的批量删除操作,如果没有数据则返回false,如果有删除数据则调用IUserDao.doRemoveBatch()方法
     * @param ids 包含了所有要删除的用户编号
     * @return 删除成功返回true, 否则返回false
     * @throws Exception
     */
    public boolean delete(Set<String> ids) throws Exception;

    /**
     * 修改用户的角色信息,调用IUserDao.doUpdateRole()操作
     * @param vo 包含有用户编号以及角色编号
     * @return 修改成功返回true, 否则返回false
     * @throws Exception
     */
    public boolean updateRole(User vo) throws Exception;

    /**
     * 修改密码操作,调用IUserDao.doUpdatePassword()
     * @param vo 包含有用户编号以及新密码的信息
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean updatePassword(User vo) throws Exception;

    /**
     * 检查用户编号是否已经存在,调用IUserDao.findById()
     * @param userid 要检查的用户编号
     * @return 如果不存在表示可以使用返回true,否则返回false
     * @throws Exception
     */
    public boolean checkUser(String userid) throws Exception;

    /**
     * 进行数据的分页显示操作
     * @param currentPage 当前所在页
     * @param lineSize 每页显示的数据行
     * @param column 模糊查询列
     * @param keyWord 模糊查询字段
     * @return 返回的内容以Map的形式保存,包含以下数据:
     * <li>key = allUsers、value = IUserDao.findAllAdmin()</li>
     * <li>key = userCount、value = IUserDao.findAllAdminCount()</li>
     * <li>key = allRoles、value = IRoleDao.findAllAdmin()</li>
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;

    /**
     * 查询一个用户的完整信息,这个信息包含有管理员的权限、角色、权限组
     * @param id 要查询的管理员权限
     * @return 数据以Pojo的形式返回,否则返回null
     * @throws Exception
     */
    public User show(String id) throws Exception;
}

package com.xyq.service.admin;

import com.xyq.entity.Role;

import java.util.Map;

public interface IRoleServiceAdmin {
    /**
     * 角色增加前的数据查询准备,主要执行如下操作:
     * 1、因为需要权限组,所以使用IGroupsDao.findAll()查询
     * @return 返回数据以Map集合保存,包含以下内容:
     * 1、key = allGroups,value = IGroupsDao.findAll()
     * @throws Exception
     */
    public Map<String,Object> insertPre() throws Exception;

    /**
     * 角色修改前的数据查询准备,主要执行如下操作:
     * <li>1、因为需要权限组,所以使用IGroupsDao.findAll()查询</li>
     * <li>2、根据角色编号查询出已有的角色信息,使用IRoleDao.findById()查询</li>
     * <li>3、为了可以进行数据的回填操作,需要使用IRoleDAo.findRoleGroups()查询每个角色对应的权限组编号</li>
     * @return 返回数据以Map集合保存,包含以下内容:
     * <li>1、key = allGroups,value = IGroupsDao.findAll()</li>
     * <li>2、key = role,value = IRoleDao.findById()</li>
     * <li>3、key = gids,value = IRoleDao.findRoleGroups()</li>
     * @throws Exception
     */
    public Map<String,Object> updatePre(int rid) throws Exception;

    /**
     * 角色信息的列表显示,本功能要执行如下的操作:
     * 1、查询出所有的角色信息,调用IRoleDao.findAllSplit()
     * 2、统计出所有的角色信息数量,调用IRoleDao.getAllCount()
     * @param currentPage 当前所在页
     * @param lineSize 每页显示的行数
     * @param column 要模糊查询的数据列
     * @param keyWord 模糊查询关键字
     * @return 返回的结果以Map集合形式返回,包含如下的内容:
     * 1、key = allRoles、value = IRoleDao.findAllSplit()
     * 2、key = rolesCount、value = IRoleDao.getAllCount()
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;

    /**
     * 角色信息的修改操作,修改的时候调用如下的操作:
     * 1、要保证修改的角色名称和其他的名称不重复
     * 2、IRoleDao.doUpdate()方法自动维护关联的数据
     * @param vo 包含了要修改数据的Role对象,同时保存有所有的权限组信息
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean update(Role vo) throws Exception;

    /**
     * 角色数据的增加操作,增加时自动维护权限组关系,包含如下操作
     * 1、要保证修改的角色名称与其它名称不重复
     * 2、IRoleDao.doCreate()方法自动维护关系数据
     * @param vo 要增加的角色对象
     * @return 增加成功返回true,否则返回false
     * @throws Exception
     */
    public boolean insert(Role vo) throws Exception;

    /**
     * 检查指定的角色名称是否存在,调用IRoleDao.findByTitle()方法
     * @param title 要检查的角色名称
     * @return 如果角色名称存在返回false,表示不可使用,角色名称不存在返回true,表示可以使用
     * @throws Exception
     */
    public boolean checkTitle(String title) throws Exception;

    /**
     * 检查排除指定的角色之外的其他角色名称是否存在,调用IRoleDao.findByTitleAndNotId()方法
     * @param title 要检查的角色名称
     * @param rid 要排除的角色编号
     * @return 如果角色名称存在返回false,表示不可使用,角色名称不存在返回true,表示可以使用
     * @throws Exception
     */
    public boolean checkTitle(String title,int rid) throws Exception;
}

package com.xyq.service.admin;

import com.xyq.entity.Document;

import java.util.Map;
import java.util.Set;

public interface IDocumentServiceAdmin {
    /**
     * 数据增加前的查询操作,要执行如下功能
     * <li>调用IDocTypeDao.findAll()查询全部类型</li>
     * @return 数据以Map集合的方式返回,包含有如下信息:
     * <li>key = allDoctypes、value = IDocTypeDao.findAll()</li>
     * @throws Exception
     */
    public Map<String,Object> insertPre() throws Exception;

    /**
     * 文档数据的增加操作,调用IDocument.doCreate(),增加前需要处理好发布日期
     * @param vo
     * @return 增加成功返回true,否则返回false
     * @throws Exception
     */
    public boolean insert(Document vo) throws Exception;

    /**
     * 数据增加前的查询操作,要执行如下功能
     * @param did 文档的编号
     * <li>调用IDocTypeDao.findAll()查询全部类型</li>
     * <li>调用IDocumentDao.findById()根据编号查询出指定的文档内容</li>
     * @return 数据以Map集合的方式返回,包含有如下信息:
     * <li>key = allDoctypes、value = IDocTypeDao.findAll()</li>
     * <li>key = document、value = IDocumentDao.findById()</li>
     * @throws Exception
     */
    public Map<String,Object> updatePre(int did) throws Exception;

    /**
     * 文档的更新操作,调用IDocumentDao.doUpdate()方法
     * @param vo
     * @return 更新成功返回true,否则返回false
     * @throws Exception
     */
    public boolean update(Document vo) throws Exception;

    /**
     * 文档数据的删除操作
     * @param ids 要删除的ID集合
     * @return 删除成功返回true,否则返回false
     * @throws Exception
     */
    public boolean delete(Set<Integer> ids) throws Exception;

    /**
     * 分页查询出所有的文档数据,要执行如下的操作:
     * <li>为了可以显示出文档类型名字,那么执行IDoctypeDao.findAll()</li>
     * <li>分页查询文档数据,执行IDocumentDao.findAllSplit()</li>
     * <li>统计查询个数,执行IDocumentDao.getAllCount()</li>
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询的字段
     * @param keyWord 模糊查询的关键字
     * @return 返回的结果有如下内容:
     * <li>key = allDocuments、value = IDocumentDao.findAllSplit()</li>
     * <li>key = documentCount、value = IDocumentDao.getAllCount()</li>
     * <li>key = allDoctypes、value=IDoctypeDao.findAll()</li>
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;
}

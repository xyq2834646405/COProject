package com.xyq.dao;

import com.xyq.entity.Document;
import com.xyq.util.dao.IDao;

import java.util.List;
import java.util.Set;

public interface IDocumentDao extends IDao<Integer, Document> {
    /**
     * 根据用户的名称查询自己所发布的所有文档信息
     * @param userid 用户ID
     * @param currentPage 当前所在页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询的列
     * @param keyWord 模糊查询的关键字
     * @return 所有用户发送的文档信息,以分页的形式返回
     * @throws Exception
     */
    public List<Document> findAllSplitByUser(String userid,Integer currentPage,Integer lineSize,String column,String keyWord) throws Exception;

    /**
     * 根据用户的名称查询出用户自己发布的文档的数量
     * @param userid 用户的ID
     * @param column 模糊查询的列
     * @param keyWord 模糊查询的关键字
     * @return 用户自己发布的文档的数量
     * @throws Exception
     */
    public Integer getAllCountByUser(String userid,String column,String keyWord) throws Exception;

    /**
     * 删除指定用户的文档数据
     * @param userid 要删除文档的用户信息
     * @param ids 要删除的文档编号
     * @return
     * @throws Exception
     */
    public boolean doRemoveBatchByUser(String userid, Set<Integer> ids) throws Exception;
}

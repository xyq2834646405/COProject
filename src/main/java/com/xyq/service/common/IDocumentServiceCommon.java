package com.xyq.service.common;

import com.xyq.entity.Document;

public interface IDocumentServiceCommon {
    /**
     * 查询一个文档的完整信息
     * @param did
     * @return
     * @throws Exception
     */
    public Document show(int did) throws Exception;
}

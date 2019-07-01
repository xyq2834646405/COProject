package com.xyq.service.common;

import com.xyq.entity.Task;
import com.xyq.entity.User;

public interface ITaskServiceCommon {
    /**
     * 显示一个任务的详细信息,包括项目名称、任务的类型名称
     * @param id
     * @return
     * @throws Exception
     */
    public Task show(int id) throws Exception;
}

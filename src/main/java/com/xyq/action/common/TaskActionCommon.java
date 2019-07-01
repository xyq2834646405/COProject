package com.xyq.action.common;

import com.xyq.entity.Task;
import com.xyq.entity.User;
import com.xyq.service.common.ITaskServiceCommon;
import com.xyq.service.common.IUserServiceCommon;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@ParentPackage("root")
@Namespace("/pages/jsp/common/task")
@Action("TaskActionCommon")
@InterceptorRef("commonStack")
public class TaskActionCommon extends AbstractAction {
    private Task task = new Task();

    @Autowired
    private ITaskServiceCommon taskServiceCommon;

    public void show(){
        try {
            Task show = taskServiceCommon.show(task.getTid());
            JSONObject obj = new JSONObject();
            obj.put("tid",show.getTid());
            obj.put("title",show.getTitle());
            if(show.getUserByCreator()!=null)
                obj.put("creator",show.getUserByCreator().getUserid());
            if(show.getUserByReceiver()!=null)
                obj.put("receiver",show.getUserByReceiver().getUserid());
            if(show.getUserByCanceler()!=null)
                obj.put("cancel",show.getUserByCanceler().getUserid());
            if(show.getTasktype()!=null)
                obj.put("type",show.getTasktype().getTitle());
            if (show.getProject()!=null)
                obj.put("project",show.getProject().getTitle());
            obj.put("note",show.getNote());
            obj.put("rnote",show.getRnote());
            obj.put("cnote",show.getCnote());
            obj.put("pri",show.getPriority());
            obj.put("createdate",formatDate(show.getCreatedate()));
            obj.put("expiredate",formatDate(show.getExpiredate()));
            obj.put("lastupdatedate",formatDate(show.getLastupdatedate()));
            obj.put("es",show.getEstimate());
            obj.put("ep",show.getExpend());
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultColumn() {
        return null;
    }

    public String getColumnData() {
        return null;
    }

    public String getTypeName() {
        return null;
    }

    public Task getTask() {
        return task;
    }
}

package com.xyq.action.emp;

import com.xyq.entity.User;
import com.xyq.service.emp.IIndexServiceEmp;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("empStack")
@ParentPackage("root")
@Namespace("/")
@Action("IndexActionEmp")
public class IndexActionEmp extends AbstractAction {
    @Autowired
    private IIndexServiceEmp indexServiceEmp;

    public void allCount(){
        User emp = (User) getSession().getAttribute("emp");
        try {
            Map<String, Integer> map = indexServiceEmp.unreadCount(emp.getUserid());
            JSONObject obj = new JSONObject();
            obj.put("noticeCount",map.get("noticeCount"));
            obj.put("status0Count",map.get("status0Count"));
            obj.put("status1Count",map.get("status1Count"));
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultColumn() {
        return "";
    }

    public String getColumnData() {
        return "";
    }

    public String getTypeName() {
        return "公告信息";
    }
}

package com.xyq.action.admin;

import com.xyq.entity.Doctype;
import com.xyq.service.admin.IDoctypeServiceAdmin;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "doctype.list",location = "/pages/jsp/admin/document/admin_document_type.jsp"),
    @Result(name = "insertVF",location = "/pages/jsp/admin/document/admin_document_type.jsp"),
    @Result(name = "updateVF",location = "/pages/jsp/admin/document/admin_document_type.jsp")
})
@Namespace("/pages/jsp/admin/document")
@Action("DoctypeActionAdmin")
public class DoctypeActionAdmin extends AbstractAction {
    private static String insertRule = "doctype.title:string";
    private static String updateRule = "doctype.dtid:int|doctype.title:string";
    @Autowired
    private IDoctypeServiceAdmin doctypeServiceAdmin;
    private Doctype doctype = new Doctype();

    public String list(){
        try {
            getRequest().setAttribute("allDoctypes",doctypeServiceAdmin.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "doctype.list";
    }

    public void update(){
        try {
            print(doctypeServiceAdmin.update(doctype));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("flag",doctypeServiceAdmin.insert(doctype));
        } catch (Exception e) {
            e.printStackTrace();
        }
        obj.put("dtid",doctype.getDtid());
        print(obj);

    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "标题类型:title";
    }

    public String getTypeName() {
        return "文档类型";
    }

    public Doctype getDoctype() {
        return doctype;
    }
}

package com.xyq.action.admin;

import com.xyq.entity.Doctype;
import com.xyq.entity.Document;
import com.xyq.entity.User;
import com.xyq.service.admin.IDocumentServiceAdmin;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "document.list",location = "/pages/jsp/admin/document/admin_document_list.jsp"),
    @Result(name = "document.insert",location = "/pages/jsp/admin/document/admin_document_insert.jsp"),
    @Result(name = "document.update",location = "/pages/jsp/admin/document/admin_document_update.jsp"),
    @Result(name = "insertVF",location = "/pages/jsp/admin/document/admin_document_insert.jsp"),
    @Result(name = "updateVF",location = "/pages/jsp/admin/document/admin_document_update.jsp")
})
@Namespace("/pages/jsp/admin/document")
@Action("DocumentActionAdmin")
public class DocumentActionAdmin extends AbstractAction {
    private static String insertRule = "document.title:string|document.content:string";
    private static String updateRule = "document.did:int|document.title:string|document.content:string";
    private Document document = new Document();
    private Doctype doctype = new Doctype();
    private File file;//上传的附件
    private String fileContentType;//上传的类型
    private String ids;
    private String oldfilename;

    @Autowired
    private IDocumentServiceAdmin documentServiceAdmin;

    public String insertPre(){
        try {
            Map<String, Object> map = documentServiceAdmin.insertPre();
            getRequest().setAttribute("allDoctypes",map.get("allDoctypes"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "document.insert";
    }

    public String insert(){
        document.setDoctype(doctype);
        User admin = (User) getSession().getAttribute("admin");
        document.setUser(admin);
        if (file.length()>0){//有文件上传
            document.setFile(createSingleFileName(fileContentType));
        }
        try {
            if (documentServiceAdmin.insert(document)){
                String filePath = getApplication().getRealPath("/upload/document/")+document.getFile();
                if(file.length()>0){
                    saveSingleFile(filePath,file);
                }
                setMsgAndUrl("insert.success.msg","admin.document.insert.action");
            }else{
                setMsgAndUrl("insert.failure.msg","admin.document.insert.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String updatePre(){
        try {
            Map<String, Object> map = documentServiceAdmin.updatePre(document.getDid());
            getRequest().setAttribute("allDoctypes",map.get("allDoctypes"));
            getRequest().setAttribute("document",map.get("document"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "document.update";
    }

    public String update(){
        if (file.length()>0){//有文件上传
            document.setFile(createSingleFileName(fileContentType));
            String filePath = getApplication().getRealPath("/upload/document/")+oldfilename;
            deleteFile(filePath);
        }else{
            document.setFile(oldfilename);
        }
        document.setDoctype(doctype);
        try {
            if (documentServiceAdmin.update(document)){
                String filePath = getApplication().getRealPath("/upload/document/")+document.getFile();
                if(file.length()>0){
                    saveSingleFile(filePath,file);
                }
                setMsgAndUrl("update.success.msg","admin.document.list.action");
            }else{
                setMsgAndUrl("update.failure.msg","admin.document.list.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public void delete(){
        Set<Integer> did = new HashSet<Integer>();
        Set<String> files = new HashSet<String>();
        //删除数据的时候会牵扯到原始的附件信息删除,信息:ID:附件名称|
        if(ids!=null){
            String[] result = ids.split("\\|");
            for (int i = 0; i < result.length; i++) {
                String[] temp = result[i].split(":");
                did.add(Integer.parseInt(temp[0]));
                if(temp[1].length()>0){
                    files.add(temp[1]);
                }
            }
            try {
                if (documentServiceAdmin.delete(did)){
                    if(files.size() > 0){ //现在有要删除的附件
                        String filePath = getApplication().getRealPath("/upload/document/");
                        deleteFileBatch(filePath,files);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String list(){
        try {
            Map<String, Object> map = documentServiceAdmin.list(getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("documentCount"),"admin.document.split.url",null,null);
            getRequest().setAttribute("allDocuments",map.get("allDocuments"));
            List<Doctype> dtlist = (List<Doctype>) map.get("allDoctypes");
            Map<Integer,String> typeMap = new HashMap<Integer, String>();
            Iterator<Doctype> iter = dtlist.iterator();
            while(iter.hasNext()){
                Doctype dt = iter.next();
                typeMap.put(dt.getDtid(),dt.getTitle());
            }
            getRequest().setAttribute("allDoctypes",typeMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "document.list";
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "文档标题:title|文档内容:content|发布者:user.userid";
    }

    public String getTypeName() {
        return "文档";
    }

    public Document getDocument() {
        return document;
    }

    public Doctype getDoctype() {
        return doctype;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setOldfilename(String oldfilename) {
        this.oldfilename = oldfilename;
    }
}

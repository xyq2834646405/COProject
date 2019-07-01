package com.xyq.util.action;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;


public abstract class AbstractAction extends ActionSupport {
    private Integer cp = 1;
    private Integer ls = 5;
    private String col;
    private String kw;

    /**
     * 用来取得默认的分页显示列
     * @return
     */
    public abstract String getDefaultColumn();

    /**
     * 处理分页传递到组件的操作属性
     * @param allRecorders 当前所在的页面
     * @param urlKey 要进行分页处理的url,通过Pages.properties读取
     * @param paramName 参数名称
     * @param paramValue 参数内容
     */
    public void handleSplit(Object allRecorders,String urlKey,String paramName,String paramValue){
        getRequest().setAttribute("currentPage",getCp());
        getRequest().setAttribute("lineSize",getLs());
        getRequest().setAttribute("column",getCol());
        getRequest().setAttribute("keyWord",getKw());
        getRequest().setAttribute("url",getUrl(urlKey));
        getRequest().setAttribute("allRecorders",allRecorders);
        getRequest().setAttribute("columnData",getColumnData());
        getRequest().setAttribute("paramName",paramName);
        getRequest().setAttribute("paramValue",paramValue);
    }

    /**
     * 设置有可能进行的模糊查询字段
     * @return
     */
    public abstract String getColumnData();

    /**
     * 取得操作类型信息,主要用于更新的提示信息数据,例如:如果公告字符串,则返回公告字符串;如果是文档字符串,则返回文档字符串
     */
    public abstract String getTypeName();

    /**
     * 负责读取要通过forward.jsp页面进行跳转的路径,读取Pages.properties
     * @param key 路径的key的信息
     * @return 返回Pages.properties文件中指定的key的内容
     */
    public String getUrl(String key){
        return getText(key);
    }

    /**
     * 负责信息的读取操作
     * @param key Messages.properties里面定义的key信息
     * @return 返回Message.properties文件中的制定key对应的内容
     */
    public String getMsg(String key){
        return getText(key,new String[]{this.getTypeName()});
    }

    /**
     * 取得HttpServletRequest接口对象
     */
    public HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }

    /**
     * 取得HttpServletResponse接口对象
     */
    public HttpServletResponse getResponse(){
        return ServletActionContext.getResponse();
    }

    /**
     * 取得HttpSession接口对象
     */
    public HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 取得ServletContext接口
     */
    public ServletContext getApplication(){
        return ServletActionContext.getServletContext();
    }

    /**
     * 每一个Action的业务操作方法执行完成之后需要给forward.jsp页面的新
     * @param msgKey Messages.properties里面的key名称
     * @param urlKey Pages.properties里面的key名称
     */
    public void setMsgAndUrl(String msgKey,String urlKey){
        getRequest().setAttribute("msg",getMsg(msgKey));
        getRequest().setAttribute("url",getUrl(urlKey));
    }

    /**
     * 信息的输出操作
     * @param o 要输出的内容
     */
    public void print(Object o){
        try {
            getResponse().getWriter().print(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 信息以JSON格式的输出操作
     * @param o 要输出的信息
     */
    public void printJSON(Object o){
        try {
            JSONObject obj = new JSONObject();
            obj.put("data",o);
            getResponse().getWriter().print(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件的类型创建文件的名字
     * @param contentType
     * @return
     */
    public String createSingleFileName(String contentType) {
        String fileExt = null;
        if ("image/bmp".equalsIgnoreCase(contentType)) {
            fileExt = "bmp";
        } else if ("image/jpg".equalsIgnoreCase(contentType)) {
            fileExt = "jpg";
        } else if ("image/jpeg".equalsIgnoreCase(contentType)) {
            fileExt = "jpg";
        } else if ("image/gif".equalsIgnoreCase(contentType)) {
            fileExt = "gif";
        } else if ("image/png".equalsIgnoreCase(contentType)) {
            fileExt = "png";
        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equalsIgnoreCase(contentType)){
            fileExt = "docx";
        } else if ("text/plain".equalsIgnoreCase(contentType)) {
            fileExt = "txt";
        } else if ("application/msword".equalsIgnoreCase(contentType)) {
            fileExt = "doc";
        } else if ("application/vnd.ms-excel".equalsIgnoreCase(contentType)){
            fileExt = "xls";
        } else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(contentType)){
            fileExt = "xlsx";
        } else if ("application/x-rar-compressed".equalsIgnoreCase(contentType)){
            fileExt = "rar";
        } else if ("application/zip".equalsIgnoreCase(contentType)){
            fileExt = "zip";
        }
        return UUID.randomUUID().toString() + "." + fileExt;
    }

    /**
     * 实现文件的保存操作
     * @param filePath 文件的路径
     * @param file 要保存的文件信息
     * @return 保存成功返回true,否则返回false
     */
    public boolean saveSingleFile(String filePath,File file) {
        File saveFile = new File(filePath);
        if (!saveFile.getParentFile().exists()) { // 父路径不存在
            saveFile.getParentFile().mkdirs(); // 创建目录
        }
        boolean flag = false;
        OutputStream output = null;
        InputStream input = null;
        try {
            output = new FileOutputStream(saveFile);
            input = new FileInputStream(file);
            byte data[] = new byte[1024];
            int len = 0;
            while ((len = input.read(data)) != -1) {
                output.write(data, 0, len);
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public boolean deleteFile(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            return file.delete();
        }
        return false;
    }

    public void deleteFileBatch(String filePath, Set<String> fileNames){
        Iterator<String> iter = fileNames.iterator();
        while (iter.hasNext()){
            deleteFile(filePath+iter.next());
        }
    }

    public String formatDate(Date date){
        if (date!=null)
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return null;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public void setLs(Integer ls) {
        this.ls = ls;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public Integer getCp() {
        return cp;
    }

    public Integer getLs() {
        return ls;
    }

    public String getCol() {
        if (col == null || "".equals(col)){
            return getDefaultColumn();
        }
        return col;
    }

    public String getKw() {
            if (this.kw == null){
            return "";
        }
        return kw;
    }
}

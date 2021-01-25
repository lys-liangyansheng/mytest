package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityServie;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class ActivityController {
    @Autowired
    private ActivityServie activityServie;

    @Autowired
    private UserService userService;

    //跳转市场活动首页
    @RequestMapping("/workbench/activity/index.do")
    public String index(Model model){
        List<User> userList=userService.queryAllUsers();
        model.addAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject=new ReturnObject();
        int ret=activityServie.saveCreateActivity(activity);
        returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityForPageByCondition.do")
    public @ResponseBody Object queryActivityForPageByCondition(int pageNo,int pageSize,String name, String owner,String startDate,String endDate){
        Map<String,Object> map=new HashMap<>();
        map.put("beginNo",(pageNo-1)*pageSize); //(1-1)*2=0  limit 0,2  (2-1)*2=2 limit 2,2
        map.put("pageSize",pageSize);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        List<Activity> activityList=activityServie.queryActivityForPageByCondition(map);
        long totalRows=activityServie.queryCountOfActivityByCondition(map);

        //根据查询结果,生成相应的信息返回
        Map<String,Object> retMap=new HashMap<>();//Map封装两个参数
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap ;
    }


    @RequestMapping("/workbench/activity/editActivity.do")
    public @ResponseBody Object editActivity(String id){
        Activity activity=activityServie.queryActivityById(id);
        return  activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject=new ReturnObject();
        int ret=activityServie.saveEditActivityById(activity);
        if(ret>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] id){
        ReturnObject returnObject=new ReturnObject();
        int ret=activityServie.deleteActivityByIds(id);
        if(ret>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public  void exportAllActivity(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //1.将数据库中的市场活动信息取出来
        List<Activity> activityList=activityServie.queryAllActivityForDetail();
        //2.创建HSSFWorkbook,excel文件
        HSSFWorkbook wb=new HSSFWorkbook();
        //3.页
        HSSFSheet sheet=wb.createSheet("市场活动表");
        //4.行
        HSSFRow row=sheet.createRow(0);
        //5.列(表头)
        HSSFCell cell=row.createCell(0);
        //6.为第一行设置标题信息
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建者");
        cell=row.createCell(8);
        cell.setCellValue("创建时间");
        cell=row.createCell(9);
        cell.setCellValue("修改者");
        cell=row.createCell(10);
        cell.setCellValue("修改时间");

        //7.创建样式
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //8.填充表单数据
        if(activityList!=null){
            Activity activity=null;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);//获取每一条数据

                row=sheet.createRow(i+1);//创建一行

                cell=row.createCell(0);//column：列的编号,从0开始，0表示第一列，1表示第二列，....
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditBy());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditTime());
            }
        }

        //1.设置响应类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        //2.IE, XMLRequest ->不同的浏览器接收响应头采用的编码格式不同,IE uriendcode,firefox iso8859-1
        //在请求头中有一个User-Agent
        String browser=request.getHeader("User-Agent");
        //火狐ISO8859-1
        String fileName= URLEncoder.encode("市场活动列表","UTf-8");
        if(browser.contains("firefox")){
            fileName=new String("市场活动列表".getBytes("UTF-8"),"ISO8859-1");
        }
        response.addHeader("Content-Disposition","attachment;filename="+fileName+".xls");


        //获取输出流
        OutputStream os=response.getOutputStream();
        wb.write(os);
        os.flush();
        wb.close();
    }
   /*
   1.导包commons-fileupload
   2.配置springmvc上传的内容
   3.写上传表单,3点:file post multipar=form-data
   4.在控制层处理,先建立File,取文件名,MultipartFile.transferTo()
    */
    @RequestMapping("/workbench/activity/fileUpload.do")
    public @ResponseBody Object fileUpload(MultipartFile myFile,String username)throws Exception{
        ReturnObject returnObject=new ReturnObject();
        //将文件上传到d:/testDir,先获取文件名
        String originFileName=myFile.getOriginalFilename();
        //创建一个文件对象,第一个参数是文件保存的路径,第二个参数是文件名
        File file=new File("d:\\testDir",originFileName); //空
        //将上传文件写入新建的文件
        myFile.transferTo(file);
        returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        System.out.println("username="+username);
        return returnObject;
    }

    @RequestMapping("/workbench/activity/fileDownLoad.do")
    public void fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取服务器的磁盘上某一个文件(student.xls)，返回浏览器
        //1.设置响应类型
        response.setContentType("application/octet-stream;charset=UTF-8");

        //根据HTTP协议的规定，浏览器每次向服务器发送请求，都会把浏览器信息以请求头的形式发送到服务器
        String browser=request.getHeader("User-Agent");

        //不同的浏览器接收响应头采用的编码格式不一样：
        //IE采用 urlencoded
        ////火狐采用 ISO8859-1
        String fileName=URLEncoder.encode("学生列表","UTF-8");
        if(browser.contains("firefox")){
            //火狐采用 ISO8859-1
            fileName=new String("学生列表".getBytes("UTF-8"),"ISO8859-1");
        }

        //默认情况下，浏览器接收到响应信息之后，直接在显示窗口中打开；
        //可以设置响应头信息，使浏览器接收到响应信息之后，在下载窗口打开
        response.addHeader("Content-Disposition","attachment;filename="+fileName+".xls");

        //2.获取输出流
        OutputStream os=response.getOutputStream();

        //3.读取student.xls文件，通过os输出到浏览器
        InputStream is=new FileInputStream("d:\\testDir\\student.xls");

        byte[] buff=new byte[1024];
        int len=0;
        while((len=is.read(buff))!=-1){
            os.write(buff,0,len);
        }
        //4.关闭资源
        is.close();
        os.flush();
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,String username,HttpSession session) throws Exception{

            ReturnObject returnObject=new ReturnObject();
            User user=(User)session.getAttribute(Contants.SESSION_USER);
            //返回结果
            Map<String,Object> retMap=new HashMap<>();
            //解析excel文件,获取每一个Activity,将所有Activity封装在一个集中
            List<Activity> activityList=new ArrayList<>();
            //获取上传excel文件
            InputStream is=activityFile.getInputStream();
            //创建工作薄
            HSSFWorkbook wb=new HSSFWorkbook(is);
            //取第一页
            HSSFSheet sheet=wb.getSheetAt(0);
            //设置对象,行,列,活动
            HSSFRow row=null;
            HSSFCell cell=null;
            Activity activity=null;

            for(int i=1;i<=sheet.getLastRowNum();i++){
                row=sheet.getRow(i);
                activity=new Activity();
                //Activity数据来自两部分,一部分是动态的,不在excel中,程序员给.另一部分固定的,从excel对应的行中获取
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateBy(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));

                //将用户上传的excel的模板文件进行解析,将每一个cell中的数据放到activity
                for(int j=0;j<row.getLastCellNum();j++){
                    cell=row.getCell(j);
                    String cellValue=getCellValue(cell);
                    if(j==0){
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if(j==4){
                        activity.setDescription(cellValue);
                    }
                }
                activityList.add(activity);
            }

            //调用业务层
        int ret=activityServie.saveCreateActivityByList(activityList);
            //保存前端需要的结果
        retMap.put("code",Contants.RETURN_OBJECT_CODE_SUCCESS);
        retMap.put("count",ret);

        return retMap;
    }

    public static String getCellValue(HSSFCell cell) {
        String ret = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                ret = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                ret = cell.getNumericCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                ret = cell.getCellFormula();
                break;
            default:
                ret = "";
        }
        return ret;
    }
    }

/**
 * @项目名：crm-project
 * @创建人： Administrator
 * @创建时间： 2020-05-18
 * @公司： www.bjpowernode.com
 * @描述：TODO
 */
package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>NAME: DicTypeController</p>
 * @author Administrator
 * @date 2020-05-18 16:09:03
 * @version 1.0
 */
@Controller
public class DicTypeController {

    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("/settings/dictionary/type/index.do")
    public String index(Model model){
        //调用service层方法，查询所有的数据字典类型列表
        List<DicType> dicTypeList=dicTypeService.queryAllDicTypes();

        //把数据保存到request中
        model.addAttribute("dicTypeList",dicTypeList);

        return "settings/dictionary/type/index";
    }

    @RequestMapping("/settings/dictionary/type/toSave.do")
    public String toSave(){
        //请求转发
        return "settings/dictionary/type/save";
    }

    @RequestMapping("/settings/dictionary/type/checkCode.do")
    public @ResponseBody Object checkCode(String code){
        //调用service层方法，查询数据字典类型
        DicType dicType=dicTypeService.queryDicTypeByCode(code);
        //根据查询结果，生成响应信息
        ReturnObject returnObject=new ReturnObject();
        if(dicType==null){
            //不重复，code可用
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            //重复，code不可用
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("编码已存在");
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/saveCreateDicType.do")
    public @ResponseBody Object saveCreateDicType(DicType dicType){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，保存数据字典类型
            int ret = dicTypeService.saveCreateDicType(dicType);

            if(ret>0){
                //保存成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                //保存失败
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            //保存失败
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/deleteDicTypeByCodes.do")
    public @ResponseBody Object deleteDicTypeByCodes(String[] code){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，删除数据字典类型
            int ret = dicTypeService.deleteDicTypeByCodes(code);
            if(ret>0){
                //删除成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                //删除失败
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统正忙，请稍后重试...");
            }
        }catch (Exception e){
            e.printStackTrace();
            //删除失败
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统正忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/editDicType.do")
    public String editDicType(String code,Model model){
        //调用service层方法，查询数据字典类型
        DicType dicType=dicTypeService.queryDicTypeByCode(code);
        //把数据保存到reuquest中
        model.addAttribute("dicType",dicType);
        //请求转发
        return "settings/dictionary/type/edit";
    }

    @RequestMapping("/settings/dictionary/type/saveEditDicType.do")
    public @ResponseBody Object saveEditDicType(DicType dicType){
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，修改数据字典类型
            int ret = dicTypeService.saveEditDicType(dicType);
            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }
}

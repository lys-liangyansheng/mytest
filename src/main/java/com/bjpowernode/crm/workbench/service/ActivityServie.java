package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityServie {
    int saveCreateActivity(Activity activity); //保存
    List<Activity> queryActivityForPageByCondition(Map<String,Object> map);
    long queryCountOfActivityByCondition(Map<String,Object> map);
    Activity queryActivityById(String id);
    int saveEditActivityById(Activity activity);
    int deleteActivityByIds(String[] ids);

    List<Activity> queryAllActivityForDetail();


    public int saveCreateActivityByList(List<Activity> activityList) ;

    List<Activity> queryActivityForDetailByClueId(String cludeId);

    List<Activity> searchActivityNoBoundById(Map<String,Object> map);
}

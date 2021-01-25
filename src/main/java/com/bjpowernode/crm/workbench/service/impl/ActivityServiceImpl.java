package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityServie {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryActivityForPageByCondition(Map<String, Object> map) {
        return activityMapper.selectActivityForPageByCondition(map);
    }

    @Override
    public long queryCountOfActivityByCondition(Map<String, Object> map) {
        return 0;
    }

//    @Override
//    public long queryCountOfActivityByCondition(Map<String, Object> map) {
//        return activityMapper.selectCountofActivityByCondition(map);
//    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int saveEditActivityById(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }


    @Override
    public List<Activity> queryAllActivityForDetail() {
        return activityMapper.selectAllActivityForDetail();
    }

    @Override
    public int saveCreateActivityByList(List<Activity> activityList) {
        return activityMapper.insertActivityByList(activityList);
    }

    @Override
    public List<Activity> queryActivityForDetailByClueId(String cludeId) {
        return activityMapper.selectActivityForDetailByClueId(cludeId);
    }

    @Override
    public List<Activity> searchActivityNoBoundById(Map<String, Object> map) {
        List<Activity> aList=activityMapper.searchActivityNoBoundById(map);
        return aList;
    }


}

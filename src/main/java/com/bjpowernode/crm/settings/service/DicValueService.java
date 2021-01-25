package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicValueService {
    Map<String, List<DicValue>> getAll();

    List<DicValue> queryAllDicValues();

    int saveCreateDicValue(DicValue dicValue);

    int deleteDicValueByIds(String[] ids);

    DicValue queryDicValueById(String id);

    int saveEditDicValue(DicValue dicValue);

    List<DicValue> queryDicValueByTypeCode(String typeCode);
}

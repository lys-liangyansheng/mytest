package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeService {
    List<DicType> queryAllDicTypes();

    DicType queryDicTypeByCode(String code);

    int saveCreateDicType(DicType dicType);

    int deleteDicTypeByCodes(String[] codes);

    int saveEditDicType(DicType dicType);




}

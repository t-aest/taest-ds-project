package com.taestdsadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taestdsadmin.entity.ItemCategory;
import com.taestdsadmin.entity.ItemBasics;

import java.util.List;

public interface ItemBasicsService extends IService<ItemBasics> {
    List<ItemBasics> selectItemBasicByName(String itemName);

    List<ItemCategory> getCategoryTree();
}

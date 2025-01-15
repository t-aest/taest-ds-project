package com.taestdsadmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taestdsadmin.dao.CategoryMapper;
import com.taestdsadmin.dao.ItemBasicsMapper;
import com.taestdsadmin.entity.ItemCategory;
import com.taestdsadmin.entity.ItemBasics;
import com.taestdsadmin.service.ItemBasicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemBasicsServiceImpl extends ServiceImpl<ItemBasicsMapper, ItemBasics> implements ItemBasicsService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<ItemBasics> selectItemBasicByName(String itemName) {
        LambdaQueryWrapper<ItemBasics> queryWrapper = new LambdaQueryWrapper<ItemBasics>().like(ItemBasics::getItemName, itemName);
        List<ItemBasics> itemBasics = this.baseMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(itemBasics)) {
            return itemBasics;
        }
        return null;
    }

    @Override
    public List<ItemCategory> getCategoryTree() {
        // 查询所有根类别
        LambdaQueryWrapper<ItemCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(ItemCategory::getParentId);
        List<ItemCategory> rootCategories = categoryMapper.selectList(queryWrapper);
        // 递归查询子类别
        for (ItemCategory rootCategory : rootCategories) {
            rootCategory.setChildren(findChildren(rootCategory.getCategoryId()));
        }
        return rootCategories;
    }


    private List<ItemCategory> findChildren(Long parentId) {
        // 查询当前父类别的子类别
        LambdaQueryWrapper<ItemCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemCategory::getParentId, parentId);
        List<ItemCategory> children = categoryMapper.selectList(queryWrapper);
        // 递归查询子类别的子类别
        for (ItemCategory child : children) {
            child.setChildren(findChildren(child.getCategoryId()));
        }
        return children;
    }
}

package com.taestdsadmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@Data
@TableName("`item_basics`")
public class ItemBasics {

    /**
     * id
     */
    private int itemId;

    /**
     * 名称
     */
    private String itemName;

    /**
     * 描述
     */
    private String itemDesc;

    /**
     * 作弊代码
     */
    private String cheatCode;

    /**
     * 物品类型  动植物、采集物、合成物
     */
    private Integer itemType;

    /**
     * 掉落物
     */
    private String itemDrops;

    /**
     * 获取方法
     */
    private String getMethod;

    /**
     * 属性
     */
    private String itemProperty;

    /**
     * 合成材料
     */
    private String craftingMaterials;

    /**
     * 科技需求
     *
     */
    private String technologyRequirements;

    /**
     * 图片实例
     *
     */
    private String imgUrl;
}

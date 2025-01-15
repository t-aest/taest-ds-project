package com.taestdsadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("`item_category`")
public class ItemCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long categoryId; 
    private String label;
    private String value;

    private Long parentId;

    @TableField(exist = false)
    private List<ItemCategory> children;
}

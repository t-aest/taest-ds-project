package com.taestdsadmin.controller;


import com.taestdsadmin.entity.ItemCategory;
import com.taestdsadmin.entity.ItemBasics;
import com.taestdsadmin.service.ItemBasicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemBasicsService itemBasicsService;


    @GetMapping("categoryList")
    public ResponseEntity<Object> categoryList() {
        List<ItemCategory> categoryTree = itemBasicsService.getCategoryTree();
        return ResponseEntity.ok().body(categoryTree);
    }


    @GetMapping("{itemName}")
    public ResponseEntity<Object> itemDesc(@PathVariable String itemName) {
        List<ItemBasics> result =  itemBasicsService.selectItemBasicByName(itemName);
        return ResponseEntity.ok().body(result.get(0));
    }
}

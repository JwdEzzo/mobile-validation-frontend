package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin("*")
public class ItemController {

   @Autowired
   private ItemService itemService;

   // POST - Create Item
   @PostMapping()
   public Item createItem(@RequestBody Item item) {
      return itemService.createItem(item);
   }

   // GET - Get all items
   @GetMapping()
   public List<Item> getAllItems() {
      return itemService.getAllItems();
   }

   // GET - Get item by id
   @GetMapping("/{id}")
   public Item getItemById(@PathVariable Long id) {
      return itemService.getItemById(id);
   }

   // PUT - Update item
   @PutMapping("/update/{id}")
   public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
      return itemService.updateItem(id, item);
   }

   // DELETE - Delete item by id
   @DeleteMapping("/delete/{id}")
   public void deleteItemById(@PathVariable Long id) {
      itemService.deleteItemById(id);
   }

}

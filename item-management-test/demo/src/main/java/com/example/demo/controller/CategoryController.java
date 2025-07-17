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

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {

   @Autowired
   private CategoryService categoryService;

   // POST -- Create Category
   @PostMapping()
   public Category createCategory(@RequestBody Category category) {
      return categoryService.createCategory(category);
   }

   // GET -- Read ALL Categories
   @GetMapping()
   public List<Category> getAllCategories() {
      return categoryService.getAllCategories();
   }

   // GET -- Read Category by ID
   @GetMapping("/{id}")
   public Category getCategoryById(@PathVariable Long id) {
      return categoryService.getCategoryById(id);
   }

   // PUT -- Update Category
   @PutMapping("/update/{id}")
   public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
      return categoryService.updateCategory(id, category);
   }

   // DELETE -- Delete Category by ID
   @DeleteMapping("/delete/{id}")
   public void deleteCategoryById(@PathVariable Long id) {
      categoryService.deleteCategory(id);
   }

}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {

   @Autowired
   private CategoryRepository categoryRepository;

   // CRUD - Create a Category
   public Category createCategory(Category category) {
      return categoryRepository.save(category);
   }

   // CRUD - Read a Category
   public Category getCategoryById(Long id) {
      return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
   }

   // CRUD - Read all Categories
   public List<Category> getAllCategories() {
      return categoryRepository.findAll();
   }

   // CRUD - Update a Category
   public Category updateCategory(Long id, Category newCategory) {

      Category categoryToBeUpdated = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

      categoryToBeUpdated.setName(newCategory.getName());

      return categoryRepository.save(categoryToBeUpdated);

   }

   // CRUD - Delete a Category
   public void deleteCategory(Long id) {
      categoryRepository.deleteById(id);
   }

}

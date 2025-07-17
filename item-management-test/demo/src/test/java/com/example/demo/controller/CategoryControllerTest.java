package com.example.demo.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

// Enable Mockito annotations for this test class
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

   // Create a mock version of CategoryService (not the real implementation)
   @Mock
   private CategoryService categoryService;

   // Create a real CategoryController instance and inject the mock service
   @InjectMocks
   private CategoryController categoryController;

   // Test for successful category creation
   @Test
   public void createCategory_ShouldReturnCreatedCategory() {
      // Create test input data
      Category testCategory = new Category();
      testCategory.setName("Electronics");

      // Create expected output data
      Category savedCategory = new Category(1L, "Electronics", null);

      // Program the mock: "When createCategory is called with inputCategory, return savedCategory"
      when(categoryService.createCategory(testCategory)).thenReturn(savedCategory);

      // Call the actual controller method we're testing
      Category result = categoryController.createCategory(testCategory);

      // Verify the returned object matches our expected result
      assertEquals(savedCategory, result);
      // Verify the mock service was called exactly once with our input
      verify(categoryService).createCategory(testCategory);
   }

   @Test
   public void getAllCategories_ShouldReturnAllCategories() {
      Category testCategory1 = new Category(1L, "Electronics", null);
      Category testCategory2 = new Category(2L, "Appliances", null);
      List<Category> categories = List.of(testCategory1, testCategory2);

      when(categoryService.getAllCategories()).thenReturn(categories);
      List<Category> result = categoryController.getAllCategories();

      assertNotNull(result, "The result should not be null");
      assertEquals(2, result.size(), "The result size should be 2");
      assertEquals(categories, result);

      verify(categoryService).getAllCategories();
   }

   @Test
   public void getCategoryById_ShouldReturnCategoryWhenExists() {
      Category testCategory = new Category(1L, "Electronics", null);
      when(categoryService.getCategoryById(1L)).thenReturn(testCategory);

      Category expectedResult = categoryController.getCategoryById(1L);

      assertEquals(expectedResult, testCategory);
      verify(categoryService).getCategoryById(1L);
   }

   @Test
   public void getCategoryById_ShouldThrowExceptionWhenDoesntExist() {
      Long nonExistentId = 472389L;

      // when getCategoryById is called with nonExistentId
      when(categoryService.getCategoryById(nonExistentId))
            .thenThrow(new RuntimeException("Category not found for ID: " + nonExistentId));

      // Assert that calling categoryController.getCategoryById with nonExistentId
      // throws RuntimeException
      assertThrows(RuntimeException.class, () -> categoryController.getCategoryById(nonExistentId));

      // THIS THROWS AN EXCEPTION
      // System.out.println(categoryController.getCategoryById(nonExistentId)); 

      verify(categoryService).getCategoryById(nonExistentId);
   }

   // Test for updating a category
   @Test
   public void updateCategory_ShouldReturnUpdatedCategory() {
      // Actual
      Category testCategory = new Category(1L, "Electronics", null);
      // Expected
      Category updatedCategory = new Category(1L, "Updated Electronics", null);

      // Program mock behavior
      when(categoryService.updateCategory(1L, testCategory)).thenReturn(updatedCategory);

      // Call controller method
      Category result = categoryController.updateCategory(1L, testCategory);

      assertEquals(updatedCategory, result);
      verify(categoryService).updateCategory(1L, testCategory);
   }

   // Test for deleting a category
   @Test
   public void deleteCategory_ShouldCallServiceDelete() {
      // Setup test ID
      Long id = 1L;

      // Call controller delete method
      categoryController.deleteCategoryById(id);

      // Verify service delete was called with correct ID
      verify(categoryService).deleteCategory(id);
   }

}

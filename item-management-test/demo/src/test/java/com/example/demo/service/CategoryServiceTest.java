package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

   @Mock
   private CategoryRepository categoryRepository;

   @InjectMocks
   private CategoryService categoryService;

   @Test
   public void createCategory_ShouldReturnSavedCategory() {

      // Instantiated Data for testing (what we're passing to the service)
      Category testCategory = new Category();
      testCategory.setName("Electronics");

      // Expected category (what repository returns with ID set)
      Category savedCategory = new Category();
      savedCategory.setId(1L);
      savedCategory.setName("Electronics");

      // Mocking Repositroy Behavior
      when(categoryRepository.save(testCategory)).thenReturn(savedCategory);

      // Call service method with inputCategory
      Category result = categoryService.createCategory(testCategory);

      // Verify the result matches what repository returned
      assertEquals(savedCategory, result);
      assertEquals(1L, result.getId());
      assertEquals("Electronics", result.getName());

      // Verify repository was called with the correct argument
      verify(categoryRepository).save(testCategory);
   }

   // Test for method getCategoryById if the Category Exists
   @Test
   public void getCategoryById_ShouldReturnCategoryWhenItExists() {

      // create a test Category object with DI=1 and name=Electromnics
      Category testCategory = new Category();
      testCategory.setId(1L);
      testCategory.setName("Electronics");

      // Uses Mockito to mock the categoryRepository's behavior.
      // When findById(1L) is called, it will return an Optional containing the test category.
      when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

      // Calls the actual service method being tested with ID=1.
      Category result = categoryService.getCategoryById(1L);

      // Verifies that the returned category is the same as our test category.
      assertEquals(testCategory, result);

      // Uses Mockito to verify that findById(1L) was called exactly once on the repository.
      verify(categoryRepository).findById(1L);

   }

   // GET ALL CATEGORIES
   @Test
   public void getAllCategories_ShouldReturnAllCategories() {

      // create a test Category object with DI=1 and name=Electromnics
      Category testCategory1 = new Category();
      testCategory1.setId(1L);
      testCategory1.setName("Electronics1");
      // create a test Category object with DI=1 and name=Electromnics
      Category testCategory2 = new Category();
      testCategory2.setId(2L);
      testCategory2.setName("Electronics2");

      Category[] categories = new Category[] { testCategory1, testCategory2 };

      // When findAll is called, it will return an Array containing the test categories.
      when(categoryRepository.findAll()).thenReturn(Arrays.asList(categories));

      List<Category> result = categoryService.getAllCategories();

      assertIterableEquals(Arrays.asList(categories), result);

      verify(categoryRepository).findAll();
   }

   // GET CATEGORY BY ID WHEN IT DOESNT EXIST
   @Test
   public void getCategoryById_ShouldThrowExceptionWhenNotFound() {

      // Setup test ID
      Long id = 1L;

      // Program mock to return empty optional
      when(categoryRepository.findById(id)).thenReturn(Optional.empty());

      // Verify exception is thrown
      Throwable thrown = assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(id));
      // Verify repository was queried
      verify(categoryRepository).findById(id);
   }

   @Test
   public void updateCategory_ShouldUpdateAndReturnCategory() {

      // Create an initial test category with ID 1 and name "Electronics"
      Category testCategory = new Category();
      testCategory.setId(1L);
      testCategory.setName("Electronics");
      System.out.println("Created category: " + testCategory.toString());

      // Create an updated category object with the same ID but a new name "Appliance"
      Category updatedCategory = new Category();
      updatedCategory.setId(1L);
      updatedCategory.setName("Appliance");
      System.out.println("Updated category: " + updatedCategory.toString());

      when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory)); // Mock the behavior of the categoryRepository to return the test category when findById is called with ID 1

      when(categoryRepository.save(testCategory)).thenReturn(testCategory); // Mock the behavior of the categoryRepository to return the test category when save is called with the test category

      Category result = categoryService.updateCategory(1L, updatedCategory); // Call the updateCategory method on the categoryService with ID 1 and the updated category

      System.out.println("Result: " + result.toString());
      assertEquals("Appliance", result.getName(), "The category name should be updated"); // Assert that the name of the result category is "Appliance", indicating that the update was successful

      verify(categoryRepository).findById(1L); // Verify that findById was called on the categoryRepository with ID 1
      verify(categoryRepository).save(testCategory); // Verify that save was called on the categoryRepository with the test category

      System.out.println("Update has been done since testCategory has become " + testCategory.toString());
   }

   // Test deleting a category
   @Test
   public void deleteCategory_ShouldCallRepositoryDelete() {

      Long id = 1L;

      categoryService.deleteCategory(id);

      verify(categoryRepository).deleteById(id);
   }

}

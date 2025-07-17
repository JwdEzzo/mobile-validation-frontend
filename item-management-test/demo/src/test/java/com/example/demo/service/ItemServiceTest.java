package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.MobileValidationResponse;
import com.example.demo.model.Category;
import com.example.demo.model.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

   @Mock
   private CategoryRepository categoryRepository;

   @Mock
   private ItemRepository itemRepository;

   @Mock
   private RestTemplate restTemplate; // Add this line

   @Mock
   private MobileValidationResponse mobileValidationResponse;

   @InjectMocks
   private ItemService itemService;

   @Test
   public void createItem_ShouldReturnSavedItem() {

      Category testCategory = new Category();
      testCategory.setId(1L);
      testCategory.setName("Electronics");

      Item testItem = new Item();
      testItem.setId(1L);
      testItem.setName("PS4");
      testItem.setDescription("Gaming Quality");
      testItem.setMobileNumber("+96178914563");
      testItem.setCategory(testCategory);

      System.out.println("The Test Item is : " + testItem);

      MobileValidationResponse validationResponse = new MobileValidationResponse();
      validationResponse.setValid(true);

      // Use lenient() for potentially unused stubs
      lenient().when(restTemplate.getForObject(anyString(), eq(MobileValidationResponse.class)))
            .thenReturn(validationResponse);
      lenient().when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
      when(itemRepository.save(testItem)).thenReturn(testItem);

      Item result = itemService.createItem(testItem);
      assertEquals(testItem, result);

      verify(itemRepository).save(testItem);
      System.out.println("The Result Item is : " + result);

   }

   @Test
   public void getItemById_ShouldReturnItem() {

      Category testCategory = new Category();
      testCategory.setId(1L);
      testCategory.setName("Electronics");

      Item testItem = new Item();
      testItem.setId(1L);
      testItem.setName("PS5");
      testItem.setDescription("Gaming Quality");
      testItem.setMobileNumber("+000000000");
      testItem.setCategory(testCategory);

      System.out.println("The Test Item is : " + testItem);

      MobileValidationResponse validationResponse = new MobileValidationResponse();
      validationResponse.setValid(true);

      // Use lenient() for potentially unused stubs
      lenient().when(restTemplate.getForObject(anyString(), eq(MobileValidationResponse.class)))
            .thenReturn(validationResponse);
      when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));

      Item result = itemService.getItemById(1L);
      assertEquals(testItem, result);

      verify(itemRepository).findById(1L);
      System.out.println("The Result Item is : " + result);

   }

   @Test
   void getAllItems_ShouldReturnAllItems() {
      // Create a category instance, we'll use it as the category for both items
      Category category = new Category(1L, "Electronics", null);
      // Create two items, both with the same category
      Item item1 = new Item(1L, "Laptop", "High-end gaming laptop", "+96178914563", category);
      Item item2 = new Item(2L, "Phone", "Smartphone", "+96170953324", category);

      // Program mock to return test data
      // When the itemRepository's findAll() method is called, return a list containing item1 and item2
      when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

      // Call the getAllItems() method on the itemService, and store the result in the variable "result"
      List<Item> result = itemService.getAllItems();
      // Verify we got 2 items
      // Assert that the size of the result list is 2
      assertEquals(2, result.size());
      // Verify repository was called
      // Verify that the findAll() method was called on the itemRepository
      verify(itemRepository).findAll();
   }

   @Test
   public void getItemById_ShouldThrowExceptionWhenMobileNumberMissing() {
      Category testCategory = new Category(1L, "Electronics", null);
      Item testItem = new Item(1L, "Laptop", "High-end gaming laptop", null, testCategory);

      // Verify exception is thrown
      assertThrows(RuntimeException.class, () -> itemService.createItem(testItem));
      System.out.println(testItem.toString());
      // Verify no save was attempted
      verify(itemRepository, never()).save(any());
   }

   @Test
   void updateItem_ShouldUpdateAndReturnItem() {

      Category existingCategory = new Category(1L, "Electronics", null);
      Item existingItem = new Item(1L, "Laptop", "Old description", "+96178914563", existingCategory);

      // Create updated category
      Category updatedCategory = new Category(2L, "Better Electronics", null);
      Item updateItem = new Item(1L, "Updated Laptop", "New description", "+96170953324", updatedCategory);

      when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
      when(categoryRepository.findById(2L)).thenReturn(Optional.of(updatedCategory));
      when(itemRepository.save(existingItem)).thenReturn(existingItem);

      // Mock mobile validation
      MobileValidationResponse validationResponse = new MobileValidationResponse();
      validationResponse.setValid(true);
      when(restTemplate.getForObject(anyString(), eq(MobileValidationResponse.class)))
            .thenReturn(validationResponse);

      Item result = itemService.updateItem(1L, updateItem);
      System.out.println(result.toString()); // Debug

      assertEquals("Updated Laptop", result.getName());
      assertEquals("New description", result.getDescription());
      assertEquals("Better Electronics", result.getCategory().getName());
      verify(itemRepository).findById(1L);
      verify(categoryRepository).findById(2L);
      verify(itemRepository).save(existingItem);
   }

   // Test deleting an item
   @Test
   void deleteItem_ShouldCallRepositoryDelete() {
      // Setup test ID
      Long id = 1L;

      // Call service delete method
      itemService.deleteItemById(id);

      // Verify repository delete was called with correct ID
      verify(itemRepository).deleteById(id);
   }
}

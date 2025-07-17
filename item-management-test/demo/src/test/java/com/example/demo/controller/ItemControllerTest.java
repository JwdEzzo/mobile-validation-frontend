package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Category;
import com.example.demo.model.Item;
import com.example.demo.service.ItemService;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

   // Mock the service layer
   @Mock
   private ItemService itemService;

   // Inject mocks into real controller
   @InjectMocks
   private ItemController itemController;

   // Test item creation
   @Test
   void createItem_ShouldReturnCreatedItem() {
      // Setup test data
      Category category = new Category(1L, "Electronics", null);
      Item inputItem = new Item();
      inputItem.setName("Laptop");
      inputItem.setDescription("High-end gaming laptop");
      inputItem.setMobileNumber("+1234567890");
      inputItem.setCategory(category);

      // Expected saved item
      Item savedItem = new Item(1L, "Laptop", "High-end gaming laptop", "+1234567890", category);

      // Program mock behavior
      when(itemService.createItem(inputItem)).thenReturn(savedItem);

      // Call controller method
      Item result = itemController.createItem(inputItem);

      // Verify result
      assertEquals(savedItem, result);
      // Verify service interaction
      verify(itemService).createItem(inputItem);
   }

   // Test getting all items
   @Test
   void getAllItems_ShouldReturnAllItems() {
      // Setup test data
      Category category = new Category(1L, "Electronics", null);
      Item item1 = new Item(1L, "Laptop", "High-end gaming laptop", "+1234567890", category);
      Item item2 = new Item(2L, "Phone", "Smartphone", "+9876543210", category);

      // Program mock to return test data
      when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

      // Call controller method
      List<Item> result = itemController.getAllItems();

      // Verify we got 2 items
      assertEquals(2, result.size());
      // Verify service was called
      verify(itemService).getAllItems();
   }

   // Test getting single item
   @Test
   void getItemById_ShouldReturnItemWhenExists() {
      // Setup test data
      Long id = 1L;
      Category category = new Category(1L, "Electronics", null);
      Item item = new Item(id, "Laptop", "High-end gaming laptop", "+1234567890", category);

      // Program mock behavior
      when(itemService.getItemById(id)).thenReturn(item);

      // Call controller method
      Item result = itemController.getItemById(id);

      // Verify correct item returned
      assertEquals(item, result);
      // Verify service called with correct ID
      verify(itemService).getItemById(id);
   }

   // Test updating an item
   @Test
   void updateItem_ShouldReturnUpdatedItem() {
      // Setup test data
      Long id = 1L;
      Category category = new Category(1L, "Electronics", null);
      Item updateData = new Item();
      updateData.setName("Updated Laptop");
      updateData.setDescription("New description");
      updateData.setMobileNumber("+96178914563");
      updateData.setCategory(category);

      // Expected return value
      Item updatedItem = new Item(id, "Updated Laptop", "New description", "+1234567890", category);

      // Program mock behavior
      when(itemService.updateItem(id, updateData)).thenReturn(updatedItem);

      // Call controller method
      Item result = itemController.updateItem(id, updateData);

      // Verify update was successful
      assertEquals(updatedItem, result);
      // Verify service was called correctly
      verify(itemService).updateItem(id, updateData);
   }

   // Test deleting an item
   @Test
   void deleteItem_ShouldCallServiceDelete() {
      // Setup test ID
      Long id = 1L;

      // Call controller delete method
      itemController.deleteItemById(id);

      // Verify service delete was called with correct ID
      verify(itemService).deleteItemById(id);
   }
}
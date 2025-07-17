package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.MobileValidationResponse;
import com.example.demo.model.Category;
import com.example.demo.model.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Service
public class ItemService {

   @Autowired
   private ItemRepository itemRepository;

   @Autowired
   private CategoryRepository categoryRepository;

   @Autowired
   private RestTemplate restTemplate;

   // CRUD - Create Item
   public Item createItem(Item item) {
      if (item.getMobileNumber() == null || item.getMobileNumber().isEmpty()) {
         throw new RuntimeException("Mobile number is required");
      }
      validateMobileNumber(item.getMobileNumber());

      return itemRepository.save(item);
   }

   // CRUD - Get items
   public List<Item> getAllItems() {
      return itemRepository.findAll();
   }

   // CRUD - Get item by id
   public Item getItemById(Long id) {
      return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
   }

   // CRUD - Delete Item by Id
   public void deleteItemById(Long id) {
      itemRepository.deleteById(id);
   }

   // CRUD - Update Item
   public Item updateItem(Long id, Item newDetails) {
      Item itemToBeUpdated = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));

      if (newDetails.getMobileNumber() == null || newDetails.getMobileNumber().trim().isEmpty()) {
         throw new RuntimeException("Mobile number is required");
      }

      validateMobileNumber(newDetails.getMobileNumber());

      // Category is now mandatory, so we don't need null checks
      Category category = categoryRepository.findById(newDetails.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

      itemToBeUpdated.setName(newDetails.getName());
      itemToBeUpdated.setDescription(newDetails.getDescription());
      itemToBeUpdated.setMobileNumber(newDetails.getMobileNumber());
      itemToBeUpdated.setCategory(category); // Category must be provided

      return itemRepository.save(itemToBeUpdated);
   }

   public void deleteItem(Long id) {
      itemRepository.deleteById(id);
   }

   // using the microservice kirmel na3mel verify
   public void validateMobileNumber(String mobileNumber) {
      String trimmedNumber = mobileNumber.trim();
      if (!trimmedNumber.matches("^\\+\\d{10,15}$")) {
         throw new RuntimeException(
               "Mobile number must start with + , then followed by 10-15 digits with no spaces between them");
      }
      String url = "http://localhost:8081/api/mobile/validate/" + trimmedNumber;

      // More Basic
      MobileValidationResponse response = restTemplate.getForObject(url, MobileValidationResponse.class);

      if (response.isValid() == false) {
         throw new RuntimeException("Invalid mobile number: " + response.getMessage());
      }
   }
}

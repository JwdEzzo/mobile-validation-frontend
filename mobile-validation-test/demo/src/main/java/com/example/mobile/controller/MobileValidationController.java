package com.example.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.dto.MobileValidationResponse;
import com.example.mobile.service.MobileValidationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mobile")
public class MobileValidationController {

   @Autowired
   private MobileValidationService mobileValidationService;

   @GetMapping("/validate/{mobileNumber}")
   public ResponseEntity<MobileValidationResponse> numVerify(@PathVariable String mobileNumber) {
      String trimmedNumber = mobileNumber.trim(); // 7attayta kirmel balke l user 7at space

      if (!trimmedNumber.matches("^\\+\\d{10,15}$")) {
         MobileValidationResponse error = new MobileValidationResponse();
         error.setValid(false);
         error.setMessage(
               "Mobile number must start with + followed by 10-15 digits with no spaces. (for example: +9876543210).");
         return ResponseEntity.badRequest().body(error);
      }

      // Proceed with service call if format is valid
      MobileValidationResponse response = mobileValidationService.validateMobile(trimmedNumber);
      return response.isValid()
            ? ResponseEntity.ok(response)
            : ResponseEntity.badRequest().body(response);
   }
}
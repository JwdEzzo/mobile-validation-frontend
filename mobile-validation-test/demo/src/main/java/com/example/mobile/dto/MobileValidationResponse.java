package com.example.mobile.dto;

import lombok.Data;

@Data
public class MobileValidationResponse {

   private boolean valid;
   private String countryCode;
   private String countryName;
   private String operatorName;
   private String message;

}

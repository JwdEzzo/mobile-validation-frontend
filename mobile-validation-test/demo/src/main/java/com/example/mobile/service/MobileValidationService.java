package com.example.mobile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.mobile.dto.MobileValidationResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MobileValidationService {

   @Value("${numverify.api.key:8f3cbe2f013d650cb01de9c41d0b9dcb}")
   private String apiKey;

   private final String NUMVERIFY_URL = "http://apilayer.net/api/validate"; // BASE URL ta3ool numverify, mnista3mlo kirmel na3mel validate la numbers

   public MobileValidationResponse validateMobile(String mobileNumber) {
      MobileValidationResponse response = new MobileValidationResponse(); // Initialize a response kirmel na3milla return at the end 

      try {

         RestTemplate restTemplate = new RestTemplate(); // HTTP Client used for making web requests to RESTful services
         String url = NUMVERIFY_URL + "?access_key=" + apiKey + "&number=" + mobileNumber; // To this URL, GET request will be sent
         String JsonResponse = restTemplate.getForObject(url, String.class); // Hay 3imlit HTTP GET Request , and it returned a JSON response, this response 7a na3milla deserialize into JavaObject using ObjectMapper

         ObjectMapper mapper = new ObjectMapper(); //Initialise ObjectMapper
         JsonNode root = mapper.readTree(JsonResponse); // JSON to Java Object, using readTree method of ObjectMapper

         if (root.get("valid").asBoolean() == true) {
            response.setValid(true);
            response.setCountryCode(root.get("country_code").asText());
            response.setCountryName(root.get("country_name").asText());
            response.setOperatorName(root.get("carrier") != null ? root.get("carrier").asText() : "Unknown");
            response.setMessage("Valid Number");
         } else {
            response.setValid(false);
            response.setMessage("Invalid Number");
         }

      } catch (Exception e) {
         response.setValid(false);
         response.setMessage("An unexpected error occurred: " + e.getMessage());
         System.err.println("Unexpected Exception: " + e.getMessage()); // hay ile
      }
      return response;
   }

}

// @Service
// public class MobileValidationService {

//     @Value("${numverify.api.key:8f3cbe2f013d650cb01de9c41d0b9dcb}")
//     private String apiKey;

//     private final String NUMVERIFY_URL = "http://apilayer.net/api/validate";

//     public MobileValidationResponse validateMobile(String mobileNumber) {
//         MobileValidationResponse response = new MobileValidationResponse();

//         try {
//             RestTemplate restTemplate = new RestTemplate();
//             String url = NUMVERIFY_URL + "?access_key=" + apiKey + "&number=" + mobileNumber;
//             String jsonResponse = restTemplate.getForObject(url, String.class);

//             ObjectMapper mapper = new ObjectMapper();
//             JsonNode root = mapper.readTree(jsonResponse);

//             // --- IMPORTANT CHANGE STARTS HERE ---
//             // First, check if the response contains an "error" node
//             if (root.has("error")) {
//                 JsonNode errorNode = root.get("error");
//                 String errorCode = errorNode.has("code") ? errorNode.get("code").asText() : "N/A";
//                 String errorMessage = errorNode.has("info") ? errorNode.get("info").asText() : "Unknown API error";
//                 response.setValid(false);
//                 response.setMessage("NumVerify API Error [" + errorCode + "]: " + errorMessage);
//                 // Log the error for debugging
//                 System.err.println("NumVerify API returned an error: " + jsonResponse);
//             } else if (root.has("valid")) { // Only proceed if "valid" field exists
//                 if (root.get("valid").asBoolean()) {
//                     response.setValid(true);
//                     response.setCountryCode(root.get("country_code") != null ? root.get("country_code").asText() : null);
//                     response.setCountryName(root.get("country_name") != null ? root.get("country_name").asText() : null);
//                     response.setOperatorName(root.get("carrier") != null ? root.get("carrier").asText() : "Unknown");
//                     response.setMessage("Valid Number");
//                 } else {
//                     response.setValid(false);
//                     // Check for potential "error" field within a "valid: false" response if NumVerify does that
//                     // (though usually, if valid is false, it's just invalid, not an API error).
//                     // If NumVerify provides a reason for invalidity in a specific field, parse it here.
//                     response.setMessage("Invalid Number");
//                     // You might want to log the full response here too if it's "valid: false" but still unexpected.
//                 }
//             } else {
//                 // This case handles responses that are neither a standard success nor a standard error with "error" node.
//                 // This could indicate an unexpected API response format.
//                 response.setValid(false);
//                 response.setMessage("Unexpected response format from NumVerify API.");
//                 System.err.println("Unexpected JSON format from NumVerify: " + jsonResponse);
//             }
//             // --- IMPORTANT CHANGE ENDS HERE ---

//         } catch (Exception e) {
//             response.setValid(false);
//             response.setMessage("An unexpected error occurred while validating mobile number: " + e.getMessage());
//             System.err.println("Exception during mobile number validation: " + e.getMessage());
//             e.printStackTrace(); // Print stack trace for more detailed debugging
//         }
//         return response;
//     }
// }

// @Service
// public class MobileValidationService {

//    @Value("${numverify.api.key:8f3cbe2f013d650cb01de9c41d0b9dcb}")
//    private String apiKey;

//    private final String NUMVERIFY_URL = "http://apilayer.net/api/validate";

//    public MobileValidationResponse validateMobile(String mobileNumber) {
//       MobileValidationResponse response = new MobileValidationResponse();
//       try {
//          RestTemplate restTemplate = new RestTemplate();
//          String url = NUMVERIFY_URL + "?access_key=" + apiKey + "&number=" + mobileNumber;

//          String jsonResponse = restTemplate.getForObject(url, String.class);
//          ObjectMapper mapper = new ObjectMapper();
//          JsonNode root = mapper.readTree(jsonResponse);

//          // --- IMPORTANT: Check for NumVerify API errors first ---
//          JsonNode errorNode = root.get("error");
//          if (errorNode != null) {
//             // NumVerify returned an error object
//             int code = errorNode.get("code") != null ? errorNode.get("code").asInt() : -1;
//             String info = errorNode.get("info") != null ? errorNode.get("info").asText() : "Unknown API error";
//             response.setValid(false);
//             response.setMessage("NumVerify API Error (" + code + "): " + info);
//             System.err.println("NumVerify API Error for " + mobileNumber + ": " + info);
//             return response; // Exit early if NumVerify itself returned an error
//          }
//          // --- End of error check ---

//          // Proceed only if no 'error' object was found
//          if (root.get("valid") != null && root.get("valid").asBoolean()) {
//             response.setValid(true);
//             response.setCountryCode(root.get("country_code") != null ? root.get("country_code").asText() : null);
//             response.setCountryName(root.get("country_name") != null ? root.get("country_name").asText() : null);
//             response.setOperatorName(root.get("carrier") != null ? root.get("carrier").asText() : "Unknown");
//             response.setMessage("Valid Number");
//          } else {
//             // If 'valid' field is present but false, or if 'valid' field is missing but no 'error' object
//             // (though the 'error' check above should catch most missing 'valid' cases)
//             String numVerifyMessage = root.get("message") != null ? root.get("message").asText()
//                   : "Invalid Number or unexpected response from NumVerify.";
//             response.setValid(false);
//             response.setMessage(numVerifyMessage);
//          }

//       } catch (HttpClientErrorException.BadRequest e) {
//          // This catches 400 errors from the external API call (NumVerify)
//          response.setValid(false);
//          response
//                .setMessage("Validation service received a bad request from NumVerify: " + e.getResponseBodyAsString());
//          System.err.println(
//                "HttpClientErrorException$BadRequest: " + e.getMessage() + " Body: " + e.getResponseBodyAsString());
//       } catch (Exception e) {
//          response.setValid(false);
//          response.setMessage("An unexpected error occurred during mobile validation: " + e.getMessage());
//          System.err.println("Unexpected Exception in MobileValidationService: " + e.getMessage());
//       }
//       return response;
//    }
// }

// https://apilayer.net/api/validate?access_key=8f3cbe2f013d650cb01de9c41d0b9dcb&number=+96178914563

// {
// "valid": true,
// "number": "96178914563",
// "local_format": "078914563",
// "international_format": "+96178914563",
// "country_prefix": "+961",
// "country_code": "LB",
// "country_name": "Lebanon",
// "location": "",
// "carrier": "Mobile Interim Company 2 sal (MIC2)",
// "line_type": "mobile"
// }
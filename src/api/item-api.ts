import axios from "axios";
import type { Item, Category, MobileValidationResponse } from "../types"; // Import Category if still needed for sending data

const API_URL = "http://localhost:8080/api/items";
const MOBILE_VALIDATION_URL = "http://localhost:8081/api/mobile";

// Item interface now matches DTO structure for responses
export async function getItems(): Promise<Item[]> {
  const response = await axios.get<Item[]>(API_URL);
  return response.data;
}

// The input is still the backend's Item entity structure
// but the return will be the DTO structure
export async function createItem(item: {
  name: string;
  description: string;
  mobileNumber: string;
  category: Category;
}): Promise<Item> {
  const response = await axios.post<Item>(API_URL, item); // Axios expects Item interface
  return response.data;
}

// Get item by id
export async function getItemById(id: number): Promise<Item> {
  const response = await axios.get<Item>(`${API_URL}/${id}`);
  return response.data;
}

// The input is still the backend's Item entity structure
// but the return will be the DTO structure
export async function updateItem(
  id: number,
  item: {
    id: number;
    name: string;
    description: string;
    mobileNumber: string;
    category: Category;
  }
): Promise<Item> {
  const response = await axios.put<Item>(`${API_URL}/update/${id}`, item); // Axios expects Item interface
  return response.data;
}

// Delete item
export async function deleteItem(id: number): Promise<void> {
  await axios.delete(`${API_URL}/delete/${id}`);
}

// Validate mobile number
export async function validateMobileNumber(
  mobileNumber: string
): Promise<MobileValidationResponse> {
  const response = await axios.get<MobileValidationResponse>(
    `${MOBILE_VALIDATION_URL}/validate/${mobileNumber}`
  );
  return response.data;
}

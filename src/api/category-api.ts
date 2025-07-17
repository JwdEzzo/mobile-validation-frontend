// src/api/category-api.ts
import axios from "axios";
import type { Category } from "../types";

const API_URL = "http://localhost:8080/api/categories";

export async function getCategories(): Promise<Category[]> {
  const response = await axios.get<Category[]>(API_URL);
  return response.data;
}

export async function createCategory(
  category: Omit<Category, "id">
): Promise<Category> {
  const response = await axios.post<Category>(API_URL, category);
  return response.data;
}

export async function getCategoryById(id: number): Promise<Category> {
  const response = await axios.get<Category>(`${API_URL}/${id}`);
  return response.data;
}

export async function updateCategory(
  id: number,
  category: Category
): Promise<Category> {
  const response = await axios.put<Category>(
    `${API_URL}/update/${id}`,
    category
  );
  return response.data;
}

export async function deleteCategory(id: number): Promise<void> {
  await axios.delete(`${API_URL}/delete/${id}`);
}

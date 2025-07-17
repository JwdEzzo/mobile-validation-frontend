// src/types.ts
export interface Item {
  id: number;
  name: string;
  description: string;
  mobileNumber: string;
  categoryId: number | null;
  categoryName: string;
}

export interface Category {
  id: number;
  name: string;
}

export interface MobileValidationResponse {
  valid: boolean;
  countryCode?: string;
  countryName?: string;
  operatorName?: string;
  message: string;
}

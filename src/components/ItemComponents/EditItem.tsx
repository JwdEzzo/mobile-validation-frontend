import React, { useState, useEffect } from "react";
import type { Item, Category } from "../../types";
import { updateItem, getItemById } from "../../api/item-api";
import { getCategories } from "../../api/category-api";
import { useNavigate, useParams, Link } from "react-router-dom";

function EditItem() {
  // States to edit items
  const [item, setItem] = useState<Item | null>(null);
  const [name, setName] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [mobileNumber, setMobileNumber] = useState<string>("");
  const [categoryId, setCategoryId] = useState<number | "">("");
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  // using the useNavigate hook of the react-router library
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  // Load the item to edit and categories
  async function loadItemAndCategories() {
    if (!id) {
      console.error("No item ID provided");
      navigate("/items");
      return;
    }

    try {
      // Load both item and categories in parallel
      const [itemResponse, categoriesResponse] = await Promise.all([
        getItemById(Number(id)),
        getCategories(),
      ]);

      setItem(itemResponse);
      setCategories(categoriesResponse);

      // Pre-populate form with current item data
      setName(itemResponse.name);
      setDescription(itemResponse.description);
      setMobileNumber(itemResponse.mobileNumber);
      setCategoryId(itemResponse.categoryId || "");
    } catch (error) {
      console.log("Error loading item or categories:", error);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadItemAndCategories();
  }, [id]);

  function handleNameChange(e: React.ChangeEvent<HTMLInputElement>) {
    setName(e.target.value);
  }

  function handleDescriptionChange(e: React.ChangeEvent<HTMLInputElement>) {
    setDescription(e.target.value);
  }

  function handleMobileNumberChange(e: React.ChangeEvent<HTMLInputElement>) {
    setMobileNumber(e.target.value);
  }

  function handleCategoryChange(e: React.ChangeEvent<HTMLSelectElement>) {
    const value = e.target.value;
    setCategoryId(value === "" ? "" : Number(value));
  }

  async function handleFormSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    if (!item) {
      return;
    }

    if (categoryId === "") {
      alert("Please select a category");
      return;
    }

    try {
      // Find the selected category
      const selectedCategory = categories.find((cat) => cat.id === categoryId);

      if (!selectedCategory) {
        alert("Selected category not found");
        return;
      }

      // Call the updateItem API
      await updateItem(item.id, {
        id: item.id,
        name,
        description,
        mobileNumber,
        category: selectedCategory, // Pass the full category object
      });

      // Navigate back to the items page
      navigate("/items");
      console.log("Edit submitted");
    } catch (error) {
      console.log("Error updating item : ", error);
    }
  }

  if (loading) {
    return <div>Loading item and categories...</div>;
  }

  if (!item) {
    return <div>Item not found</div>;
  }

  return (
    <div>
      <h1>Edit Item</h1>
      <form
        onSubmit={handleFormSubmit}
        style={{ display: "flex", flexDirection: "column", width: "300px" }}
      >
        <label>Name</label>
        <input
          type="text"
          placeholder="Enter name..."
          value={name}
          onChange={handleNameChange}
          required={true}
        />

        <label>Description</label>
        <input
          type="text"
          placeholder="Enter description..."
          value={description}
          onChange={handleDescriptionChange}
          required={true}
        />

        <label>Mobile Number</label>
        <input
          type="text"
          placeholder="Enter mobile number..."
          value={mobileNumber}
          onChange={handleMobileNumberChange}
          required={true}
        />

        <label>Category</label>
        <select
          value={categoryId}
          onChange={handleCategoryChange}
          required={true}
        >
          <option value="">Select a category...</option>
          {categories.map((category) => (
            <option key={category.id} value={category.id}>
              {category.name}
            </option>
          ))}
        </select>

        <button type="submit">Update Item</button>
        <Link to="/items">Back to Items</Link>
      </form>
    </div>
  );
}

export default EditItem;

import { useEffect, useState } from "react";
import type { Category } from "../../types";
import { Link, useNavigate } from "react-router-dom";
import { createItem, validateMobileNumber } from "../../api/item-api";
import { getCategories } from "../../api/category-api";

function CreateItem() {
  // States to create item
  const [name, setName] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [mobileNumber, setMobileNumber] = useState<string>("");
  const [categoryId, setCategoryId] = useState<number | "">("");
  const [loading, setLoading] = useState<boolean>(true);
  const [categories, setCategories] = useState<Category[]>([]);

  const navigate = useNavigate();

  // Function to load categories in the dropdown
  async function loadCategories() {
    try {
      const response = await getCategories();
      setCategories(response);
    } catch (error) {
      console.log("Error fetching categories:", error);
    } finally {
      setLoading(false);
    }
  }

  // Load categories when the component mounts
  useEffect(() => {
    loadCategories();
  }, []);

  function handleNameChange(e: React.ChangeEvent<HTMLInputElement>) {
    setName(e.target.value);
  }

  function handleDescriptionChange(e: React.ChangeEvent<HTMLInputElement>) {
    setDescription(e.target.value);
  }

  function handleMobileChange(e: React.ChangeEvent<HTMLInputElement>) {
    setMobileNumber(e.target.value);
  }

  function handleCategoryChange(e: React.ChangeEvent<HTMLSelectElement>) {
    const value = e.target.value;
    setCategoryId(value === "" ? "" : Number(value));
  }

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    if (categoryId === "") {
      alert("Please select a category");
      return;
    }

    //This Blocvk of code is commented out because this validation test was conflicting with the validation test with the backend, causing the test to fail, even though it was working in postman, but it wasnt weorking in the frontend
    // It gave an error of code 106: Rate limit reached, but the limit wasnt reached.
    // The reason was 2 validations were happening at the same time and that somehow affected the rate limit

    // try {
    //   const validationResult = await validateMobileNumber(mobileNumber);
    //   if (!validationResult.valid) {
    //     alert(
    //       "Please enter a valid mobile number, check for spaces or special characters and the + sign at the beginning."
    //     );
    //     return;
    //   }
    // } catch (error) {
    //   alert("Error validating mobile number");
    //   return;
    // }

    try {
      // Tries to find the full category object from the 'categories' state that matches the 'categoryId' selected by the user in the dropdown.
      // We iterates through the categories array to find the category object whose id matches the categoryId the user selected in his dropdown.
      const selectedCategory = categories.find(
        (category) => category.id === categoryId
      );

      if (!selectedCategory) {
        alert("Selected category not found");
        return;
      }

      // Calls 'createItem' function to send data to the backend.
      // It passes an object containing the item's details, including the full selected category object. Remember that this object needs to match the backend's Item entity structure.
      await createItem({
        name,
        description,
        mobileNumber,
        category: selectedCategory,
      });

      console.log("Item created successfully!");
      navigate("/items");
    } catch (error) {
      console.log("Error creating item:", error);
    }
  }

  if (loading === true) {
    return <div>Loading categories...</div>;
  }

  return (
    <div>
      <h1>Create Item</h1>
      <form
        onSubmit={handleSubmit}
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
          onChange={handleMobileChange}
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

        <button type="submit">Submit Item</button>
        <Link to="/">Back to Dashboard</Link>
        <Link to="/items">Back to Item Table</Link>
      </form>
    </div>
  );
}

export default CreateItem;

import React, { useEffect, useState } from "react";
import type { Category } from "../../types";
import { Link, useNavigate, useParams } from "react-router-dom";
import { getCategoryById, updateCategory } from "../../api/category-api";

function EditCategory() {
  // States to update category and its name
  const [category, setCategory] = useState<Category | null>(null);
  const [name, setName] = useState<string>("");

  // using the useNavigate hook of the react-router library
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  // Function to load the category
  async function loadCategory() {
    // If there is no id, return undefined;
    if (!id) {
      return;
    }

    try {
      // Call the getCategoryById API
      const response: Category = await getCategoryById(Number(id));
      setCategory(response);
      // Update the name using the state
      setName(response.name);
    } catch (error) {
      console.log("Error fetching category:", error);
    }
  }

  // re-rendering / Loading the category everytime the pathvariable "id" changes
  useEffect(() => {
    loadCategory();
  }, [id]);

  // Function to change the name
  function handleNameChange(e: React.ChangeEvent<HTMLInputElement>) {
    setName(e.target.value);
  }

  // This function is the event handler for the form.
  // It calls the updateCategory API and then redirects the user to the home page.
  async function handleFormSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    // If category is undefined, null, or empty, then return.
    if (!category) {
      return;
    }

    try {
      // Call the updateCategory API
      await updateCategory(category.id, { id: category.id, name: name });
      // Navigate back to the home page
      navigate("/");
      console.log("Edit submitted");
    } catch (error) {
      console.log("Error updating item : ", error);
    } finally {
      setName(""); // Reset the name state
    }
  }

  // If category is undefined, null, or empty, then return
  if (!category) {
    return <div>Loading... Please wait</div>;
  }

  return (
    <div>
      <h2>Edit Category</h2>
      <form onSubmit={handleFormSubmit}>
        <label>Name</label>
        <input
          type="text"
          value={name}
          placeholder="Enter Name"
          onChange={handleNameChange}
          required
          //
        />
        <button type="submit">Submit Edit</button>
      </form>
      <Link to="/">Back to Table</Link>
    </div>
  );
}

export default EditCategory;

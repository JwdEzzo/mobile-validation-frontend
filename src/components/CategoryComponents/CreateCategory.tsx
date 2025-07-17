import React, { useState } from "react";
import { Link } from "react-router-dom";
import { createCategory } from "../../api/category-api";

function CreateCategory() {
  //  State for changing the name
  const [name, setName] = useState<string>("");

  // Function to change the name
  function handleNameChange(e: React.ChangeEvent<HTMLInputElement>) {
    setName(e.target.value);
  }

  // Function to create a category using the name state, then reset the name back to empty
  async function handleCreateSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    await createCategory({ name });
    setName("");
  }

  return (
    <div>
      <h1>Create a Category</h1>
      <form
        style={{ display: "flex", flexDirection: "column", width: "300px" }}
        onSubmit={handleCreateSubmit}
      >
        <label>Name:</label>
        <input
          type="text"
          placeholder="Enter Name"
          value={name}
          required={true}
          onChange={handleNameChange}
          //
        />
        <div style={{ display: "flex", justifyContent: "center" }}>
          <button type="submit" style={{ marginTop: "10px" }}>
            Save
          </button>
          <Link to="/" style={{ marginLeft: "10px" }}>
            Back to Dashboard
          </Link>
          <Link to="/categories" style={{ marginLeft: "10px" }}>
            Back to Categories
          </Link>
        </div>
      </form>
    </div>
  );
}

export default CreateCategory;

import { useEffect, useState } from "react";
import type { Category } from "../../types";
import { Link, useNavigate } from "react-router-dom";
import { deleteCategory, getCategories } from "../../api/category-api";

function CategoryTable() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [refresh, setRefresh] = useState<boolean>(false); // To force a rerender every time an item is fetched, deleted, or edited
  const navigate = useNavigate();
  async function fetchCategories() {
    const response: Category[] = await getCategories();
    setCategories(response);
  }

  // Function to navigate to the view/edit page when we click on the view/edit button
  // The id of the category is assigned automatgically during the mapping process, line 55-57
  function readDetails(id: number) {
    navigate("/category/view/" + id);
  }

  function editDetails(id: number) {
    navigate("/category/edit/" + id);
  }

  // Function to delete a category using the id passed during the mapping process
  async function handleDelete(id: number) {
    try {
      await deleteCategory(id);
      await fetchCategories(); // Directly refetch categories after successful deletion
    } catch (error) {
      console.log("Error deleting item", error);
      console.log("Check if there are items associated with this category!!");
    }
    setRefresh(true);
    setRefresh(false);
  }

  // re-rendering / Loading the category everytime we refresh
  useEffect(() => {
    fetchCategories();
  }, [refresh]);

  return (
    <div>
      <h1>Category Table</h1>
      <div>
        <Link to="/category/create">Create Category</Link>
        <Link to="/" style={{ marginLeft: "16px" }}>
          Back to Dashboard
        </Link>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {categories.map((category) => (
              <tr key={category.id}>
                <td>{category.id}</td>
                <td>{category.name}</td>
                <td>
                  <button onClick={() => readDetails(category.id)}>View</button>
                  <button onClick={() => editDetails(category.id)}>Edit</button>
                  <button onClick={() => handleDelete(category.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default CategoryTable;

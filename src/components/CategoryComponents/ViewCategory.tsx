import { useEffect, useState } from "react";
import type { Category } from "../../types";
import { getCategoryById } from "../../api/category-api";
import { Link, useParams } from "react-router-dom";

function ViewCategory() {
  // id State to get the category, which is the path variable
  // category State to get the category
  // loading State to check if the category is loading
  const { id } = useParams<{ id: string }>();
  const [category, setCategory] = useState<Category | null>(null);
  const [loading, setLoading] = useState(true);

  // Function to load/view the category
  async function viewDetails() {
    try {
      setLoading(true); // start loading
      const response: Category = await getCategoryById(Number(id)); // wait for the response
      setCategory(response); // set the category state as the category response
    } catch (error) {
      console.log("Error fetching category:", error);
    } finally {
      setLoading(false); // stop loading
    }
  }

  useEffect(() => {
    viewDetails();
  }, [id]); // Rerenderss when ID changes

  if (loading) return <div>Loading...</div>;
  if (!category) return <div>Category not found</div>;

  return (
    <div>
      <h1>Category Details</h1>
      <div>
        <p>ID: {category.id}</p>
        <p>Name: {category.name}</p>
      </div>
      <Link to="/categories">Back to table</Link>
    </div>
  );
}

export default ViewCategory;

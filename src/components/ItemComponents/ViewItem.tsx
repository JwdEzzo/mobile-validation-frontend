import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import type { Item } from "../../types";
import { getItemById } from "../../api/item-api";

function ViewItem() {
  // id state to get the item, using react-router
  const { id } = useParams<{ id: string }>();
  const [item, setItem] = useState<Item | null>(null);
  const [loading, setLoading] = useState(true);

  // Function to load/view the item
  async function viewDetails() {
    try {
      setLoading(true);
      const response: Item = await getItemById(Number(id)); // wait for the response , the parameter passed is the id
      setItem(response);
    } catch (error) {
      console.log("Error fetching item:", error);
    } finally {
      setLoading(false);
    }
  }

  // Load the item when the component mounts, AND/OR when the id changes
  useEffect(() => {
    viewDetails();
  }, [id]);

  if (loading) {
    return <div>Loading... Please wait!</div>;
  }

  if (!item) {
    return <div>Item not found</div>;
  }

  return (
    <div>
      <h1>Item Details</h1>
      <div>
        <p>ID: {item.id}</p>
        <p>Name: {item.name}</p>
        <p>Description: {item.description}</p>
        <p>Mobile Number: {item.mobileNumber}</p>
        <p>Category: {item.categoryName || "No Category"}</p>
      </div>
      <Link to="/items">Back to table</Link>
    </div>
  );
}

export default ViewItem;

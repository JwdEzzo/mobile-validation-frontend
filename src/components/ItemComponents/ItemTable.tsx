import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { deleteItem, getItems } from "../../api/item-api";
import type { Item } from "../../types";

function ItemTable() {
  //
  const [items, setItems] = useState<Item[]>([]);
  const [refresh, setRefresh] = useState(false);

  const navigate = useNavigate();

  async function fetchItems() {
    try {
      const response: Item[] = await getItems();
      setItems(response);
      console.log("Fetched Items:", response); // Add this line
      setRefresh(true);
      setRefresh(false);
    } catch (error) {
      console.log("Error fetching items:", error);
    }
  }

  useEffect(() => {
    fetchItems();
  }, [refresh, navigate]);

  async function handleDelete(id: number): Promise<void> {
    await deleteItem(id);
    await fetchItems();
    setRefresh(true);
    setRefresh(false);
    console.log("Item deleted successfully");
  }

  function editDetails(id: number): void {
    navigate("/item/edit/" + id);
  }

  function readDetails(id: number) {
    navigate("/item/view/" + id);
  }

  return (
    <div>
      <h1>Item Table</h1>
      <div>
        <Link to="/item/create">Create Item</Link>
        <Link to="/" style={{ marginLeft: "16px" }}>
          Back to Dashboard
        </Link>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Description</th>
              <th>Mobile Number</th>
              <th>Category</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.name}</td>
                <td>{item.description}</td>
                <td>{item.mobileNumber}</td>
                <td>{item.categoryName || "No Category"}</td>
                <td>
                  <button onClick={() => readDetails(item.id)}>View</button>
                  <button onClick={() => editDetails(item.id)}>Edit</button>
                  <button onClick={() => handleDelete(item.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ItemTable;

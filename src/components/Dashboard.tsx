import CategoryTable from "./CategoryComponents/CategoryTable";
import ItemTable from "./ItemComponents/ItemTable";

function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>

      <div style={{ marginBottom: "16px" }}>
        <h2>Categories</h2>
        <CategoryTable />
      </div>

      <div>
        <h2>Items</h2>
        <ItemTable />
      </div>
    </div>
  );
}

export default Dashboard;

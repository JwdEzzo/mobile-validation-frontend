// src/App.tsx

import { BrowserRouter, Route, Routes } from "react-router-dom";
import CategoryTable from "./components/CategoryComponents/CategoryTable";
import CreateCategory from "./components/CategoryComponents/CreateCategory";
import EditCategory from "./components/CategoryComponents/EditCategory";
import ViewCategory from "./components/CategoryComponents/ViewCategory";
import ItemTable from "./components/ItemComponents/ItemTable";
import EditItem from "./components/ItemComponents/EditItem";
import CreateItem from "./components/ItemComponents/CreateItem";
import ViewItem from "./components/ItemComponents/ViewItem";
import Dashboard from "./components/Dashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Default route for Dashboard */}
        <Route path="/" element={<Dashboard />} />

        <Route path="/categories" element={<CategoryTable />} />
        <Route path="/category/create" element={<CreateCategory />} />
        <Route path="/category/edit/:id" element={<EditCategory />} />
        <Route path="/category/view/:id" element={<ViewCategory />} />

        <Route path="/items" element={<ItemTable />} />
        <Route path="/item/create" element={<CreateItem />} />
        <Route path="/item/edit/:id" element={<EditItem />} />
        <Route path="/item/view/:id" element={<ViewItem />} />

        {/*All errors go to this */}
        <Route path="*" element={<div>Page Not Found</div>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

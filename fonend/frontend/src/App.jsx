import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import NavigationBar from "./components/NavigationBar";
import AdminTables from "./pages/AdminTables";
import AdminMenu from "./pages/AdminMenu";
import OrderPage from "./pages/OrderPage";
import ReportPage from "./pages/ReportPage";
import AdminReport from "./pages/AdminReport";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Profile from "./pages/Profile";

function Home() {
  return (
    <div className="text-center mt-5">
      <h1 className="fw-bold text-primary">☕ Trang chủ quán cafe</h1>
      <p className="lead">Chào mừng bạn đến hệ thống quản lý quán cafe!</p>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <NavigationBar />
      <div className="container mt-4">
        <Routes>
        <Route path="/admin/report" element={<AdminReport />} />
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/admin/tables" element={<AdminTables />} />
          <Route path="/admin/menu" element={<AdminMenu />} />
          <Route path="/order" element={<OrderPage />} />
          <Route path="/report" element={<ReportPage />} />
          <Route path="/profile" element={<Profile />} />
          <Route
            path="*"
            element={<div className="text-center mt-5">Trang không tồn tại</div>}
          />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

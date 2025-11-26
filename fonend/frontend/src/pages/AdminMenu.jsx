import React, { useEffect, useState } from "react";
import client from "../api/axiosClient";

export default function AdminMenu() {
  const [items, setItems] = useState([]);
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");

  useEffect(() => { load(); }, []);

  const load = async () => {
    const res = await client.get("/api/menu");
    setItems(res.data);
  };

  const create = async () => {
    if (!name || !price) return alert("Vui lòng nhập đầy đủ thông tin!");
    await client.post("/api/menu", { name, price });
    setName("");
    setPrice("");
    load();
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center text-primary mb-4">📋 Quản lý Menu</h2>

      <div className="card p-4 shadow-sm mb-4">
        <h5>Thêm món mới</h5>
        <div className="d-flex gap-2 mt-2">
          <input
            className="form-control"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Tên món"
          />
          <input
            type="number"
            className="form-control"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            placeholder="Giá (VND)"
          />
          <button className="btn btn-success" onClick={create}>Thêm</button>
        </div>
      </div>

      <div className="card shadow-sm p-4">
        <h5>Danh sách món</h5>
        <ul className="list-group mt-3">
          {items.map(i => (
            <li key={i.id} className="list-group-item d-flex justify-content-between">
              <span>{i.name}</span>
              <span className="fw-bold text-success">{i.price.toLocaleString()} VND</span>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

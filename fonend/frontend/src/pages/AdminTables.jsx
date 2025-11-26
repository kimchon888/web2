import React, { useEffect, useState } from "react";
import client from "../api/axiosClient";

export default function AdminTables() {
  const [tables, setTables] = useState([]);
  const [num, setNum] = useState("");

  useEffect(() => { load(); }, []);

  const load = async () => {
    try {
      const res = await client.get("/api/tables");
      setTables(res.data);
    } catch (e) {
      console.error("Lỗi tải danh sách bàn:", e);
      alert("Không thể tải danh sách bàn!");
    }
  };

  const create = async () => {
    if (!num) return alert("Nhập số bàn!");
    try {
      await client.post("/api/tables", { tableNumber: num, status: "EMPTY" });
      setNum("");
      load();
    } catch (e) {
      alert("Không thể tạo bàn mới!");
    }
  };

  const updateStatus = async (id, status) => {
    try {
      await client.put(`/api/tables/${id}/status?status=${status}`);
      load();
    } catch (e) {
      alert("Không thể cập nhật trạng thái!");
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center text-primary mb-4">🪑 Quản lý Bàn</h2>

      <div className="card p-4 shadow-sm mb-4">
        <h5>Tạo bàn mới</h5>
        <div className="d-flex gap-2 mt-2">
          <input
            type="number"
            className="form-control"
            placeholder="Số bàn"
            value={num}
            onChange={(e) => setNum(e.target.value)}
          />
          <button className="btn btn-success" onClick={create}>Tạo</button>
        </div>
      </div>

      <div className="card shadow-sm p-4">
        <h5>Danh sách bàn</h5>
        <ul className="list-group mt-3">
          {tables.map(t => (
            <li key={t.id} className="list-group-item d-flex justify-content-between align-items-center">
              <span>🪑 Bàn #{t.tableNumber} - <strong>{t.status}</strong></span>
              <div className="btn-group">
                <button className="btn btn-outline-secondary btn-sm" onClick={() => updateStatus(t.id, "EMPTY")}>Empty</button>
                <button className="btn btn-outline-warning btn-sm" onClick={() => updateStatus(t.id, "OCCUPIED")}>Occupied</button>
                <button className="btn btn-outline-success btn-sm" onClick={() => updateStatus(t.id, "PAID")}>Paid</button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

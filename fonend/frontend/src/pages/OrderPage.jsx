import React, { useEffect, useState } from "react";
import client from "../api/axiosClient";

export default function OrderPage() {
  const [availableTables, setAvailableTables] = useState([]);
  const [menu, setMenu] = useState([]);
  const [selectedTable, setSelectedTable] = useState("");
  const [cart, setCart] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      client.get("/api/tables/available"),
      client.get("/api/menu")
    ])
      .then(([t, m]) => {
        setAvailableTables(t.data);
        setMenu(m.data);
      })
      .catch(() => alert("Lỗi tải dữ liệu!"))
      .finally(() => setLoading(false));
  }, []);

  const addToCart = (id) =>
    setCart({ ...cart, [id]: (cart[id] || 0) + 1 });

  const removeFromCart = (id) => {
    const updated = { ...cart };
    if (updated[id] > 1) updated[id]--;
    else delete updated[id];
    setCart(updated);
  };

  const placeOrder = async () => {
    if (!selectedTable || Object.keys(cart).length === 0) {
      alert("⚠️ Hãy chọn bàn và món trước khi đặt hàng!");
      return;
    }

    const items = Object.keys(cart).map((k) => ({
      menuItemId: Number(k),
      quantity: cart[k],
    }));

    try {
      await client.post("/api/orders", {
        tableId: selectedTable,
        items
      });

      alert("✅ Đặt hàng thành công!");
      setCart({});
      setSelectedTable("");

      // reload lại danh sách bàn trống
      const r = await client.get("/api/tables/available");
      setAvailableTables(r.data);

    } catch (err) {
      console.error(err);
      alert("❌ Lỗi khi đặt hàng!");
    }
  };

  if (loading) return <h3 className="text-center mt-5">⏳ Đang tải...</h3>;

  return (
    <div className="container mt-4">
      <h2 className="text-center text-primary mb-4">☕ Đặt bàn & chọn món</h2>

      <div className="card shadow-sm p-4 mb-4">
        <h5>Chọn bàn</h5>
        {availableTables.length === 0 ? (
          <p className="text-danger">Hiện không còn bàn trống!</p>
        ) : (
          <select
            className="form-select w-50 mt-2"
            onChange={(e) => setSelectedTable(e.target.value)}
            value={selectedTable}
          >
            <option value="">-- Chọn bàn --</option>
            {availableTables.map(t => (
              <option key={t.id} value={t.id}>Bàn {t.tableNumber}</option>
            ))}
          </select>
        )}
      </div>

      <div className="card shadow-sm p-4 mb-4">
        <h5>Menu</h5>
        <div className="row mt-3">
          {menu.map(m => (
            <div key={m.id} className="col-md-4 mb-3">
              <div className="card p-3 shadow-sm h-100 text-center">
                <h5>{m.name}</h5>
                <p className="text-muted">{m.price.toLocaleString()} VND</p>
                <div>
                  <button className="btn btn-sm btn-success me-2" onClick={() => addToCart(m.id)}>+</button>
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={() => removeFromCart(m.id)}
                    disabled={!cart[m.id]}
                  >
                    -
                  </button>
                  <span className="ms-2 fw-bold">{cart[m.id] || 0}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="card shadow-sm p-4 mb-4">
        <h5>Giỏ hàng</h5>
        {Object.keys(cart).length === 0 ? (
          <p>Chưa chọn món nào.</p>
        ) : (
          <ul className="list-group mt-3">
            {Object.keys(cart).map(id => {
              const item = menu.find(m => m.id === Number(id));
              return (
                <li key={id} className="list-group-item d-flex justify-content-between">
                  <span>{item?.name} × {cart[id]}</span>
                  <strong>{(item?.price || 0) * cart[id]} VND</strong>
                </li>
              );
            })}
          </ul>
        )}
      </div>

      <div className="text-center">
        <button
          className="btn btn-primary btn-lg"
          onClick={placeOrder}
          disabled={!selectedTable || Object.keys(cart).length === 0}
        >
          🛒 Đặt hàng
        </button>
      </div>
    </div>
  );
}

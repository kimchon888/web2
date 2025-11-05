import React from "react";

function HomePage() {
  return (
    <div className="text-center mt-5">
      <h1 className="fw-bold text-primary mb-3">☕ Hệ thống Quản lý Quán Cafe</h1>
      <p className="lead text-muted">Quản lý đặt bàn, menu và đơn hàng thông minh, nhanh chóng!</p>
      <img
        src="https://cdn-icons-png.flaticon.com/512/924/924514.png"
        alt="coffee"
        width="150"
        className="mt-4 shadow-sm"
      />
    </div>
  );
}

export default HomePage;

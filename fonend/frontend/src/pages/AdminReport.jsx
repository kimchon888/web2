import { useEffect, useState } from "react";
import client from "../api/axiosClient";

export default function AdminReport() {
  const [report, setReport] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    client.get("/api/orders/report/today")
      .then(res => setReport(res.data))
      .catch(err => {
        console.error("Lỗi khi tải báo cáo:", err);
        alert("Không thể tải báo cáo doanh thu!");
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="container mt-5 text-center">
        <h3>⏳ Đang tải báo cáo...</h3>
      </div>
    );
  }

  return (
    <div className="container mt-5 text-center">
      <h2 className="text-primary mb-4">📊 Báo cáo doanh thu hôm nay</h2>
      <div className="card shadow-lg p-5 bg-light">
        <h4 className="mb-3">
          Tổng số đơn hàng: <strong>{report.totalOrders}</strong>
        </h4>
        <h4>
          Tổng doanh thu:{" "}
          <strong>{report.totalRevenue.toLocaleString()} VND</strong>
        </h4>
      </div>
    </div>
  );
}

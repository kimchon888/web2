import { useEffect, useState } from "react";
import client from "../api/axiosClient";

export default function AdminReport() {
  const [report, setReport] = useState({ totalOrders: 0, totalRevenue: 0 });

  useEffect(() => {
    client.get("/orders/report/today")
      .then(res => setReport(res.data))
      .catch(err => console.error("Lỗi khi tải báo cáo:", err));
  }, []);

  return (
    <div className="container mt-5 text-center">
      <h2 className="text-primary mb-4">📊 Báo cáo doanh thu hôm nay</h2>
      <div className="card shadow-lg p-5 bg-light">
        <h4 className="mb-3">Tổng số đơn hàng: <strong>{report.totalOrders}</strong></h4>
        <h4>Tổng doanh thu: <strong>{report.totalRevenue.toLocaleString()} VND</strong></h4>
      </div>
    </div>
  );
}

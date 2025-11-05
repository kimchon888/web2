import React, { useEffect, useState } from "react";
import client from "../api/axiosClient";

export default function ReportPage(){
  const [report, setReport] = useState(null);
  useEffect(()=> {
    client.get("/report/daily").then(r=>setReport(r.data));
  },[]);
  if(!report) return <div>Loading...</div>;
  return (
    <div>
      <h2>Báo cáo trong ngày</h2>
      <p>Tổng khách: {report.totalCustomers}</p>
      <p>Tổng doanh thu: {report.totalRevenue}</p>
    </div>
  );
}

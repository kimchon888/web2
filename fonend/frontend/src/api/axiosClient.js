// F:\web2\fonend\frontend\src\api\axiosClient.js
import axios from "axios";

const client = axios.create({
  baseURL: "http://localhost:8082/api", // ⚠️ backend của bạn đang chạy ở cổng 8082
  headers: {
    "Content-Type": "application/json",
  },
});

// Tùy chọn: tự động thêm token nếu có trong localStorage
client.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default client;

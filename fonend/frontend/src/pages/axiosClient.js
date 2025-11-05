// src/api/axiosClient.js
import axios from "axios";

const client = axios.create({
  baseURL: "http://localhost:8082/api",
});

//Tự động thêm Bearer token cho mọi request
client.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default client;

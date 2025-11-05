import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8082/api/auth/login", {
        username,
        password,
      });

      if (res.data.error) {
        alert("❌ " + res.data.error);
        return;
      }

      // 🔹 Trường hợp backend trả về `roles` là mảng
      let role = null;
      if (Array.isArray(res.data.roles)) {
        role = res.data.roles[0]; // lấy role đầu tiên
      } else {
        role = res.data.role; // fallback nếu backend trả về 1 role duy nhất
      }

      // 🔹 Lưu vào localStorage
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", role);
      localStorage.setItem("username", res.data.username);

      window.dispatchEvent(new Event("authChange"));
      alert("✅ Đăng nhập thành công!");
      navigate("/");
    } catch (err) {
      alert("❌ Sai tài khoản hoặc mật khẩu!");
      console.error(err);
    }
  };

  return (
    <div
      className="d-flex align-items-center justify-content-center vh-100"
      style={{
        background: "linear-gradient(135deg, #e0f7fa, #f1f8e9)",
      }}
    >
      <div className="card shadow-lg p-4" style={{ width: "400px" }}>
        <h3 className="text-center mb-4 text-primary">☕ Coffee Login</h3>

        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label fw-semibold">Tên đăng nhập</label>
            <input
              type="text"
              className="form-control"
              placeholder="Nhập username..."
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label fw-semibold">Mật khẩu</label>
            <input
              type="password"
              className="form-control"
              placeholder="Nhập mật khẩu..."
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button className="btn btn-primary w-100">Đăng nhập</button>
        </form>

        <p className="text-center mt-3 mb-0">
          Chưa có tài khoản?{" "}
          <a href="/register" className="fw-bold text-decoration-none">
            Đăng ký ngay
          </a>
        </p>
      </div>
    </div>
  );
}

export default Login;

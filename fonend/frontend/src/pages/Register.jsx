import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Register() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8082/api/auth/register", {
        username,
        email,
        password,
      });
      alert("🎉 Đăng ký thành công!");
      navigate("/login");
    } catch (err) {
      alert("⚠️ Tên đăng nhập đã tồn tại!");
    }
  };

  return (
    <div
      className="d-flex align-items-center justify-content-center vh-100"
      style={{
        background: "linear-gradient(135deg, #f8bbd0, #f3e5f5)",
      }}
    >
      <div className="card shadow-lg p-4" style={{ width: "400px" }}>
        <h3 className="text-center mb-4 text-success">☕ Coffee Register</h3>

        <form onSubmit={handleRegister}>
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
            <label className="form-label fw-semibold">Email</label>
            <input
              type="email"
              className="form-control"
              placeholder="Nhập email..."
              value={email}
              onChange={(e) => setEmail(e.target.value)}
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

          <button className="btn btn-success w-100">Đăng ký</button>
        </form>

        <p className="text-center mt-3 mb-0">
          Đã có tài khoản?{" "}
          <a href="/login" className="fw-bold text-decoration-none text-success">
            Đăng nhập
          </a>
        </p>
      </div>
    </div>
  );
}

export default Register;

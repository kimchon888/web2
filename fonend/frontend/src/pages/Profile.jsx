import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Profile() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, [navigate]);

  if (!user) {
    return (
      <div className="text-center mt-5">
        <h4>Đang tải thông tin tài khoản...</h4>
      </div>
    );
  }

  return (
    <div className="container mt-5" style={{ maxWidth: "600px" }}>
      <div className="card shadow p-4">
        <h3 className="text-center text-primary mb-3">👤 Thông tin tài khoản</h3>
        <ul className="list-group list-group-flush">
          <li className="list-group-item">
            <strong>Tên đăng nhập:</strong> {user.username}
          </li>
          {user.role && (
            <li className="list-group-item">
              <strong>Vai trò:</strong> {user.role}
            </li>
          )}
        </ul>
        <button
          className="btn btn-danger mt-3 w-100"
          onClick={() => {
            localStorage.clear();
            window.dispatchEvent(new Event("authChange"));
            navigate("/login");
          }}
        >
          Đăng xuất
        </button>
      </div>
    </div>
  );
}

export default Profile;

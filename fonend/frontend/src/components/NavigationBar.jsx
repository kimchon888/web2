import React, { useEffect, useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { Navbar, Nav, Container, Button } from "react-bootstrap";

function NavigationBar() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));
  const [role, setRole] = useState(localStorage.getItem("role"));
  const navigate = useNavigate();

  useEffect(() => {
    const handleAuthChange = () => {
      setIsLoggedIn(!!localStorage.getItem("token"));
      setRole(localStorage.getItem("role"));
    };

    window.addEventListener("authChange", handleAuthChange);
    return () => window.removeEventListener("authChange", handleAuthChange);
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    window.dispatchEvent(new Event("authChange"));
    navigate("/login");
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
      <Container>
        <Navbar.Brand as={Link} to="/" className="fw-bold text-warning">
          ☕ Coffee System
        </Navbar.Brand>
        <Navbar.Toggle />
        <Navbar.Collapse>
          <Nav className="ms-auto align-items-center">
            <NavLink className="nav-link" to="/">Trang chủ</NavLink>

            {role === "ROLE_ADMIN" && (
              <>
                <NavLink className="nav-link" to="/admin/tables">Bàn</NavLink>
                <NavLink className="nav-link" to="/admin/menu">Menu</NavLink>
                <NavLink className="nav-link" to="/admin/report">Báo cáo</NavLink>
              </>
            )}

            {role === "ROLE_USER" && (
              <NavLink className="nav-link" to="/order">Đặt hàng</NavLink>
            )}

            {isLoggedIn ? (
              <>
                <NavLink className="nav-link" to="/profile">Tài khoản</NavLink>
                <Button variant="outline-warning" size="sm" className="ms-2" onClick={handleLogout}>
                  Đăng xuất
                </Button>
              </>
            ) : (
              <>
                <NavLink className="nav-link" to="/login">Đăng nhập</NavLink>
                <NavLink className="nav-link" to="/register">Đăng ký</NavLink>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavigationBar;

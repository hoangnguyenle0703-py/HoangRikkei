#!/usr/bin/env bash
# =====================================================================
#  Script chứng minh hai service dùng database tách biệt.
#  Chạy SAU KHI đã start cả hai service.
# =====================================================================
set -e

USER_SVC="http://localhost:8083/api/v1/users"
INV_SVC="http://localhost:8084/api/v1/products"

echo "========================================================"
echo " 1. Dữ liệu USERS (từ user_db, qua user-service :8083)"
echo "========================================================"
curl -s "$USER_SVC" | python3 -m json.tool || curl -s "$USER_SVC"
echo

echo "========================================================"
echo " 2. Dữ liệu PRODUCTS (từ inventory_db, qua inventory-service :8084)"
echo "========================================================"
curl -s "$INV_SVC" | python3 -m json.tool || curl -s "$INV_SVC"
echo

echo "========================================================"
echo " 3. THÔNG TIN KẾT NỐI DB — bằng chứng tách biệt"
echo "========================================================"
echo ">> user-service:"
curl -s "$USER_SVC/db-info" | python3 -m json.tool || curl -s "$USER_SVC/db-info"
echo
echo ">> inventory-service:"
curl -s "$INV_SVC/db-info" | python3 -m json.tool || curl -s "$INV_SVC/db-info"
echo
echo "========================================================"
echo " => So sánh databaseName / jdbcUrl / connectionPool / dbUser:"
echo "    nếu KHÁC NHAU => hai service KHÔNG dùng chung kết nối."
echo "========================================================"

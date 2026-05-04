# BTL-SE Library Management

Ứng dụng quản lý thư viện dùng Spring Boot, Thymeleaf, Spring Data JPA và MySQL.

## Yêu cầu

- Java 17
- Maven
- MySQL 8.x

## Chạy database

1. Mở MySQL bằng user có quyền tạo database.
2. Chạy script:

```sql
source library-management/src/main/resources/data.sql;
```

Script sẽ `DROP DATABASE IF EXISTS QuanLyThuVien`, tạo lại schema và thêm dữ liệu mẫu.

Tài khoản mẫu:

- Quản lý: số điện thoại `0900000001`, mật khẩu `123`
- Nhân viên: số điện thoại `0900000002`, mật khẩu `123`
- Bạn đọc: email `c@gmail.com`, số điện thoại `0933333333`

## Chạy ứng dụng

Nếu MySQL dùng user `root` và mật khẩu `0211205`, chạy trực tiếp:

```powershell
cd library-management
mvn spring-boot:run
```

Nếu máy khác dùng mật khẩu DB khác:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="mat_khau_mysql"
cd library-management
mvn spring-boot:run
```

Mặc định ứng dụng chạy tại:

```text
http://localhost:8080
```

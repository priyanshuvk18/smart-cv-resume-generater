# SmartCV Builder 📄✨

An industrial-level, full-stack application for building professional CVs/Resumes with ease. SmartCV Builder allows users to create, manage, and download their resumes in various premium templates.

## 🚀 Features

- **User Authentication**: Secure Login and Registration system using JWT.
- **Dashboard**: A user-friendly dashboard to manage all your CVs.
- **Premium Templates**: Choose from several professional designs:
  - **Classic**: Traditional and formal layouts.
  - **Modern**: Clean, sleek, and contemporary designs.
  - **Creative**: Bold and unique styles to stand out.
- **PDF Generation**: High-quality PDF export powered by iText.
- **Admin Panel**: Manage templates and user data (Admin only).
- **Environment Driven**: Scalable configuration using `.env` files.

## 🛠️ Technology Stack

- **Backend**: Java Spring Boot 3.2.x
- **Frontend**: Thymeleaf (Server-side rendering)
- **Security**: Spring Security + JWT
- **Database**: 
  - **Development**: H2 (In-memory)
  - **Production**: MySQL
- **Build Tool**: Maven
- **PDF Engine**: iText 7

## 📋 Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6+ (or use the included `mvnw`)
- **MySQL**: 8.0+ (if running in production mode)

## ⚙️ Installation & Setup

### 1. Clone the repository
```bash
git clone https://github.com/priyanshuvk18/smart-cv-resume-generater.git
cd smart-cv-resume-generater
```

### 2. Configure Environment Variables
Create a `.env` file in the root directory and add your database credentials:

```properties
DB_URL=jdbc:mysql://localhost:3306/smartcv_builder?createDatabaseIfNotExist=true
DB_USERNAME=your_username
DB_PASSWORD=your_password
DB_DRIVER=com.mysql.cj.jdbc.Driver
DB_DIALECT=org.hibernate.dialect.MySQLDialect
JWT_SECRET=your_secure_random_key
```

### 3. Build the project
```bash
./mvnw clean install
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8081`.

## 🛡️ Security

- **JWT Token**: Authentication is handled via JSON Web Tokens.
- **Access Control**: Role-based access (USER, ADMIN).

## 📄 License

This project is licensed under the MIT License.

---
Built with ❤️ by [Priyanshu](https://github.com/priyanshuvk18)

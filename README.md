# Heshima Studio

Heshima Studio is a **mock creative agency web application** built to showcase full-stack development and design skills.  
The backend is built with **Java 21, Spring Boot, and MySQL**, while the frontend (coming next) will use **React** for a sleek, techy, agency-style experience.

---

## 🎯 Project Overview
This application simulates how a real creative agency handles service inquiries and project management.  
Visitors can explore available services and submit inquiries that are saved to the database.

---

## 🛠️ Tech Stack

**Backend**
- Java 21 / Spring Boot 3
- Spring Data JPA (MySQL)
- Spring Security (CustomUserDetailsService)
- JUnit 5 & Mockito for testing
- Maven build system

**Frontend (coming soon)**
- React + Vite
- Axios for API calls
- Tailwind or Styled Components for UI

---

## 🧩 Features

| Feature | Description |
|----------|-------------|
| **Inquiry Management** | Handles service inquiries with `InquiryController` and `InquiryService`. |
| **Product Catalog** | Lists branding, web design, and UX/UI services from `ProductController`. |
| **Authentication** | Custom user details service for login (ADMIN / USER roles). |
| **Data Initialization** | Seeds roles, admin user, and default products on startup. |
| **Error Handling** | Global exception handler returns clean API error responses. |
| **Health Check** | `/api/health` endpoint confirms app is running. |

---

## 🧪 Testing Summary
The backend includes **comprehensive unit tests** using JUnit and Mockito.

✅ `InquiryControllerTest` – verifies POST `/api/inquiries`  
✅ `ProductControllerTest` – verifies GET endpoints  
✅ `ProductServiceImplTest` – tests repository integration logic  
✅ `InquiryServiceImplTest` – covers create, read, delete, and error branches  
✅ `CustomUserDetailsServiceTest` – ensures role mapping and security correctness  
✅ `GlobalExceptionHandlerTest` – validates API error responses  
✅ `DataInitializerTest` – confirms startup seeding works correctly

> All tests passed successfully (100% green).

---

## ⚙️ Running Locally

### 1️⃣ Start MySQL
Make sure MySQL is running, and you’ve created a database, e.g.:
```sql
CREATE DATABASE heshima_studio;
```
### 2️⃣ Configure Application Properties

Update src/main/resources/application.properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/heshima_studio
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

Make sure to replace your password with your local database password.
```

### 3️⃣ Run the App

From IntelliJ or terminal:
```
mvn spring-boot:run
```
The backend will start on http://localhost:8080

### 💻 API Endpoints

```markdown
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/products`       | Fetch all active products |
| GET    | `/api/products/{id}`  | Fetch product by ID |
| POST   | `/api/inquiries`      | Create new inquiry |
| GET    | `/api/inquiries`      | Get all inquiries (Admin only) |
| DELETE | `/api/inquiries/{id}` | Delete inquiry by ID (Admin only) |
| GET    | `/api/health`         | Check backend health status 
```

### 💡 Reflection & Challenges

During development, the biggest challenge was working around the @MockBean incompatibility with newer JDK versions.
I learned how to use pure Mockito mocks (MockMvcBuilders.standaloneSetup) to simulate controller behavior safely;
a valuable technique for maintaining compatibility with future Java releases.

This project strengthened my understanding of Spring Boot architecture, DTO handling, and controller/service testing.

---

### 🎨 Design Vision (Frontend Preview)

The upcoming React frontend will reflect the Heshima Studio aesthetic:
	•	Minimalist black, beige, and off-white palette
	•	Roboto Mono typography
	•	Layout inspired by Apple’s “Orchard” retail experience
	•	Wireframes built in Miro, tasks tracked in Trello
---

### 👤 Author

Jaha Knight
Software Engineering Apprentice @ Boomi
📍 Full-stack Developer | UX Designer | MBA Candidate
💼 Heshima Studio (frontend launching soon)

---

### 🧾 License

This project is for educational and portfolio demonstration purposes only.








# Heshima Studio 👩🏾‍💻

Heshima Studio is a **mock creative agency web application** built to showcase full-stack development, UI design,
amd software engineering best practices.
The backend is built with **Java 21, Spring Boot, and MySQL**, while the frontend will use **React** for a sleek,
techy, agency-style experience.

This project includes:

* A **Spring Boot** + **MySQL** backend powering product data and inquiry storage
* A **React** frontend that delivers a polished, agency-style user experience
* API communication via Axios
* Complete CRUD support for inquiries and service listings
* A lightweight "project scope cart" for selecting service before submitting an inquiry

This application demonstrates both backend architecture and frontend UX execution designed to mirror a real creative agency workflow.

---

## 🎯 Project Overview
Visitors can browse services (Branding, Web Design, UX/UI), add them to a customized “project scope,” and submit an inquiry.
On the backend, all inquiries are stored as **Orders** with optional **Order Items**, giving the admin a clean view of incoming leads.

This simulates the intake process of a real design studio.

---

## 🛠️ Tech Stack
**Backend**
- Java 21
- Spring Boot 3
- Spring Data JPA (MySQL)
- Spring Security (CustomUserDetailsService)
- JUnit 5 / Mockito (100% green tests)
- Maven

**Frontend**
- React 18 + Vite
- Axios
- Inline CSS-in-JS styling
- Responsive layout
- UI/UX designed using Heshima Studio’s brand system
- Miro wireframes + Trello task tracking

---

## 🎨Frontend Features (React)

| Feature                                            | Description                                                                                                  |
|----------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| **Hero Landing Page**                              | Modern agency-style landing section with custom visuals.                                                     |
| **Dynamic Services Page**                          | Fetches real products from the Spring API (/api/products). Falls back to placeholders if backend is offline. |
| **Inquiry Form**                                   | Allows general inquiries or service-specific inquiries.                                                      |
| **Project Scope Cart**                             | Add/remove services, view estimated total before submitting.                                                 |
| **Admin Dashboard(frontend)**                      | Displays inquiries returned by /api/inquiries.                                                               |
| **Routing with React Router**                      | Home → Services → Cart → Admin.                                                                              |
| **Clean, minimalist UI based on brand guidelines** | Beige, navy, orange accent, Roboto Mono aesthetic.                                                           |

## 🧩 Backend Features
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
### 4️⃣ Run the frontend 
```
npm install
npm run dev
```
Frontend will start on http://localhost:5173\
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

### 💡 Challenges & Lessons Learned

- **React Refresh + Routing Conflict**\
I attempted to automatically redirect users back to / on hard refresh.\
Due to React Router + Vite history mode, this caused route loss and broken links so I documented this clearly and removed the feature.
- **Dinero.js Deprecation**\
Stretch goal was currency formatting with Dinero.js, but Vite + ES module imports caused persistent build errors.
I pivoted to Intl.NumberFormat instead; lightweight and reliable.
- **Testing with JDK 21**
Newer Java versions caused issues with @MockBean + Spring Boot testing.
Solution: use pure Mockito (MockMvcBuilders.standaloneSetup) to simulate controllers safely.

Each challenge strengthened my understanding of dependency management, routing, and mocking strategies.

---

### 🚀 Future Enhancements
- Full admin login system 
- Two-factor authentication (2FA)
- Inquiry detail view + status updates
- Full CMS-style product management
- Email notifications on inquiry submit
- Stripe checkout for service deposits

---

### 👤 Author

Jaha Knight\
Software Engineering Apprentice @ Boomi\
📍 Full-stack Developer | UX Designer | MBA Candidate\
💼 Portfolio Project: Heshima Studio\

---

### 🧾 License

This project is for educational and portfolio demonstration purposes only.








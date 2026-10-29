# Smart Hospital Appointment & Patient Management System

A complete Java web project demonstrating **Servlets, JSP, Hibernate, Spring Boot REST APIs**, and MySQL.

## Project Structure

```
SmartHospitalSystem/
├── database/
│   └── schema.sql              # MySQL schema + sample data
├── hospital-web/               # Traditional Servlet + JSP (Tomcat)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/hospital/
│       │   ├── entity/         # JPA entities
│       │   ├── dao/            # Data Access Objects
│       │   ├── servlet/        # Controllers
│       │   └── util/           # HibernateUtil, PasswordUtil
│       ├── resources/
│       │   └── hibernate.cfg.xml
│       └── webapp/             # JSP pages + CSS
└── hospital-api/               # Spring Boot REST API
    ├── pom.xml
    └── src/main/
        ├── java/com/hospital/api/
        │   ├── controller/
        │   ├── entity/
        │   └── repository/
        └── resources/
            └── application.properties
```

## Prerequisites

- JDK 17 or 21
- Apache NetBeans (or IntelliJ / VS Code)
- MySQL 8.0+
- Apache Tomcat **10.1.x** (required for Jakarta EE)

## Quick Start

### 1. Database Setup

```bash
mysql -u root -p < database/schema.sql
```

Or open `database/schema.sql` in MySQL Workbench and execute it.

### 2. Configure Database Password

**hospital-web** → `src/main/resources/hibernate.cfg.xml`  
Change: `<property name="hibernate.connection.password">root</property>`

**hospital-api** → `src/main/resources/application.properties`  
Change: `spring.datasource.password=root`

### 3. Run hospital-web (Servlet/JSP)

1. Open `hospital-web` in NetBeans
2. Add Tomcat 10.1 server (Tools → Servers)
3. Right-click project → Clean and Build
4. Right-click project → Run
5. Open: http://localhost:8080/hospital-web/

### 4. Run hospital-api (Spring Boot)

1. Open `hospital-api` in NetBeans / IntelliJ
2. Run `HospitalApiApplication.java`
3. API base URL: http://localhost:8081

### REST API Endpoints

| Method | Endpoint                              | Description                |
|--------|---------------------------------------|----------------------------|
| GET    | /api/doctors                          | List all active doctors    |
| GET    | /api/doctors/{id}                     | Get doctor by ID           |
| GET    | /api/doctors/{id}/schedule            | Doctor's upcoming schedule |
| GET    | /api/doctors/specialization/{spec}    | Filter by specialization   |
| GET    | /api/appointments/{id}                | Get appointment            |
| GET    | /api/patients/{id}/appointments       | Patient's appointments     |
| GET    | /api/receipts/{id}                    | Get receipt                |

### Features Included

- Patient registration & login (BCrypt password hashing)
- Doctor listing with specialization filter
- Appointment booking with **double-booking prevention**
- Patient dashboard with appointment history
- Cancel appointment
- Digital printable receipts
- Spring Boot REST API (JSON)
- Bootstrap 5 responsive UI

### Default Test Flow

1. Register a new patient
2. Login
3. Browse doctors
4. Book an appointment
5. View receipt (print-ready)
6. Check dashboard

---

**Note:** Use fictional data only. This is a student/demo project.

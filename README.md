# iReedBooks-2

**iReedBooks-2** is an online bookshop project where users can browse and purchase books across various categories, simulate checkout, and manage orders. It includes an admin panel for management tasks such as book, author, and genre administration. ([github.com](https://github.com/COSYBANANA63/iReedBooks-2))

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Features
- User authentication (login, register, password reset) ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/web))
- Remember-me and session management with filters and listeners ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/filters/SessionCheckFilter.java), [github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/RememberMeFilter.java))
- Browse books by categories and authors, view detailed book pages ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/src/java/servlets))
- Shopping cart and checkout simulation ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/CartPersistenceListener.java))
- Order tracking and order history ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/src/java/servlets))
- Admin panel for managing books, authors, genres, and orders ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/web))
- Email notifications for order confirmations ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/EmailService.java))

## Tech Stack
- Java EE (Servlets & JSP)
- Microsoft SQL Server 18 (mssql-JDBC driver) ([github.com](https://github.com/COSYBANANA63/iReedBooks-2))
- Apache Tomcat (or any servlet container)
- Ant (build.xml) ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/build.xml))
- NetBeans IDE project

## Prerequisites
- Java JDK 8 or higher
- Microsoft SQL Server 18
- Apache Tomcat or compatible servlet container
- (Optional) NetBeans IDE for development

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/COSYBANANA63/iReedBooks-2.git
   cd iReedBooks-2
   ```
2. Import into NetBeans or build with Ant:
   ```bash
   ant clean build
   ```
3. Set up the database:
   - Run the script at `DB/script.sql` to create the `BookStore` database and schema. ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/DB), [github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/DB/script.sql))
4. Configure the database connection in `src/java/util/DatabaseConnection.java` (update `JDBC_URL`). ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/DatabaseConnection.java))
5. Configure SMTP settings in `src/java/util/EmailService.java` (update `SMTP_HOST`, `SMTP_USER`, `SMTP_PASSWORD`). ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/EmailService.java))
6. Deploy the generated WAR (`dist/iReedBooks_2_Web_exploded`) to your servlet container.

## Usage
- Access the application at `http://localhost:8080/iReedBooks-2/`.
- Register a new user or log in at `/auth/login`.
- Browse books at `/bookList.jsp` or the homepage.
- Manage your cart at `/cart.jsp`.
- Checkout and view orders at `/orders.jsp`.
- Admin pages:
  - Login at `/admin-login.jsp`.
  - Dashboard at `/admin-dashboard.jsp`.

## Project Structure
```
📦iReedBooks-2
 ┣ DB/
 ┃ ┗ script.sql (database setup)              ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/DB), [github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/DB/script.sql))
 ┣ build.xml (Ant build script)             ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/build.xml))
 ┣ src/java/
 ┃ ┣ filters/ (session and security filters)([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/filters/SessionCheckFilter.java))
 ┃ ┣ servlets/ (core servlets handling requests) ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/src/java/servlets))
 ┃ ┗ util/ (utility classes: DB connection, email, listeners)([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/DatabaseConnection.java), [github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/src/java/util/EmailService.java))
 ┗ web/ (JSP pages and static assets)      ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/tree/master/web))
```

## Database Schema
The `DB/script.sql` file defines tables such as `Users`, `Book`, `Genre`, `Author`, `Transactions`, `RememberMeTokens`, and others, along with foreign key relationships and indexes. ([github.com](https://github.com/COSYBANANA63/iReedBooks-2/blob/master/DB/script.sql))

## Configuration
Update the following configuration files:
- `src/java/util/DatabaseConnection.java`: JDBC connection URL.
- `src/java/util/EmailService.java`: SMTP host, user, and password.
- `web/WEB-INF/web.xml`: servlet mappings and filter configurations.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
[MIT](LICENSE)


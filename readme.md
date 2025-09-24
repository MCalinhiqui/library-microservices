<h1 align="center" style="font-weight: bold;">Library Microservices</h1>

![Java 17+](https://img.shields.io/badge/Java-17+-red?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.x-6DB33F?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5+-brightgreen?logo=java&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18+-red?logo=apachemaven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?logo=swagger&logoColor=black)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-4.x-green?logo=java&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![TDD](https://img.shields.io/badge/TDD-Test%20Driven%20Development-orange?logo=testcafe&logoColor=white)

Este repositório contém o conjunto de microsserviços que compõem o **sistema de biblioteca**, desenvolvido com **Spring Boot** e arquitetura baseada em **microserviços**.

## 🚀 Microsserviços

- **Config Server** → Responsável pela centralização da configuração de todos os microsserviços.
- **Book Service** → Gerencia o ciclo de vida dos livros (cadastro, atualização, status, etc).
- **User Service** → Responsável pela gestão dos usuários da biblioteca.
- **Loan Service** → Controle dos empréstimos e devoluções de livros.

Cada microsserviço possui seu próprio `README.md` com instruções específicas.

## 📂 Estrutura do Repositório

```
library-microservices/
│── config-server/
│── book-service/
│── user-service/
│── loan-service/
│── README.md
└── .gitignore
```

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3+**
- **Spring Cloud Config**
- **Spring Data JPA / Hibernate**
- **PostgreSQL (via Docker)**
- **OpenAPI (Swagger)**
- **JUnit 5 & Mockito**
- **Docker & Docker Compose**

## ⚙️ Como Rodar

1. Clone este repositório:
   ```bash
   git clone https://github.com/MCalinhiqui/library-microservices.git
   cd library-microservices
   ```

2. Suba os containers do banco de dados no diretório `library-microservices/config-server/docker`:
   ```bash
   docker-compose up -d
   ```

3. Suba o **Config Server**:
   ```bash
   cd config-server
   ./mvnw spring-boot:run
   ```

4. Suba os demais microsserviços (em terminais separados):

   ```bash
   cd book-service
   ./mvnw spring-boot:run
   ```

   (repita para `user-service` e `loan-service`)

## 👨‍💻 Desenvolvido por
[Moisés Calinhiqui](https://github.com/MCalinhiqui)
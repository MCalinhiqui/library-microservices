# 📚 Book Service

<a href="https://github.com/MCalinhiqui">

![Java 17+](https://img.shields.io/badge/Java-17+-red?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.x-6DB33F?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Docker-316192?logo=postgresql&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5+-brightgreen?logo=java&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18+-red?logo=apachemaven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?logo=swagger&logoColor=black)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-4.x-green?logo=java&logoColor=white)
![TDD](https://img.shields.io/badge/TDD-Test%20Driven%20Development-orange?logo=testcafe&logoColor=white)

</a>

---

## 📝 Descrição

O **Book Service** é um microsserviço responsável pela gestão de livros dentro do ecossistema de bibliotecas.  
Ele permite o cadastro, consulta e atualização do estado de livros.

---

## ⚙️ Pré-requisitos

- **Docker** e **Docker Compose** instalados.
- **Java 17+** instalado.
- **Maven 3+** instalado.
- **IDE de sua preferência** instalada e configurada.
---

## 🐳 Subindo o banco de dados com Docker

Aceder ao diretório: `libraryMicrosservices/config-server/docker`

Executar:
```bash
docker-compose up -d
```
Isso irá criar um container PostgreSQL com usuário, senha e banco de dados definidos no `book-service.yml`.

---

## ▶️ Executando o projeto


```bash
mvn spring-boot:run
```

O serviço iniciará em:

```
http://localhost:8081
```

---

## 📖 Endpoints principais

## Endpoints - Book Service

| Método  | Endpoint                         | Descrição                                         |
|---------|----------------------------------|---------------------------------------------------|
| POST    | `/api/books`                     | Criar livro                                       |
| GET     | `/api/books`                     | Buscar todos os livros                            |
| GET     | `/api/books/{codigo}`            | Buscar livro por código                           |
| GET     | `/api/books?status=DISPONIVEL`   | Buscar livros por status                          |
| PATCH   | `/api/books/{codigo}`            | Atualização parcial (título, autor, ano, status)  |
| PATCH   | `/api/books/{codigo}/status`     | Atualizar apenas o status do livro                |
| DELETE  | `/api/books/{codigo}`            | Remover livro                                     |


---

## 📑 Documentação da API

Após subir a aplicação, acesse:

```
http://localhost:8081/swagger-ui.html
```

---

## 🧪 Testes

Este projeto segue a prática de **TDD (Test-Driven Development)**, utilizando:
- **JUnit 5** para criação de testes unitários
- **Mockito** para simulação de dependências

```bash
mvn test
```

---

## 📌 Observações

- O campo **status** segue o seguinte fluxo de transição:
    - `INDISPONIVEL → DISPONIVEL → ALUGADO → DISPONIVEL → INDISPONIVEL`
    - Nunca no sentido inverso.

---

<p align="center">
  Developed by <a href="https://github.com/MCalinhiqui">Moisés Calinhiqui</a><br/>
  <a href="https://github.com/MCalinhiqui">
    <img src="https://img.shields.io/badge/github-MCalinhiqui-blue?style=flat&logo=github">
  </a>
</p>

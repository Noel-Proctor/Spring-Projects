**Ecommerce Store**

This is a Spring Boot application to serve as a backend for an online Ecommerce website.

The applications consists of several REST APIs that allow the admin to manange products, categories and orders, and sellers and customers to upload products and place orders. 

**ENDPOINTS**

**1. Sign In - Method POST**
URL - /api/auth/signin

The request body should be a JSON object containing the following fields:
  username (String): The username of the user.
  password (String): The password of the user.

  Example Request Body:
  
  json
  {
      "username": "john_doe",
      "password": "password123"
  }
  
  Response
  The response will be a JSON object containing the user's information, including roles, along with a JWT token set as a cookie.
  
  Example Response:
  
  json
  {
      "id": 1,
      "username": "john_doe",
      "roles": ["ROLE_USER"]
  }

Status Codes
200 OK: The user was successfully authenticated.
401 Unauthorized: The credentials are incorrect.

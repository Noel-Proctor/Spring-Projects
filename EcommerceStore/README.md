**Ecommerce Store**

This is a Spring Boot application to serve as a backend for an online Ecommerce website. It is far from complete but I am uploading and updating as I go.

The applications consists of several REST APIs that allow the admin, users and sellers to manage products and categories, add items to their cart, and place orders. 

Improvements: 
- Add Tests.
- Improve validations.
- Improve security. While all endpoints require authentication (except Signup), role and authorisation validation has not yet been completed.

Upcoming enhancements: 
- I am currently working on a Front end for the application.

If you have any suggestions or would like to contact me, please do so at noelproctor@hotmail.co.uk.

***AUTHENTICATION ENDPOINTS***

**1. Sign In - /api/auth/signin - Method POST**

The request body should be a JSON object containing the following fields:

username (String): The username of the user.

password (String): The password of the user.

		Example URL:
		http://localhost:8080/api/auth/signin
	 
		Example Request Body:
		json{"username": "john_doe","password": "password123"}
		  
		Example Response:
		json{"id": 1,"username": "john_doe","roles": ["ROLE_USER"}


**2. Sign Up - /api/auth/signup - Method: POST**

The request body should be a JSON object containing the following fields:

  username (String): The desired username for the new user.

  password (String): The desired password for the new user.
    
  firstName (String): The first name of the user.
    
  lastName (String): The last name of the user.
    
  email (String): The email address of the user.
    
  roles (Set<String>): The roles assigned to the user (optional).


		Example URL:
		http://localhost:8080/api/auth/signup
	
		Example Request Body:  
		
			json{
			"username": "john_doe",
			"password": "password123",
			"firstName": "John",
			"lastName": "Doe",
			"email": "john.doe@example.com",
			"roles": ["ROLE_USER"]
				}
		
		Example Response:
		
		json {"message": "User registered successfully!"}


**3. Sign Out - /api/auth/signout - Method: Post**

Description
This endpoint allows the user to sign out and clears the JWT cookie.

Response
The response will be a JSON object containing a message indicating the sign-out status.

		Example URL:
		http://localhost:8080/api/auth/signout
		
		Example Response:
		json{"message": "You have been signed out.}
		
		Status Codes
		200 OK: The user was successfully signed out.




**4. Get User Details - /api/auth/user - Method -GET**

Description
This endpoint retrieves the details of the currently authenticated user.

Response
The response will be a JSON object containing the user's information, including roles.

		Example URL:
		http://localhost:8080/api/auth/user
	
		Example Response:	
		json
		{
		    "id": 1,
		    "username": "john_doe",
		    "roles": ["ROLE_USER"]
		}
	
		Status Codes
		200 OK: The request was successful.


***CATEGORY MAINTENENCE ENDPOINTS***

**1. Create Category - /api/public/categories - Method: POST**

The request body should be a JSON object containing the following fields:

	name (String): The name of the category.

		Example URL: http://localhost:8080/api/public/categories
	
		Example Request Body:
		
		json
		{
		    "name": "Technology",  
		}
		
		Example Response:
		
		json
		{
			 "id": 3,
			 "name": "Technology",
			 
		}



**2. Get All Categories - /api/public/categories - Method: GET**

The endpoint retrieves all categories with pagination and sorting options.

Query Parameters:

page (Integer, optional): The page number to retrieve.

pageSize (Integer, optional): The number of items per page.

sortBy (String, optional): The field to sort by.

sortOrder (String, optional): The order of sorting (ASC or DESC).

		Example URL: http://localhost:8080/api/public/categories


		Example Response: 
		
		json
		{
		    "content": [
		        {
		            "categoryId": 4,
		            "categoryName": "Cooking"
		        },
		        {
		            "categoryId": 2,
		            "categoryName": "Fishing"
		        },
		        {
		            "categoryId": 3,
		            "categoryName": "Sports"
		        },
		        {
		            "categoryId": 5,
		            "categoryName": "Technology"
		        }
		    ],
		    "pageNumber": 0,
		    "pageSize": 5,
		    "totalElements": 4,
		    "totalPages": 1,
		    "lastPage": true
		}


**3. Delete Category - /api/admin/categories/{categoryId} - Method: DELETE**

Path Parameters:
 categoryId (Long): The ID of the category to delete.

	Example Request URL: http://localhost:8080/api/admin/categories/1
		
	Example Response:
		
	json
	{
		"id": 1,
		"name": "Electronics",
	}

**4. Update Category - /api/public/categories - Method: PUT**

The request body should be a JSON object containing the following fields:

id (Long): The ID of the category.

name (String): The name of the category.

description (String): A brief description of the category.


	Example Request: http://localhost:8080/api/admin/categories/6
		
	Example Request Body:
			
		json
		{
			"id": 2,
			"name": "Books",
			"description": "All genres of books"
		}
	Example Response:
		
		json
		{
			"id": 2,
			"name": "Books",
			"description": "All genres of books"
		}



***PRODUCT MAINTAINENCE ENDPOINTS***

**1. Add Product to Category -/admin/categories/product- Method: POST**
The request body should be a JSON object containing the product details.

Request Body
productName (String): The name of the product.

description (String): A brief description of the product.

quantity (Integer): The available quantity of the product.

price (String): The price of the product.

discount (String): The discount percentage for the product (if applicable).

	Example Request: http://localhost:8080/api/admin/categories/product
 
 	Example Request Body:
	
	json
	{
	    "productName": "Laptop",
	    "description": "Top of the range.",
	    "quantity": 10,
	    "price": "1500.99",
	    "discount": "5"
	}
	Response
	The response will be a JSON object containing the added product details, including its newly assigned ID.
	
	Example Response:
	
	json
	{
	    "id": 1,
	    "productName": "Laptop",
	    "description": "Top of the range.",
	    "quantity": 10,
	    "price": "1500.99",
	    "discount": "5" 
	}


 
**2. Get Products by Category - /public/categories/{categoryId}/products - Method: GET**

This endpoint retrieves products based on the specified category, with pagination and sorting options.

Path Parameters:

categoryId (Long): The ID of the category to retrieve products for.

Query Parameters:

pageNumber (Integer, optional): The page number to retrieve. Default value is provided by AppConstants.PAGE_NUMBER.

pageSize (Integer, optional): The number of items per page. Default value is provided by AppConstants.PAGE_SIZE.

direction (String, optional): The direction of sorting (ASC or DESC). Default value is provided by AppConstants.DIRECTION.

orderBy (String, optional): The field to sort by. Default value is provided by AppConstants.PRODUCT_SORT_BY.

	Example Request URL:
	http://localhost:8080/api/public/categories/1/products?pageNumber=1&pageSize=10&direction=ASC&orderBy=name
	
	Example Response:

	json
	{
	    "content": [
	        {
	            "productId": 5,
	            "productName": "12Ft Beach Fishing Rod",
	            "description": "Catch the big one",
	            "image": "default.png",
	            "quantity": 19,
	            "price": 160.0,
	            "discount": 5.0,
	            "specialPrice": 152.0,
	            "categoryId": 2
	        },
	        {
	            "productId": 6,
	            "productName": "8Ft Lure Fishing Rod",
	            "description": "Great for Bass and Pollock",
	            "image": "default.png",
	            "quantity": 20,
	            "price": 82.99,
	            "discount": 0.0,
	            "specialPrice": 82.99,
	            "categoryId": 2
	        }
	    ],
	    "pageNumber": 0,
	    "pageSize": 2,
	    "totalElements": 2,
	    "totalPages": 4,
	    "lastPage": false
	}


**3. Update Product -/admin/products/{productId}- Method: PUT**

This endpoint updates the details of a specific product.

Path Parameters:

productId (Long): The ID of the product to update.

Request Body: The request body should be a JSON object containing the updated product details.

productName (String): The name of the product.

description (String): A brief description of the product.

quantity (Integer): The available quantity of the product.

price (String): The price of the product.

discount (String): The discount percentage for the product (if applicable).

	Example Request URL: http://localhost:8080/api/admin/products/6
 
 	Example Request Body:
	
	json
	{
	     "productName": "8 ft fishing rod",
            "description": "For catching even better fish",
            "image": "default.png",
            "quantity": 3,
            "price": 200.0,
            "discount": 20.0,
            "special_Price": 75.0,
            "categoryName": "Fishing"
	}
 
	Example Response:
	
	json
	{
	    "productId": 6,
	    "productName": "8 ft fishing rod",
	    "description": "For catching even better fish",
	    "image": "default.png",
	    "quantity": 3,
	    "price": 200.0,
	    "discount": 20.0,
	    "specialPrice": 160.0,
	    "categoryId": 2
	}


**4. Update Product Image - /admin/products/{productId}/image -Method: PUT** 

This endpoint updates the image of a specific product.

Path Parameters:

productId (Long): The ID of the product to update the image for.

Request Body: The request body should contain the image file as MultipartFile.

	Example Request: http://localhost:8080/api/admin/products/6/image
 
 	Example Request Body:

	MultipartFile: [binary image data]

	Example Response:

	json
	{
	    "productId": 6,
	    "productName": "8 ft fishing rod",
	    "description": "For catching even better fish",
	    "image": "70dae54e-eba8-405b-84f1-5d07634e5ebd.jpg",
	    "quantity": 3,
	    "price": 200.0,
	    "discount": 20.0,
	    "specialPrice": 160.0,
	    "categoryId": 2
	}

**5. Delete Product - /admin/products/{productId} - Method: DELETE**

This endpoint deletes a specific product.

Path Parameters:

productId (Long): The ID of the product to delete.

	Example Request URL: http://localhost:8080/api/admin/products/5

	Example Response:

	json
	{
	"message": "Product with Id 4 has been deleted"
	}


***CART ENDPOINTS***

**1. Add Product to Cart -/carts/products/{productId}/quantity/{quantity} -Method: POST**

This endpoint adds a specified quantity of a product to the cart of the currently signed in user.

Path Parameters
productId (Long): The ID of the product to add to the cart.

quantity (Integer): The quantity of the product to add to the cart.

	Example Request URL: http://localhost:8080/api/carts/products/7/quantity/1

	Example Response:

	json
	{
	    "cartId": 2,
	    "products": [
	        {
	            "productId": 7,
	            "productName": "Laptop",
	            "description": "Game and code all day.",
	            "image": "default.png",
	            "quantity": 1,
	            "price": 1182.99,
	            "discount": 0.0,
	            "specialPrice": 1182.99,
	            "categoryId": 5
	        }
	    ],
	    "totalPrice": 1182.99
	}
    
**2. Get User Cart - /carts/user/cart - Method: GET**

This endpoint retrieves the current authenticated user's cart details.

Response
The response will be a JSON object containing the user's cart details, including the items in the cart, total quantity, and total price.

	Example Request: http://localhost:8080/api/carts/user/cart
 
 	Example Response:

	json
	{
	    "cartId": 2,
	    "products": [
	        {
	            "productId": 7,
	            "productName": "Laptop",
	            "description": "Game and code all day.",
	            "image": "default.png",
	            "quantity": 1,
	            "price": 1182.99,
	            "discount": 0.0,
	            "specialPrice": 1182.99,
	            "categoryId": 5
	        }
	    ],
	    "totalPrice": 1182.99
	}



 **3. Update Cart Product -/carts/products/{productId}/update/{operation} - Method: POST**
 
This endpoint updates the quantity of a specific product in the user's cart based on the operation specified.

Path Parameters
productId (Long): The ID of the product to update in the cart.

operation (String): The operation to perform. Can be "delete" to decrease the quantity or anything else to increase the quantity.

	Example Request URL:http://localhost:8080/api/carts/products/5/update/add
 
	Example Request URL: http://localhost:8080/api/carts/products/5/update/delete
 
	Example Response:
	
	json
	{
	    "cartId": 2,
	    "products": [
	        {
	            "productId": 7,
	            "productName": "Laptop",
	            "description": "Game and code all day.",
	            "image": "default.png",
	            "quantity": 1,
	            "price": 1182.99,
	            "discount": 0.0,
	            "specialPrice": 1182.99,
	            "categoryId": 5
	        }
	    ],
	    "totalPrice": 1182.99
	}


 **4. Delete Product from Cart -/carts/{cartId}/product/{productId}- Method: DELETE**

This endpoint deletes a specific product from a user's cart.

Path Parameters
productId (Long): The ID of the product to delete from the cart.

cartId (Long): The ID of the cart from which the product is to be deleted.

	Example Request URL: http://localhost:8080/api/carts/1/product/7

	Example Response:

	json
	{
	    "status": "Product 'Laptop' removed from cart."
	}

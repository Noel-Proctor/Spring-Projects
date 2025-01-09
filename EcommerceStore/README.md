**Ecommerce Store**

This is a Spring Boot application to serve as a backend for an online Ecommerce website. It is far from complete but I am uploading and updating as I go in the hope someone might **give me a job :D**

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





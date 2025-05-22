# transaction-management

1) Access H2 Console:http://localhost:8080/h2-console.
	Use JDBC URL: jdbc:h2:mem:testdb,
	username: sa,
	password: (empty).

2) Register a User:
	Endpoint: POST /api/auth/register
	URL: http://localhost:8080/api/auth/register
	Body (JSON):
	{
	username: testuser,
	password: password123
	}
	Response: No content (HTTP 200 if successful).
	
3) Login to Get JWT:
	Endpoint: POST /api/auth/login
	URL: http://localhost:8080/api/auth/login
	Body (JSON):
	{
	  username: testuser,
	  password: password123
	}
	Response: JWT token (e.g., eyJhbGciOiJIUzI1NiIs...).
	
4) Retrieve Protected Transaction APIs (GET /api/transactions):
	a) Basic Retrieval:
		GET http://localhost:8080/api/transactions?page=1
			
	b) Search by Customer ID:
		GET http://localhost:8080/api/transactions?customerId=222&page=1
				
		Expected: HTTP 200 OK with paginated transactions (e.g., content array with records).
				
	c) Search by Account Number:
		GET http://localhost:8080/api/transactions?accountNumber=8872838299&page=1
		
		Expected: Records with accountNumber=8872838299.
				
	d) Search by Description:
		GET http://localhost:8080/api/transactions?description=Payment&page=1
		
		Expected: Records with description containing Payment.
				
	e) Pagination:
		GET http://localhost:8080/api/transactions?page=1
		
		Expected: First page with records.
		
5) Update Description (PUT /api/transactions/{id}):
	a) Single Update:
		PUT http://localhost:8080/api/transactions/8872838299 
		
		{description:Updated Payment}
		
		Expected: HTTP 200 OK with updated transaction.
				
	b) Concurrent Update Test:
		Open two Postman instances and send Simultaneously:
		PUT http://localhost:8080/api/transactions/8872838299
		{description:Update 1}
		PUT http://localhost:8080/api/transactions/6872838260
		{description:Update 2}
		
		Expected: One request succeeds, the other fails with HTTP 500 and message Concurrent update detected. Please retry..
				
6) Test Authentication:
	a) Send GET without JWT:
		GET http://localhost:8080/api/transactions?accountNumber=8872838299
		
		Expected: HTTP 403 Forbidden
			
	b) Send GET with invalid JWT:
		GET http://localhost:8080/api/transactions?accountNumber=8872838299
		
		Authorization: Bearer invalid_token
		
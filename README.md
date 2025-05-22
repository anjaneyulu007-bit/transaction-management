# transaction-management

1) Access H2 Console:http://localhost:8080/h2-console.<br>
   	Use JDBC URL: jdbc:h2:mem:testdb,<br>
   	username: sa,<br>
   	password: (empty).<br><br>
   
3) Register a User:<br>
	Endpoint: POST /api/auth/register<br>
	URL: http://localhost:8080/api/auth/register<br>
	Body (JSON):<br>
	{<br>
	"username" : "testuser",<br>
	"password" : "password123"<br>
	}<br>
	Response: No content (HTTP 200 if successful).<br><br>
	
4) Login to Get JWT:<br>
	Endpoint: POST /api/auth/login<br>
	URL: http://localhost:8080/api/auth/login <br>
	Body (JSON):<br>
	{<br>
	  "username": "testuser",<br>
	  "password: "password123"<br>
	}
	Response: JWT token (e.g., eyJhbGciOiJIUzI1NiIs...).<br><br>
	
5) Retrieve Protected Transaction APIs (GET /api/transactions):<br>
	a) Basic Retrieval:<br>
		GET http://localhost:8080/api/transactions?page=1  <br><br>
			
	b) Search by Customer ID:<br>
		GET http://localhost:8080/api/transactions?customerId=222&page=1  <br>
				
		Expected: HTTP 200 OK with paginated transactions (e.g., content array with records).<br><br>
				
	c) Search by Account Number:<br>
		GET http://localhost:8080/api/transactions?accountNumber=8872838299&page=1  <br>
		
		Expected: Records with accountNumber=8872838299.<br><br>
				
	d) Search by Description:<br>
		GET http://localhost:8080/api/transactions?description=Payment&page=1  <br>
		
		Expected: Records with description containing Payment.<br><br>
				
	e) Pagination:<br>
		GET http://localhost:8080/api/transactions?page=1<br>
		
		Expected: First page with records.<br><br>
		
6) Update Description (PUT /api/transactions/{id}):<br>
	a) Single Update:<br>
		PUT http://localhost:8080/api/transactions/8872838299 <br>
		
		{"description" : "Updated Payment"}<br>
		
		Expected: HTTP 200 OK with updated transaction.<br><br>
				
	b) Concurrent Update Test:<br>
		Open two Postman instances and send Simultaneously:<br>
		PUT http://localhost:8080/api/transactions/8872838299 <br>
		{description:Update 1}<br>
		PUT http://localhost:8080/api/transactions/6872838260 <br>
		{description:Update 2}<br>
		
		Expected: One request succeeds, the other fails with HTTP 500 and message Concurrent update detected. Please retry..  <br><br>
				
7) Test Authentication:<br>
	a) Send GET without JWT:<br>
		GET http://localhost:8080/api/transactions?accountNumber=8872838299 <br>
		
		Expected: HTTP 403 Forbidden  <br><br>
			
	b) Send GET with invalid JWT: <br>
		GET http://localhost:8080/api/transactions?accountNumber=8872838299  <br>
		
		Authorization: Bearer invalid_token <br>
		

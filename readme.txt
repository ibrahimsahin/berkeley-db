In this experiment,  you are expected to develop a document-oriented database that can be queried over the 
HTTP protocol. You are going to write your code in Java and use some external libraries that will aid you in the 
development process.
The database you are going to develop should accept JSON queries over the HTTP protocol. For this purpose, 
you  will  embed  Jetty  in  your  application  to  run  an  HTTP  server  on  port  8080  that  listens  and  responds  to 
incoming requests.
In the database part of the program, you will be dealing with key-data pairs. To store and retrieve data, you will 
utilize Berkeley DB which stores data in its internal B-tree. The key will be the element that is used to retrieve 
the actual data.
Your program should be able to handle the incoming requests, perform the necessary database queries, and 
return the result to the client.  The queries and results will be JSON objects which will have specific attributes 
according to their type.
There can be  five  types of queries. The type of the query can be determined from the  action  attribute of the 
JSON query object, which can have one of the values “insert”, “select”, “update”, “delete” and “truncate”.
After evaluating the query, your program should return a JSON object to the client over HTTP,  along with  an 
HTTP status code
5
that represents the status of the operation.
If a query is not a valid JSON object, is  missing a required attribute,  or one of its attributes corresponds to an 
invalid  value  (“action”  is  not  one  of  the  predefined  values,  “data”  is  not  a  JSON  object,  …),  an  empty  JSON 
object should be returned with the HTTP status code 400 (bad request)
If the database with the given name does not exist for any query type   except insert, or no  record  can be found 
with the given key for select and delete queries, an empty JSON object should be returned with the HTTP status 
code 404 (not found).
Additional behavior of the query types are as follows:
Insert: 
  The request will contain attributes db, key and data.
  If the database with the given name does not exist, create the database.
  If a  record  with the given key already exists in the database, return HTTP status code 409 (conflict)  with a 
JSON object having key and data attributes that contain the key and data of the conflicting record from the 
database.
  Insert the  record  into the database.  Upon successful operation, return HTTP status code 200 with a JSON 
object having key and data attributes that contain the inserted key and data.
Select: 
  The request will contain attributes db and key.
  Select  the  record  with  the  given  key  from  the  database.  Upon  successful  operation,  return  HTTP  status 
code 200 with a JSON object  having key  and  data  attributes that contain the key and data of the selected 
record. 
Update: 
  The request will contain attributes db, key and data.
  Update the  record  with the given key from the database. Upon successful operation, return HTTP status 
code 200 with a JSON object  having  key  and  data  attributes that contain the key and data of the  record 
before the update.
Delete: 
  The request will contain attributes db and key.
  Delete  the  record  with the given key from the database.  Upon successful operation, return HTTP status 
code 200 with a JSON object  having  key  and  data  attributes that contain the  key and data of the  deleted 
record.
Truncate: 
  The request will contain the attribute db.
  Delete all records  from the database  with the given name. Upon successful operation, return HTTP status 
code 200 with a JSON object  having deleted  attribute  that contains  the  number of records in the database 
before truncation.
Another HTTP status code you may encounter when testing is code 500 (internal server error). Jetty returns a 
response with this status code when an unhandled exception occurs while it handles an HTTP request
{
"action": "insert",
"data": {
"address": {
"no": "16",
"street": "Walnut"
},
"age": 34,
"children": null,
"married": false,
"name": "Johnny"
},
"db": "mydb",
"key": "65237224819"
}
A JSON query that is expected to perform an insertion.
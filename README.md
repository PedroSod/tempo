The application has a script package to fill the users, teams and defaults roles into DB.
The user fill endpoint should be the first one, because I use the user id as reference in teams.
So this way we can solve the issue that we can have if some data is deleted.
I have tried to write only necessary tests, to validate only extremely necessary features.

Pagination and Sorting is some improvement in the Team or User services that I suggest.

To run the application using docker run the following command:

mvn clean install && docker-compose up --build

JSON Postman collection link
https://www.getpostman.com/collections/8e20542480ad044917de




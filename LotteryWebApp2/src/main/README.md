# OVERVIEW:
----------------------------------
This is the basic drawing lots program with admin and public interfaces. From the admin side, it enables to view all of the users' accounts by print a table with personal details (first name, last name, email address, telephone number, username, and hashed password). On the other hand, every public member can draw 6 numbers in the range 0-60 (either manually or let the program do so by clicking the `Get User Numbers` button). There is no constrain in terms of how many times, the user can draw his combinations of numbers. Once he wants to check if among all of his submitted numbers there is a match, he clicks on the `Check Match!` button, which gives him a response in form of a message.


# CONFIGURATION:
-----------------------------------------------

### In order the program works correctly, the following libraries should be added: 
1. jstl-1.2  - (Due to my use of JSTL  statements inside `accounts.jsp`)
2. mysql: mysql-connector-java:8.0.212

_(both files are attached to the project and are located in `librariesToInstall` directory)_

### You should change the relative file paths to your absolute paths in the following classes: 
* `GetUserNumbers` class (line 73) 
* `AddUserNumbers` class (line 84)

### In order to set up MySQL database put the following credentials:
* **user**: user 
* **password**: sesame 

* Note: You may have to change the ports, depending on which your Tomcat and MySQL databases are running locally. If you wish to run MySQL and Tomcat from docker, you should change all of the connection present in project to `jdbc:mysql://db:3306/lottery`

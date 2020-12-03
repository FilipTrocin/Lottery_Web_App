# CONFIGURATION:

### In order the program works correctly, the following libraries should be added: 
1. jstl-1.2  - (Due to my use of JSTL  statements inside `accounts.jsp`)
2. mysql: mysql-connector-java:8.0.212

_(both files are attached to the project and are located in `librariesToInstall` directory)_

### You should change the relative file paths to your absolute paths in the following classes: 
* `GetUserNumbers` class (line 73) 
* `AddUserNumbers` class (line 84)

### In order to set up MySQL database put the following credentials:
**user**: user 
**password**: sesame 

* Note: You may have to change the ports, depending on which your Tomcat and MySQL databases are running locally. If you wish to run MySQL and Tomcat from docker, you should change all of the connection present in project to `jdbc:mysql://db:3306/lottery`

### I

<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>

<h1>Home Page</h1>

<form action="CreateAccount" method="post" name="CreateForm" onsubmit="return(validation());">
    <label for="firstname">First name:</label><br>
    <input type="text" id="firstname" name="firstname" title="user's first name" required=""><br>
    <label for="lastname">Last name:</label><br>
    <input type="text" id="lastname" name="lastname" title="user's last name" required=""><br>
    <label for="email">Email:</label><br>
    <input type="text" id="email" name="email" title="user's email" placeholder="joebloggs@email.com"><br>
    <label for="phone">Telephone Number:</label><br>
    <input type="tel" id="phone" name="phone" title="user's phone number" placeholder="44-0191-1234567"
           required="" ><br>
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username" required=""><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" title="8 to 15 characters with at least 1 uppercase,
     1 lowercase character and at least 1 digit" required=""><br><br>
    <input type="submit" value="Submit">

<script type="text/javascript">
    function validation(){
        const bigLetter = /.*[A-Z]/
        const smallLetter = /.*[a-z]/
        const digit = /.*\d/
        const phoneFormat = /^\d{2}-\d{4}-\d{7}$/;
        const mailFormat = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;

        // var password = document.getElementById('password')
        // var phone = document.getElementById('phone')
        // var email = document.getElementById('email')

        var x = document.getElementById('password').value
        var y = document.getElementById('phone').value
        var z = document.getElementById('email').value


        if (x > 15 || x < 8 || !x.match(digit) || !x.match(smallLetter) || !x.match(bigLetter)){
            // Reporting invalid input
            // password.setCustomValidity('password must be 8-15 characters long, and contain at least 1 uppercase, 1 lowercase ' +
            //     'character and 1 digit')
            // Display msg in form of bubble tp the user
            // password.reportValidity()
            alert('Password must be 8-15 characters long, and contain at least 1 uppercase, 1 lowercase character and 1 digit')
            document.CreateForm.password.focus();
            return false
        }

        if (!y.match(phoneFormat)){
            // phone.setCustomValidity('Wrong phone format')
            // phone.reportValidity()
            alert('Wrong phone format');
            document.CreateForm.phone.focus();
            return false
        }

        if (!z.match(mailFormat)){
            alert('Email address is in the wrong format');
            document.CreateForm.email.focus();
            return false
        }
        return true
    }



    // function passwordValidation(){
    //     var x;
    //     const bigLetter = /.*[A-Z]/
    //     const smallLetter = /.*[a-z]/
    //     const digit = /.*\d/
    //     const pass = document.getElementById('password')
    //
    //     // Assigning value entered by user in 'password' input field
    //     x = document.getElementById('password').value;
    //
    //     if (x > 15 || x < 8 || !x.match(digit) || !x.match(smallLetter) || !x.match(bigLetter)){
    //         pass.setCustomValidity('password must be 8-15 characters long, and contain at least 1 uppercase, 1 lowercase' +
    //             ' character and 1 digit')
    //         document.getElementById('password').value = null;
    //     }
    //     else {
    //         document.getElementById('password').value = x
    //     }
    // }
    //
    // function phoneValidation(){
    //     var x;
    //     const phoneFormat = /^\d{2}-\d{4}-\d{7}$/;
    //     const phone = document.getElementById('phone')
    //
    //     x = document.getElementById('phone').value;
    //
    //     if (!x.match(phoneFormat)){
    //         phone.setCustomValidity('Phone number is not in a correct format')
    //     }
    //     else {
    //         document.getElementById('phone').value = x
    //     }
    //
    // }

</script>

</form>

<form action="LogIn" method="post">
    <label for="username">Username:</label><br>
    <input type="text" id="usernameLogin" name="username" required=""><br>
    <label for="password">Password:</label><br>
    <input type="password" id="passwordLogin" name="password" title="8 to 15 characters with at least 1 uppercase,
     1 lowercase character and at least 1 digit" required=""><br><br>
    <input type="submit" value="Log In">
</form>

</body>
</html>
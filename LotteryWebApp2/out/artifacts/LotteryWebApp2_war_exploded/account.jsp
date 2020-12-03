<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
</head>
<body>

<h1>User Account</h1>

<p><%= request.getAttribute("message") %></p><br>
<p><strong><%= "Your Firstname: " %></strong> <%=request.getSession().getAttribute("uFirstname")%></p>
<p><strong><%= "Your Lastname: "%></strong> <%=request.getSession().getAttribute("uLastname")%></p>
<p><strong><%= "Your Email: "%></strong> <%=request.getSession().getAttribute("uEmail")%></p>
<p><strong><%= "Your username: "%></strong> <%=request.getSession().getAttribute("uUsername")%></p><br><br>
<p><%=request.getSession()%></p>
<label>Enter 6 integers from the range 0-60</label>
<form action="AddUserNumbers" method="post" id="numcombination">
    <label>
        <input type="number" id="num0" name="digit" min="0" max="60">
        <input type="number" id="num1" name="digit" min="0" max="60">
        <input type="number" id="num2" name="digit" min="0" max="60">
        <input type="number" id="num3" name="digit" min="0" max="60">
        <input type="number" id="num4" name="digit" min="0" max="60">
        <input type="number" id="num5" name="digit" min="0" max="60">
    </label>
    <input type="button" value="Generate Numbers" onclick="range(0, 61)">
    <input type="button" value="Submit" onclick='document.forms["numcombination"].submit();'><br>

</form>

<form action="GetUserNumbers" method="post">
    <input type="submit" value="Get Draws">
</form>


<form action="UserLogin" method="post">
    <input type="submit" value="Get All Data">
</form>

<a href="index.jsp">Home Page</a>

<script>
    let arr = [];

    function range(start, end) {
        for (let i = 0; i < 6; i++) {
             arr[i] = Math.floor(Math.random()*(end - start) + start);
             var elementID = ("num%d")
             document.getElementById(elementID.replace("%d", i.toString())).value = arr[i];
        }
    }

</script>

</body>
</html>

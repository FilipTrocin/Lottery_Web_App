<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Account</title>
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
    </script>
        </head>
<body>

<h1>User Account</h1>

<p><%= request.getAttribute("message") %></p><br>
<p><strong><%= "Your Firstname: " %></strong> <%=request.getSession().getAttribute("uFirstname")%></p>
<p><strong><%= "Your Lastname: "%></strong> <%=request.getSession().getAttribute("uLastname")%></p>
<p><strong><%= "Your Email: "%></strong> <%=request.getSession().getAttribute("uEmail")%></p>
<p><strong><%= "Your username: "%></strong> <%=request.getSession().getAttribute("uUsername")%></p><br><br>

<h3>Select 6 lottery draw numbers</h3>
<form action="AddUserNumbers" method="post" id="numcombination">
    <div class="nums">
        <label>
            <input type="number" id="num0" name="digit" min="0" max="60">
            <input type="number" id="num1" name="digit" min="0" max="60">
            <input type="number" id="num2" name="digit" min="0" max="60">
            <input type="number" id="num3" name="digit" min="0" max="60">
            <input type="number" id="num4" name="digit" min="0" max="60">
            <input type="number" id="num5" name="digit" min="0" max="60">
        </label>
    </div>
        <input type="button" value="Generate Numbers" onclick="range(0, 61)">
        <input type="submit" id="submitForm" value="Submit"><br>
</form>

<c:choose>
    <c:when test="${not empty draws}">
        <c:forEach var="dr" items="${draws}" varStatus="status">
            <strong style="color: red"><c:out value="${'['} ${status.index} ${']'}" /></strong>
            <c:out value="${dr}"/><br>
        </c:forEach>
    </c:when>
</c:choose>

<div>
    <form action="GetUserNumbers" method="post">
        <button id="getDraws" type="submit">Get Draws</button>
    </form>
    <form action="CheckMatch" method="post">
        <input type="submit" value="Check Match!">
    </form>
</div>

<div>
    <a href="index.jsp">Home Page</a>
</div>

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

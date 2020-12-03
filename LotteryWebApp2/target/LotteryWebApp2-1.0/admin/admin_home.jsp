
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
    </script>
    <script>
        $(document).ready(function (){
            $('#table').click(function (){
                $("p").show(1000)
            })
        })
    </script>
    <style>
        .show-button{
            display: block;
            width: 100%;
            background-color: #006400;
            color: white;
            padding: 15px 30px;
            font-size: 16px;
            text-align: center;
        }
        .show-button:hover{
            background-color: #ddd;
            color: black;
        }
        h1{
            text-align: center;
        }
    </style>
    <title>Title</title>
</head>
<body>

<h1>Admin Panel</h1>

<input type="submit" value="View all users" id="table" class="show-button">

<p hidden><%= request.getAttribute("data")%></p>

<div>
    <a href="../index.jsp">Home Page</a>
</div>

</body>
</html>

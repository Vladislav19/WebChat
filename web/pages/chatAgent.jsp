<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 19.03.2018
  Time: 4:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script type="text/javascript">
        function doAjax() {
            var inputText = $("#message").val();
            var inputLogin = $("#login").html() ;
            $.ajax({
                url : 'sendMessage',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                data : ({
                    text: inputText,
                    login: inputLogin
                }),
                success: function (data) {
                    var result = data.text
                    $("#response").text(result);
                }
            });
        }
    </script>

    <script type="text/javascript">
        window.onload = function findFreeAgent() {
            var inputText = $("#login").html() ;
            $.ajax({
                url : 'waitClient',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                data : ({
                    text: inputText
                }),
                success: function (data) {
                    var result = data.text
                    $("#response").text(result);
                    takeMessage();
                }
            });
        }
    </script>


    <script type="text/javascript">
        function takeMessage() {
            var inputText = $("#login").html() ;
            $.ajax({
                url : 'giveMessageAgent',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                data : ({
                    text: inputText
                }),
                success: function (data) {
                    var result = data.text
                    $("#response").text(result);
                    takeMessage();
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <p id="login" ><sec:authentication   property="principal.username" /></p>
    <input class="col-md-8" type="text" id="message" name="message" placeholder="Type you message">
    <input type="button" value="Send" onclick="doAjax()">
    <p style="align-content: center"> Wait a free agent </p>
    <p id="response"></p>
</div>
</body>
</html>

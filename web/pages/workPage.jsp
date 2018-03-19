<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 07.03.2018
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Spring Security</title>

  <link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet">
  <link href="<c:url value="/pages/css/jumbotron-narrow.css" />" rel="stylesheet">

  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

</head>

<body>

<div class="container">

  <div class="jumbotron" style="margin-top: 20px;">
    <h1>Chat</h1>
    <p class="lead">
      Этот сервис предоставляет возможность получить техподдержку в любое время суток
    </p>
    <sec:authorize access="hasRole('CLIENT')">
      Вы вошли в систему в качестве клиента.
    </sec:authorize>
    <sec:authorize access="hasRole('AGENT')">
      Вы вошли в систему в качестве агента.
    </sec:authorize>
  </div>

  <div align="center" class="footer">
    <p>© Vlados 2018</p>
  </div>

</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 18.03.2018
  Time: 13:22
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
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>

</head>

<body>

<div class="container">

  <div class="jumbotron" style="margin-top: 20px;">
    <h1>Chat</h1>
    <p class="lead">
      Этот сервис предоставляет возможность получить техподдержку в любое время суток
    </p>

    <sec:authorize access="!isAuthenticated()">
      <p>
        <a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a>
        <a class="btn btn-lg btn-success" href="<c:url value="/reg" />" role="button">Зарегистрироваться</a>
      </p>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
      <p>Ваш логин: <b id="login"><sec:authentication property="principal.username" /></b></p>
      <p>Ваша роль: <sec:authorize access="hasRole('CLIENT')">Client</sec:authorize>
        <sec:authorize access="hasRole('AGENT')">Agent</sec:authorize></p>

      <sec:authorize access="hasRole('CLIENT')">
        <p>
          <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a>
          <a class="btn btn-lg btn-success" href="<c:url value="/reg" />" role="button">Зарегистрироваться</a>
          <a class="btn btn-lg btn-success" href="<c:url value="/chatClient" />"  role="button">Начать чат</a>
        </p>
      </sec:authorize>

      <sec:authorize access="hasRole('AGENT')">
        <p>
          <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a>
          <a class="btn btn-lg btn-success" href="<c:url value="/reg" />" role="button">Зарегистрироваться</a>
          <a class="btn btn-lg btn-success" href="<c:url value="/chatAgent" />"  role="button">Начать чат</a>
        </p>
      </sec:authorize>

    </sec:authorize>
  </div>

  <div align="center" class="footer">
    <p>© Vlados 2018</p>
  </div>

</div>
</body>
</html>

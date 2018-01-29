<%--
  Created by IntelliJ IDEA.
  User: choi
  Date: 2018. 1. 29.
  Time: AM 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
<html>
  <head>
    <title>회원가입</title>
  </head>
  <body>
      <form action="/user/sign-up" method="post">
          <div class="form-group">
              <label for="id">아이디:</label>
              <input type="text" class="form-control" id="id">
          </div>
          <div class="form-group">
              <label for="pw">비밀번호:</label>
              <input type="password" class="form-control" id="pw">
          </div>
          <div class="form-group">
              <label for="pw2">비밀번호 재입력:</label>
              <input type="password" class="form-control" id="pw2">
          </div>

          <button type="submit" class="btn btn-primary">가입하기</button>
      </form>

      <script src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script>
      <script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
  </body>
</html>

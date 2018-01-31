<%--
  Created by IntelliJ IDEA.
  User: choi
  Date: 2018. 1. 29.
  Time: PM 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <div class="container">
      <form action="" method="post" id="login_user_info">
        <div class="form-group">
          <label for="id">아이디:</label>
          <input type="text" class="form-control" id="id">
        </div>
        <div class="form-group">
          <label for="pwd">비밀번호:</label>
          <input type="password" class="form-control" id="pwd">
        </div>

        <div class="form-check">
          <label class="form-check-label">
            <input class="form-check-input" type="checkbox"> 아이디 기억하기
          </label>
        </div>

        <button type="submit" class="btn btn-primary" id="login_submit_button">로그인</button>
      </form>
    </div>

    <script src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#login_submit_button').on('click', function () {
                $.ajax({
                    url: "/user/login",
                    method: "POST",
                    type: "json",
                    data: $('#login_user_info').serialize(),
                    success: function (data) {
                        console.log("%csuccess%c!!!", "color: blue", "color: red");
                        alert("data");
                    },
                    error: function (request, status, error) {
                        console.log("%cfail%c!!!", "color: blue", "color: red");
                        alert("error");
                    }
                });
            });
        });
    </script>
  </body>
</html>

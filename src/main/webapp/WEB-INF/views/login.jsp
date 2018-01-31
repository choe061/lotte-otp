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
    <title>LOTTE.com 로그인</title>
  </head>
  <body>
    <div class="container">
      <form action="" method="post" id="login_user_info">
        <div class="form-group">
          <label for="id">아이디:</label>
          <input type="text" class="form-control" id="id">
        </div>
        <div class="form-group">
          <label for="pw">비밀번호:</label>
          <input type="password" class="form-control" id="pw">
        </div>

        <div class="form-check">
          <label class="form-check-label">
            <input class="form-check-input" type="checkbox"> 아이디 기억하기
          </label>
        </div>

        <button type="button" class="btn btn-primary" id="login_submit_button" >로그인</button>
      </form>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">상세 내용</h4>
          </div>

          <div class="modal-body">
            <form action="/to-do/update" method="post">
              <div class="form-group">
                <label for="otp">OTP :</label>
                <input type="text" name="otp" placeholder="otp" class="form-control" id="otp">
              </div>
              <input type="submit" value="인증">
            </form>
          </div>
        </div>

      </div>
    </div>

    <script src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#login_submit_button').on('click', function () {
                var id = $('#id').val();
                var pw = $('#pw').val()
                console.log('val : ' + id + ", "+ pw);
                $.ajax({
                    url: "/user/login",
                    method: "POST",
                    contentType : "application/json; charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify({
                        "id": id,
                        "pw": pw
                    }),
                    success: function (responseJson) {
                        alert("data : "+responseJson);
                        console.log("%csuccess%c!!!", "color: blue", "color: red");
                    },
                    error: function (request, status, error) {
                        alert("error : "+status+". "+error);
                        console.log("%cfail%c!!!", "color: blue", "color: red");
                    }
                });
            });
        });
    </script>
  </body>
</html>

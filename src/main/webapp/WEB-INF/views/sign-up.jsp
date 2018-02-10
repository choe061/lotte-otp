<%--
  Created by IntelliJ IDEA.
  User: choi
  Date: 2018. 2. 6.
  Time: PM 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Lotte.com 회원가입</title>
  <!-- Bootstrap core CSS-->
  <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="${pageContext.request.contextPath}/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="${pageContext.request.contextPath}/css/sb-admin.css" rel="stylesheet">
</head>

<body class="bg-dark">
  <div class="container">
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">회원가입</div>
      <div class="card-body">
        <div class="col-md-12">
          <img src="http://image.lotte.com/lotte/images/common/header2015/logo_main_2015.gif" style="margin: 0 auto; display: block;">
        </div>
        <form action="/user/sign-up" method="post">
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-9">
                <label for="id">아이디</label>
                <input class="form-control" name="id" id="id" type="text" aria-describedby="nameHelp" placeholder="아이디">
              </div>
            </div>
            <div class="form-row" style="margin-top: 15px">
              <div class="col-md-3">
                <button type="button" class="btn btn-danger" id="duplicate_button">ID 중복확인</button>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="pw">비밀번호</label>
                <input class="form-control" name="pw" id="pw" type="password" placeholder="비밀번호">
              </div>
              <div class="col-md-6">
                <label for="pw2">비밀번호 확인</label>
                <input class="form-control" name="pw2" id="pw2" type="password" placeholder="비밀번호 재입력">
              </div>
            </div>
          </div>
          <input type="submit" class="btn btn-primary btn-block" value="가입하기">
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="login">로그인 페이지</a>
        </div>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <script src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script>
  <script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/sign-up.js"></script>
  <script type="text/javascript">
      $(document).ready(function() {
          $('#duplicate_button').on('click', function () {
              var id = $('#id').val();
              duplicateId(id);
          });
      });
  </script>
</body>

</html>

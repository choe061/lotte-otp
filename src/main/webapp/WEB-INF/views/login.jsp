<%--
  Created by IntelliJ IDEA.
  User: choi
  Date: 2018. 2. 6.
  Time: PM 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Lotte.com 로그인</title>
  <!-- Bootstrap core CSS-->
  <%--<link href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css" rel="stylesheet">--%>
  <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="${pageContext.request.contextPath}/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="${pageContext.request.contextPath}/css/sb-admin.css" rel="stylesheet">
</head>

<body class="bg-dark">
  <div class="container">
    <div class="card card-login mx-auto mt-5">
      <div class="card-header">로그인</div>
      <div class="card-body">
        <div class="col-md-12">
          <img src="http://image.lotte.com/lotte/images/common/header2015/logo_main_2015.gif" style="margin: 0 auto; display: block;">
        </div>
        <form action="" method="post" id="login_user_info">
          <div class="form-group">
            <label for="id">아이디</label>
            <input class="form-control" id="id" type="text" aria-describedby="emailHelp" placeholder="아이디">
          </div>
          <div class="form-group">
            <label for="pw">비밀번호</label>
            <input class="form-control" id="pw" type="password" placeholder="비밀번호">
          </div>
          <div class="form-group">
            <div class="form-check">
              <label class="form-check-label">
                <input class="form-check-input" type="checkbox" id="save-id"> 아이디 기억하기</label>
            </div>
          </div>
          <button type="button" class="btn btn-primary btn-block" id="login-submit-button" >로그인</button>
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="/sign-up">회원가입</a>
        </div>
      </div>
    </div>
  </div>

  <!-- OTP Input Modals -->
  <div class="modal fade" id="otp-input-dialog" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="exampleModalLabel">OTP 2차 인증</h4>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        </div>
        <form action="" method="get">
          <div class="modal-body">
            <div class="form-group">
              <label for="otp" class="control-label">OTP :</label>
              <input type="text" class="form-control" name="otp" id="otp" placeholder="OTP를 입력하세요">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
            <input type="button" class="btn btn-primary" id="otp-submit-button" value="인증하기">
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- OTP Connection Modal -->
  <div class="modal fade" id="otp-connect-dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">OTP 연동 안내</h4>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        </div>
        <div class="modal-body">
          <div>
            <p> &bigcirc; 카카오톡 플러스 친구('균봇'검색 or QR코드를 통해 친구 추가하세요.)</p>
            <div>
              <p> &bigcirc; 회원님과 카카오톡 연동을 위한 임시 키를 발급해드립니다. 플러스 친구 추가 후 안내에 따라 임시 키를 입력해주세요.</p>
              <div><span> &bigcirc; 임시 키(만료시간 5분) : </span><span id="temp-key"></span></div>
            </div>
            <div style="text-align: center;">
              <img src="images/qrcode_350.png" width="50%" style="margin-bottom: 5%">
              <img src="images/add_friend.png" width="50%">
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/vendor/bootbox.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

  <script src="${pageContext.request.contextPath}/js/login.js"></script>
  <script src="${pageContext.request.contextPath}/js/otp-auth.js"></script>
  <script src="${pageContext.request.contextPath}/js/js.cookie.js"></script>

  <script type="text/javascript">
      printLogo();
      $(document).ready(function() {
          if (Cookies.get('id') !== undefined) {
              $('#id').val(Cookies.get('id'));
              $('#save-id').attr('checked', true);
          }
          <%--var session_uid = "${sessionScope.id}";--%>
          <%--if (typeof session_uid === 'string' && session_uid !== '') {--%>
              <%--getOTPConnectStatus(session_uid);--%>
          <%--}--%>

          $('#pw').keydown(function (event) {
              if (event.keyCode === 13) {
                  requestLogin();
              }
          });

          $('#otp').keydown(function (event) {
              if (event.keyCode === 13) {
                  request2NdAuth();
                  return false;
              }
          });

          $('#login-submit-button').on('click', function () {
              requestLogin();
          });

          $('#otp-submit-button').on('click', function () {
              request2NdAuth();
          });
      });
  </script>
</body>

</html>

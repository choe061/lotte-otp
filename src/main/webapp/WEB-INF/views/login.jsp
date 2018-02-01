<%--
  Created by IntelliJ IDEA.
  User: choi
  Date: 2018. 1. 29.
  Time: PM 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
<html>
  <head>
    <title>LOTTE.com 로그인</title>
  </head>
  <body>
    <div class="container">
      <img src="http://image.lotte.com/lotte/images/common/header2015/logo_main_2015.gif">
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

    <!-- OTP Input Modal -->
    <div class="modal fade" id="otpInputDialog" role="dialog">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">상세 내용</h4>
          </div>

          <div class="modal-body">
            <form action="/otp/auth" method="get">
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

    <!-- OTP Input Modal -->
    <div class="modal fade" id="otpConnectDialog" role="dialog">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">OTP 연동 필수</h4>
          </div>

          <div class="modal-body">
            <div>
              <p> &bigcirc; 카카오톡 플러스 친구('균봇'검색 or QR코드를 통해 친구 추가하세요.)</p>
              <img src="images/qrcode_350.png" sizes="50%">
            </div>
            <div>
              <p> &bigcirc; 회원님과 카카오톡 연동을 위한 임시 키를 발급해드립니다. 플러스 친구 추가 후 안내에 따라 임시 키를 입력해주세요.</p>
              <p> &bigcirc; 임시 키 :</p><p id="temp-key"></p>
            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
          </div>

        </div>

      </div>
    </div>

    <script src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
    <script src="js/login.js"></script>
    <script src="js/otp-auth.js"></script>
    <script type="text/javascript">
        printLogo();
        $(document).ready(function() {
            $('#login_submit_button').on('click', function () {
                var id = $('#id').val();
                var pw = $('#pw').val();
                requestLogin(id, pw);
            });
        });
    </script>
  </body>
</html>

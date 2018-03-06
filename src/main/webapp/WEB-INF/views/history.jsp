<%--
  Created by IntelliJ IDEA.
  User: choi
  Date: 2018. 2. 6.
  Time: PM 11:37
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
    <title>Lotte.com 접속이력</title>
    <!-- Bootstrap core CSS-->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Page level plugin CSS-->
    <link href="${pageContext.request.contextPath}/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/css/sb-admin.css" rel="stylesheet">
    <style>
      .dropdown-menu{
        right: 0 !important;
        left: initial !important;
      }
    </style>
  </head>
  <body class="fixed-nav sticky-footer bg-dark" id="page-top">

    <!-- Navigation-->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
      <a class="navbar-brand" href="/main">LOTTE.com</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
          <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
            <a class="nav-link" href="/main">
              <i class="fa fa-fw fa-dashboard"></i>
              <span class="nav-link-text">Main</span>
            </a>
          </li>
          <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
            <a class="nav-link" href="#">
              <i class="fa fa-fw fa-table"></i>
              <span class="nav-link-text">나의 접속이력</span>
            </a>
          </li>
        </ul>
        <ul class="navbar-nav sidenav-toggler">
          <li class="nav-item">
            <a class="nav-link text-center" id="sidenavToggler">
              <i class="fa fa-fw fa-angle-left"></i>
            </a>
          </li>
        </ul>
        <ul class="navbar-nav ml-auto">
          <!-- 알림창, 최근 로그인 접속 정보 3개 넣기 -->
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle mr-lg-auto" id="alertsDropdown" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i class="fa fa-fw fa-bell"></i>
              <span class="d-lg-none">Alerts
                <span class="badge badge-pill badge-warning">6 New</span>
              </span>
              <span class="indicator text-warning d-none d-lg-block">
                <i class="fa fa-fw fa-circle"></i>
              </span>
            </a>
            <div class="dropdown-menu" aria-labelledby="alertsDropdown">
              <h6 class="dropdown-header">새로운 알림</h6>
              <div class="dropdown-divider"></div>
              <%--<a class="dropdown-item" href="#">
                <span class="text-success">
                  <strong>접속 성공</strong>
                </span>
                <span class="small float-right text-muted">${myHistory.accessed_at}</span>
                <div class="dropdown-message small">${myHistory.accessed_at}</div>
              </a>
              <a class="dropdown-item" href="#">
                <span class="text-danger">
                  <strong>접속 실패</strong>
                </span>
                <span class="small float-right text-muted">${myHistory.accessed_at}</span>
                <div class="dropdown-message small">${myHistory.accessed_at}</div>
              </a>
              <c:forEach items="${myHistory}" var="myHistory">
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">
                  <c:choose>
                    <c:when test="${myHistory.success}">
                      <span class="text-success">
                        <strong><i class="fa fa-long-arrow-up fa-fw"></i>접속 성공awefawefawefawefawefawef</strong>
                      </span>
                    </c:when>
                    <c:otherwise>
                      <span class="text-danger">
                        <strong><i class="fa fa-long-arrow-up fa-fw"></i>접속 실패</strong>
                      </span>
                    </c:otherwise>
                  </c:choose>
                  <span class="small float-right text-muted">${myHistory.accessed_at}</span>
                  <div class="dropdown-message small">${myHistory.accessed_at}</div>
                </a>
              </c:forEach>--%>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item small" href="#">모든 접속 이력 조회</a>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
              <i class="fa fa-fw fa-sign-out"></i>Logout</a>
          </li>
        </ul>
      </div>
    </nav>

    <div class="content-wrapper">
      <div class="container-fluid">
        <!-- Breadcrumbs-->
        <%--<ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="#">Dashboard</a>
          </li>
          <li class="breadcrumb-item active">Tables</li>
        </ol>--%>
        <!-- Example DataTables Card-->
        <div class="card mb-3">
          <div class="card-header">
            <i class="fa fa-table"></i> ${sessionScope.id} 님의 접속이력</div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                <tr>
                  <th>No.</th>
                  <th>IP Address</th>
                  <th>OS</th>
                  <th>Browser</th>
                  <th>Date</th>
                  <th>Result</th>
                </tr>
                </thead>
                <tfoot>
                <tr>
                  <th>No.</th>
                  <th>IP Address</th>
                  <th>OS</th>
                  <th>Browser</th>
                  <th>Date</th>
                  <th>Result</th>
                </tr>
                </tfoot>
                <tbody>
                  <c:forEach items="${myHistory}" var="myHistory">
                    <tr>
                      <td>${myHistory.id}</td>
                      <td>${myHistory.ip}</td>
                      <td>${myHistory.os}</td>
                      <td>${myHistory.browser}</td>
                      <td>${myHistory.accessed_date}</td>
                      <c:choose>
                        <c:when test="${myHistory.success}">
                          <td>
                            <span class="text-success">
                              <strong>성공</strong>
                            </span>
                          </td>
                        </c:when>
                        <c:otherwise>
                          <td>
                            <span class="text-danger">
                              <strong>실패</strong>
                            </span>
                          </td>
                        </c:otherwise>
                      </c:choose>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div class="card-footer small text-muted">Updated at 11:59 PM</div>
        </div>
      </div>
      <!-- /.container-fluid-->
      <!-- /.content-wrapper-->
      <footer class="sticky-footer">
        <div class="container">
          <div class="text-center">
            <small>Copyright © LOTTE.com 2018</small>
          </div>
        </div>
      </footer>
      <!-- Scroll to Top Button-->
      <a class="scroll-to-top rounded" href="#page-top">
        <i class="fa fa-angle-up"></i>
      </a>
      <!-- Logout Modal-->
      <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">로그아웃 하시겠습니까?</h5>
              <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">로그아웃을 누르면 세션이 종료됩니다.</div>
            <div class="modal-footer">
              <button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
              <a class="btn btn-primary" href="/user/logout">로그아웃</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Bootstrap core JavaScript-->
    <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Page level plugin JavaScript-->
    <script src="${pageContext.request.contextPath}/vendor/datatables/jquery.dataTables.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="${pageContext.request.contextPath}/vendor/sb-admin.min.js"></script>
    <!-- Custom scripts for this page-->
    <script src="${pageContext.request.contextPath}/vendor/sb-admin-datatables.min.js"></script>
  </body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/assets/css/local.css">
    <link rel="stylesheet" href="${contextPath}/assets/css/all.css">

</head>
<body>
<div class="container-fluid" >
    <div class="site-content">
        <jsp:include page="../header.jsp"/>
        <div class="row site-padding" style="margin-top: 20vh">
            <div class="panel-body">
                <div class="content col-8" style="margin-left:25vw">
                <form action="${contextPath}/login" method="post">
                    <fieldset>
                        <legend></legend>
                        <h4 style="margin-bottom: 4vh;text-align:center;">Sign in</h4>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                Bad cridentials
                                <br/>
                            </div>
                        </c:if>
                        <div class="form-group row">
                            <div class="col">
                                 <input class="form-control form:input-large" placeholder="Email"
                                       name='username' type="text" id="username">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col">
                                <input class="form-control form:input-large" placeholder="Password"
                                       name='password' type="password" value="" id="password">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col">
                                <input class="col btn btn-primary orderbutton" type="submit"
                                       value="Login" id="Login">
                            </div>
                        </div>
                        <div class="nav nav-tabs"></div>
                        <div class="form-group row">
                            <div style="text-align:center;width: 100%;margin-top: 3vh">
                                <a href="${contextPath}/registration" style="">Sign up new user</a>
                            </div>
                        </div>
                    </fieldset>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


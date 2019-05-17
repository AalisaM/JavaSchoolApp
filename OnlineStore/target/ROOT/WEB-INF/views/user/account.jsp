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
        <div class="row site-padding catalogue">
            <div style="margin-top: 20vh;">
                <sec:authorize access="isAuthenticated()">
                    <jsp:include page="userMenu.jsp"/>
                    <div id="curModule">
                        <c:if test="${empty modulePSWD}">
                            <jsp:include page="userAccount.jsp"/>
                        </c:if>
                        <c:if test="${!empty modulePSWD}">
                            <jsp:include page="changePassword.jsp"/>
                        </c:if>
                    </div>
                </sec:authorize>
           </div>
        </div>
    </div>
</div>
</body>
</html>

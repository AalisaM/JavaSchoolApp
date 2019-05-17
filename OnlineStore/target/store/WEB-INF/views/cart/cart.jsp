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
            <div style="margin-top: 20vh">
                <sec:authorize access="isAuthenticated()">
                    <jsp:include page="../order/orderUserDataTemplate.jsp"/>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <div class="notification">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            Please login if you want to make order.
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </div>
                </sec:authorize>
                <div class="col content" id="currentCart" style=" ${empty cartExtendedDTO.curCart ? "width: 80vw;" :"margin-left: 28vw; width: 60vw;"}">
                    <jsp:include page="cartData.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/assets/css/local.css">
    <link rel="stylesheet" href="${contextPath}/assets/css/all.css">

</head>
<body>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<c:set var="currentPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="topmarginImg" value="${20}"/>
<c:if test="${!product.deleted}">
    <sec:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
        <c:set var="topmarginImg" value="${29.5}"/>
    </sec:authorize>
</c:if>
<div class="container-fluid" >
    <div class="site-content">
        <jsp:include page="../header.jsp"/>
        <div class="row site-padding catalogue">
            <div class="col-sm-2 navigation" style="position: fixed; overflow: auto; margin-top: ${topmarginImg}vh">
                <div class="row filter content-product">
                    <div class="card-product border-0" style="outline: none">
                        <c:if test="${product.imageSource.length() > 0}">
                            <div style="text-align: center">
                                <div class="crop">
                                <%--<img width="100px" src="${contextPath}/assets/images/uploads/${product.id}_icon.png"/>--%>
                                    <img alt="product_${product.id}" id="myImg" src="http://${ip}:8190/${product.imageSource}"/>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${product.imageSource.length() == 0 || empty product.imageSource}">
                            <div style="text-align: center">
                                <div class="crop">
                                    <img alt="product_${product.id}" id="myImg" src="${contextPath}/assets/images/product.png"/>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div style="margin: 0 auto">
                        <c:if test="${product.amount > 0}">
                            <button class="btn btn-primary orderbutton" style="width: 10vw" id="addProdFromProfile">
                                <span class="fas fa-cart-arrow-down"></span>${product.price} <span class="fas fa-ruble-sign"></span>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            <div id="myModal" class="modal">
                <span class="close">&times;</span>
                <img alt="product_${product.id}" class="modal-content" id="img01">
                <div id="caption"></div>
            </div>

            <div id="productDescription">
                <c:if test="${!product.deleted}">
                    <sec:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
                        <div class="row admin-nav" style="margin-top: 20vh; margin-left: 20vw;">
                            <div class="col">
                                <ul class="nav nav-tabs">
                                    <li class="nav-item">
                                        <a class="nav-link active productProfile" href="#" >Profile</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link productEdit" href="#" >Edit</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </sec:authorize>
                </c:if>
                <jsp:include page="productData.jsp"/>
            </div>
            <c:if test="${!product.deleted}">
                <sec:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
                    <div id="productEditForm" style="display: none;">
                        <div class="row admin-nav" style="margin-top: 20vh; margin-left: 20vw;">
                            <div class="col">
                                <ul class="nav nav-tabs">
                                    <li class="nav-item">
                                        <a class="nav-link productProfile" href="#" >Profile</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link productEdit active" href="#" >Edit</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div id="ef">
                            <jsp:include page="../admin/productEditForm.jsp"/>
                        </div>
                    </div>
                </sec:authorize>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>

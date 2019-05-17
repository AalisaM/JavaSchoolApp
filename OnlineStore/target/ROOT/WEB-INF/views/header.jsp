<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="currentPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<p id="warPath" hidden>${contextPath}</p>
<div class="row header navbar-fixed-top fixed-top">
    <div class="col" style="max-height: 1.5vh">
        <div class="row header-menu site-padding ">
            <div>
                <a href="${contextPath}/"><img src="${contextPath}/assets/images/logo2.png" alt="logo" style="width:50px" align="left">
                    <p style="font-size: 2rem; width: 13vw; margin-top: 1.5vw">T-Store</p></a>
            </div>
            <p hidden id="ip">${ip}</p>
            <div class="col">
                <ul class="nav justify-content-end ">
                    <sec:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(currentPath, 'admin') ? 'active' : ''}"
                               href="${contextPath}/admin">Admin</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(currentPath, 'login') ? 'active' : ''}" href="${contextPath}/login">Log in</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(currentPath, 'register') ? 'active' : ''}"
                               href="${contextPath}/registration">Sign up</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(currentPath, 'profile') ? 'active' : ''}"
                               href="${contextPath}/account">Account</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${contextPath}/logout">Log out</a>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
        <div class="row align-items-center header-content site-padding">
            <div class="col search">
            <sec:authorize access="isAuthenticated()">
                <p id="loggedUserName" class="text-left" style="float: left" hidden>Your login: <sec:authentication property="principal.username" />
                </p>
            </sec:authorize>
                <c:if test="${! empty ip}">
                    <div style="float: right;width: 75%;" class="searchGoodBox">
                        <div class="input-group">
                            <input class="form-control" type="search" id="example-search-input" style="width: 75%;" placeholder="Enter product name">
                            <span class="input-group-append">
                                <button class="btn btn-outline-secondary border-left-0 border" id="searchProd">
                                     <span class="fa fa-search"></span>
                                </button>
                            </span>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="col-sm-2 cart">
                <p class="text-right" id="cartTemplate">
                    <jsp:include page="cart/cartIcon.jsp"/>
                </p>
            </div>
        </div>
    </div>
</div>


<script src="${contextPath}/assets/js/jquery.js"></script>
<script src="${contextPath}/assets/js/jquery-ui.js"></script>
<script src="${contextPath}/assets/js/common.js"></script>
<script src="${contextPath}/assets/js/cartOrderEvents.js"></script>
<script src="${contextPath}/assets/js/admin.js"></script>
<script src="${contextPath}/assets/js/account.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>



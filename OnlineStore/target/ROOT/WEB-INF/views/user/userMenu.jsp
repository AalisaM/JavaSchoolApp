<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

<div class="col-sm-2 navigation" style="position: fixed;">
    <ul class="nav nav-pills flex-column menu">
        <li class="nav-item">
            <a class="nav-link disabled">Account menu</a>
        </li>
            <li class="nav-item">
                <a id="accEdit" class="nav-link" href="${contextPath}/account">Account edit</a>
                <a id="cpswd" class="nav-link" href="#">Change password</a>
                <a id="localOH" class="nav-link" href="#">Order History</a>
            </li>
    </ul>
</div>

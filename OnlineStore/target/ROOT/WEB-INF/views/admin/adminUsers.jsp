<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

<div class="col content" style="margin-left: 17vw; width: 70vw;">
    <jsp:include page="../notifications.jsp"></jsp:include>
    <h4>User list</h4>

    <table class="table" style="width: 100%;">
        <thead class="thead-dark">
        <tr>
            <th scope="col" hidden>User ID</th>
            <th scope="col">User Name </th>
            <th scope="col">User Email</th>
            <th scope="col">User Active Address</th>
            <th scope="col">User Birth</th>
            <th scope="col">Is Admin ?</th>
        </tr>
        </thead>
        <tbody>
        <%--<c:forEach items="${categorizedOrders.unpaidOrders}" var="c">--%>
        <c:forEach items="${listUsers}" var="u">
            <tr>
                <td class="userid" hidden>${u.id}</td>
                <td>${u.fullName}</td>
                <td>${u.email}</td>
                <td>${u.activeAddressId.address}</td>
                <td>${u.birth}</td>
                <td><input type="checkbox" name="isAdmin" class="isAdmin" value="${u.admin}" ${u.admin ? 'checked' : ''}>
                </td>
                <%--<td><a href="<c:url value='/users/remove/${u.id}' />" >Delete</a></td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<table class="table" style="width: 68vw;">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Order id</th>
        <th scope="col">Address</th>
        <th scope="col">Payment type</th>
        <th scope="col">Payment status</th>
        <th scope="col">Order status </th>
        <th scope="col">Total price </th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="c">
        <tr>
            <td class="">${c.id}</td>
            <td class="">${c.address}</td>
            <td class="">${c.paymentType.type}</td>
            <td class="">${c.paymentStatus.status}</td>
            <td class="">${c.orderStatus.status}</td>
            <td class="">${c.totalPrice}</td>
            <td><a href="${contextPath}/order/${c.id}"> Details</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

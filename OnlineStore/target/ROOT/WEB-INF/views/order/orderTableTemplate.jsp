<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="col content" style="margin-left: 17vw; width: 72vw;">
    <table class="table table-responsive" style="width: 68vw;">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Order id</th>
            <th scope="col">Address</th>
            <th scope="col">Shipping type</th>
            <th scope="col">Payment type</th>
            <th scope="col">Payment status</th>
            <th scope="col">Order status </th>
            <th scope="col">Total price </th>
            <th scope="col">Amount </th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <%--<c:forEach items="${categorizedOrders.unpaidOrders}" var="c">--%>
        <c:forEach items="${orders}" var="c">
            <tr>
                <td class="orderid">${c.id}</td>
                <td class="address">${c.address}</td>
                <td class="shippingType">
                    <%--<p class="shippingTypeid" hidden> ${c.shippingType}</p>--%>
                    <p class="shippingTypes"> ${c.shippingType}</p
                </td>
                <td class="paymentType">
                    <p class="paymentTypeid" hidden> ${c.paymentType.id}</p>
                    <p class="paymentTypes"> ${c.paymentType.type}</p></td>
                <td class="paymentstatus">
                    <p class="paymentstatusid" hidden> ${c.paymentStatus.id}</p>
                    <p class="paymentstatuss"> ${c.paymentStatus.status}</p>
                </td>
                <td class="orderstatus">
                    <p class="orderststusid" hidden>${c.orderStatus.id}</p>
                    <p class="orderststuss"> ${c.orderStatus.status}</p>
                </td>
                <td class="totalprice">${c.totalPrice}</td>
                <td class="amount">${c.amount}</td>
                <td><a href="${contextPath}/order/${c.id}"> Details</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

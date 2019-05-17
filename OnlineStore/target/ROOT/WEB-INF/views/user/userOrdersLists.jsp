<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<div class="col content" style="margin-left: 17vw; width: 70vw;">
    <h2> Waiting for payment orders</h2>
    <c:set var="orders" value="${categorizedOrders.unpaidOrders}"/>
    <%@ include file="userOrderTemplate.jsp" %>
</div>
<c:if test="${! empty categorizedOrders.paidOrders}">
    <div class="col content" style="margin-top:4vw; margin-left: 17vw; width: 70vw;">
    <h2> Orders on the way</h2>
        <c:set var="orders" value="${categorizedOrders.paidOrders}"/>
        <%@ include file="userOrderTemplate.jsp" %>
</div>
</c:if>
<c:if test="${! empty categorizedOrders.processedOrders}">
<div class="col content" style="margin-top:4vw; margin-left: 17vw; width: 70vw;">
    <h2> Delivered orders</h2>
    <c:set var="orders" value="${categorizedOrders.processedOrders}"/>
    <%@ include file="userOrderTemplate.jsp" %>
</div>
</c:if>

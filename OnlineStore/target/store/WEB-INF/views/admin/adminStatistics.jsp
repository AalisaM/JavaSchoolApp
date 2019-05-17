<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="col content" style="margin-left: 17vw; width: 70vw;">

    <h4>Revenue</h4>
    <table class="table">
        <thead class="thead-dark">
        <tr><th scope="col">Period</th>
            <th scope="col">Revenue, rubles.</th>
        </tr>
        </thead>
        <c:if test="${! empty revenueMonth}">
            <tr>
                <td class="">Last month</td>
                <td>${revenueMonth} </td>
            </tr>
        </c:if>
        <c:if test="${! empty revenuePeriod}">
            <tr>
                <td class="">Selected period</td>
                <td>${revenuePeriod} </td>
            </tr>
        </c:if>
        <c:if test="${! empty revenueWeek}">
            <tr>
                <td class="">Last week</td>
                <td>${revenueWeek} </td>
            </tr>
        </c:if>
    </table>
</div>

<div class="col content" style="margin-top:4vw; margin-left: 17vw; width: 70vw;">
    <h4>Top 10 clients</h4>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">User fullname</th>
            <th scope="col">User email</th>
            <th scope="col">Money spent</th>
        </tr>
        </thead>
        <c:forEach items="${userTop}" var="user">
            <tr>
                <td>${user.key.fullName}</td>
                <td>${user.key.email}</td>
                <td>${user.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="col content" style="margin-top:4vw; margin-left: 17vw; width: 70vw;">
    <h4>Top 10 ordered products</h4>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Product name</th>
            <th scope="col">Product price</th>
            <th scope="col">Players amount</th>
            <th scope="col">Bought quantity</th>

        </tr>
        </thead>
        <c:forEach items="${productTop}" var="product">
            <tr>
                <td>${product.key.name}</td>
                <td>${product.key.price}</td>
                <td>${product.key.minPlayerAmount} - ${product.key.maxPlayerAmount}</td>
                <td>${product.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>

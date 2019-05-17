<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h4>Products list</h4>
<table class="table table-responsive">
    <thead class="thead-dark">
    <tr>
        <th scope="col" hidden>Product ID</th>
        <th scope="col">Product Name </th>
        <th scope="col">Product Price</th>
        <th scope="col">Product Amount</th>

        <th scope="col">Product Weight</th>
        <th scope="col">Product Volume</th>
        <th scope="col">Players</th>
        <th scope="col">Has Image</th>
        <th scope="col">Category</th>
        <th scope="col">Details</th>
        <th scope="col">Remove</th>

    </tr>
    </thead>
    <c:forEach items="${productList}" var="p">
        <tr>
            <td class="productid" hidden>${p.id}</td>
            <td class="productname">${p.name}</td>
            <td class="productprice">${p.price}</td>
            <td>${p.amount}</td>
            <td>${p.weight}</td>
            <td>${p.volume}</td>
            <td>${p.minPlayerAmount} - ${p.maxPlayerAmount}</td>
            <td>${! empty p.imageSource}</td>
            <td>${p.category.title}</td>
            <td><a href="${contextPath}/product/${p.id}"> Details</a></td>
            <td><span class="fas fa-trash-alt removeProduct"></span></td>
        </tr>
    </c:forEach>
</table>

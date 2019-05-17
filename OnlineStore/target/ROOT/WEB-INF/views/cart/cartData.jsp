<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<jsp:include page="../notifications.jsp"></jsp:include>

<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Product</th>
        <th scope="col">Price</th>
        <th scope="col">Quantity</th>
        <th scope="col">Total</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${!empty cartExtendedDTO.curCart}">
        <c:forEach items="${cartExtendedDTO.curCart.cartItem}" var="c">
            <tr class="productItem">
                <td class="productid" style="display:none;">${c.product.id}</td>
                <td class="productname"> ${c.product.name}</td>
                <td class="productprice">${c.price / c.amount}</td>
                <td>
                    <span class="fas fa-minus cartDecItem" style="margin-right: 1vw; display: inline-block;"> </span>
                    <p class="amount" style="display: inline-block">${c.amount}</p>
                    <span class="fas fa-plus cartIncItem" style="margin-left: 1vw; display: inline-block;"></span>
                </td>
                <td class="total">${(c.price)}</td>
                <td>
                    <span class="fas fa-trash-alt deleteCartItem"></span>
                </td>
            </tr>
        </c:forEach>
        <tr></tr>
    </c:if>
    <c:if test="${!empty cartExtendedDTO.cartAnon}">
        <c:forEach items="${cartExtendedDTO.cartAnon.cartItem}" var="c">
            <tr class="productItem">
                <td class="productid" style="display:none;">${c.product_id}</td>
                <td class="productname"> ${c.product_name}</td>
                <td class="productprice">${c.price / c.amount}</td>
                <td>
                    <span class="fas fa-minus cartDecItemAnon" style="margin-right: 1vw; display: inline-block;"> </span>
                    <p class="amount" style="display: inline-block">${c.amount}</p>
                    <span class="fas fa-plus cartIncItemAnon" style="margin-left: 1vw; display: inline-block;"></span>
                </td>
                <td class="total">${(c.price)}</td>
                <td>
                    <span class="fas fa-trash-alt deleteCartItem"></span>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
<div style="float: right;">Total price: <jsp:include page="cartPrice.jsp"/></div>

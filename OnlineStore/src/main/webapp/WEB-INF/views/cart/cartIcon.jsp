<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

<a href="${contextPath}/cart">

    <span class="fas fa-shopping-cart"></span>
    <c:set var="cartValue" value='${ ! empty curCart ? curCart.totalAmount + "" : ( ! empty cartExtendedDTO  ?
    (! empty cartExtendedDTO.curCart ? cartExtendedDTO.curCart.totalAmount + "" :
    (! empty cartExtendedDTO.cartAnon ? cartExtendedDTO.cartAnon.totalAmount + "" : "")) : "" )}'/>
        <em id="lblCartCount" class="badge badge-primary">${cartValue}</em>
        <em id="cartPrice">
        <jsp:include page="cartPrice.jsp"/>
    </em>
</a>

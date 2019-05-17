<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<c:set var="currentPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="topmargin" value="${20}"/>
<c:if test="${!product.deleted}">
    <sec:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
        <c:set var="topmargin" value="${0}"/>
    </sec:authorize>
</c:if>
<div class="col content" style="margin-top: ${topmargin}vh; margin-left: 20vw; width: 60vw;" id="productProfData">
   <div class="form-group col-12">
        <h1 class="productname">${product.name}</h1>
       <p class="productid" hidden>${product.id}</p>
    </div>
    <div id="disc">
        <div class="row admin-nav">
            <div class="col">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link  active descNav" href="#" >Description</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ruleNav" href="#" >Rules</a>
                    </li>
                </ul>
            </div>
        </div>
        <p class="form-group col white-space-pre">${product.description}</p>
    </div>
    <div id="rule" style="display: none;">
        <div class="row admin-nav">
            <div class="col">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link descNav" href="#" >Description</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active ruleNav" href="#">Rules</a>
                    </li>
                </ul>
            </div>
        </div>
        <p class="form-group col white-space-pre">${product.rule}</p>
    </div>
    <div class="form-group col-6">
        <span class="fas fa-users"></span>${product.minPlayerAmount} - ${product.maxPlayerAmount}
        <span class="fas fa-tags" style="margin-left: 10px;"></span>${product.category.title}
    </div>
    <div class="form-group col-6">
        <span class="fas fa-dollar-sign"></span>${product.price}
        <p class="productprice" hidden>${product.price}</p>
    </div>
    <div class="form-group col-6">
        <c:if test="${product.amount > 0}">
            <span class="fas fa-check" style="color: forestgreen"></span> in stock
        </c:if>
        <c:if test="${product.amount <= 0}">
            <span class="fas fa-times" style="color: #721c24"></span> out of stock
        </c:if>
    </div>
</div>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/assets/css/local.css">
    <link rel="stylesheet" href="${contextPath}/assets/css/all.css">

</head>
<body>
<div class="container-fluid" >
    <div class="site-content">
        <jsp:include page="../header.jsp"/>
        <div class="row site-padding" style="margin-top: 20vh">
            <p id="orderID" hidden>${orderdto.id}</p>
            <c:if test="${! empty orderdto}">
            <div class="col-sm-3 navigation" style="position: fixed;">
                <div class="row filter content">
                    <div class="col">
                        <div class="form-row">
                            <p id="userEmail" hidden>${orderdto.email}</p>
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1  fas fa-user-circle"></span>
                                <div class="col" style="margin:auto">
                                        ${orderdto.email}
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                    <span class="col-form-label col-sm-1 fas fa-map-marker-alt"></span>
                                    <div class="col" style="margin:auto">
                                         ${orderdto.address}
                                        <input hidden readonly type="text" class="form-control" id="address"
                                           aria-describedby="maxCostHelp" value="${orderdto.address}">
                                    </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <p id="shippingid" hidden>${orderdto.shippingType.id}</p>
                                <span class="col-form-label col-sm-1 fas fa-shipping-fast"></span>
                                <div class="col" style="margin:auto">
                                    ${orderdto.shippingType.type}
                                    <input hidden readonly type="text" class="form-control" id="shipping"
                                       aria-describedby="maxCostHelp" value="${orderdto.shippingType.type}">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <p id="paymentid" hidden>${orderdto.paymentType.id}</p>
                                <span class="col-form-label col-sm-1 fas fa-cash-register"></span>
                                <div class="col" style="margin:auto">
                                     ${orderdto.paymentType.type}
                                     <input hidden readonly type="text" class="form-control" id="payment"
                                       aria-describedby="maxCostHelp" value="${orderdto.paymentType.type}">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                            <p id="statusid" hidden>${orderdto.paymentStatus.id}</p>
                                <span class="col-form-label col-sm-1 fas fa-info"></span>
                                <div class="col" style="margin:auto">
                                    ${orderdto.paymentStatus.status}
                                    <input hidden readonly type="text" class="form-control" id="status"
                                           aria-describedby="maxCostHelp" value="${orderdto.paymentStatus.status}">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row" hidden>
                                <p id="orderstatusid" hidden>${orderdto.orderStatus.id}</p>
                                <input readonly type="text" class="form-control" id="orderstatus"
                                       aria-describedby="maxCostHelp" value="${orderdto.orderStatus.status}">
                                <small class="form-text text-muted">Order status for current moment</small>
                            </div>
                        </div>
                        <div class="nav-tabs nav"></div>
                        <div class="form-row">
                            <div class="form-group row">
                                <div class="col-form-label col-sm-12">
                                    Amount of products: ${orderdto.amount}
                                </div>
                                <div class="col">
                                    <input hidden readonly type="text" class="form-control" id="lblCartCountDTO"
                                           aria-describedby="maxCostHelp" value="${orderdto.amount}">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-dollar-sign"></span>
                                <div class="col" style="margin:auto">
                                    ${orderdto.totalPrice}
                                    <input hidden readonly type="text" class="form-control" id="cartPriceDTO"
                                             aria-describedby="maxCostHelp" value="${orderdto.totalPrice}">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-6">
                                <button id="cancel" class="btn btn-secondary" style="width: 100%">Cancel</button>
                            </div>
                            <div class="form-group col-6">

                                <c:if test="${orderdto.paymentType.type == 'cash'}">

                                    <button id="approve" class="btn btn-primary orderbutton" style="width: 100%">Approve</button>
                                </c:if>
                                <c:if test="${orderdto.paymentType.type != 'cash'}">
                                    <button class="btn btn-primary orderbutton" id="pay" style="width: 100%">Pay</button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
            </c:if>
            <div class="col content" id="currentCart" style="margin-left: 36.5vw; width: 52.5vw;">
               <h5>Please approve next order's data</h5>

                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Total Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orderdto.orderProducts}" var="c">
                        <tr class="productItem">
                            <td class="productid" style="display: none">${c.productid}</td>
                            <td class="productname">${c.productName}</td>
                            <td class="amount">${c.amount}</td>
                            <td class="productprice">${c.price * c.amount}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>

</body>
</html>

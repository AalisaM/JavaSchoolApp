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
<jsp:include page="../notifications.jsp"></jsp:include>
<div class="container-fluid" >
    <div class="site-content">
        <jsp:include page="../header.jsp"/>
        <p id="pStatusId" hidden>${order.orderHistoryDTO.paymentStatus.id}</p>
        <p id="pStatusVal" hidden> ${order.orderHistoryDTO.paymentStatus.status}</p>
        <p id="id" hidden> ${order.orderHistoryDTO.id}</p>

        <div class="row site-padding catalogue">
            <div style="margin-top: 20vh;">
            <div class="col-sm-3 navigation" style="position: fixed;">
                <div class="row filter content">
                    <div class="col">
                        <div class="form-row">
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-user-circle"></span>
                                <div class="col" style="margin:auto">
                                    ${order.orderHistoryDTO.email}
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                             <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-map-marker-alt"></span>
                                 <div class="col" style="margin:auto">
                                     ${order.orderHistoryDTO.address}
                                 </div>
                             </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-shipping-fast"></span>
                                <div class="col" style="margin:auto">
                                    ${order.orderHistoryDTO.shippingType}
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-cash-register"></span>
                                <div class="col" style="margin:auto">
                                    ${order.orderHistoryDTO.paymentType.type}
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-info"></span>
                                <div class="col" style="margin:auto">
                                    ${order.orderHistoryDTO.paymentStatus.status}
                                </div>
                             </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group row">
                                <span class="col-form-label col-sm-1 fas fa-info"></span>
                                        <div class="col" style="margin:auto" id="orderStatusProfile">
                                            ${order.orderHistoryDTO.orderStatus.status}
                                            <c:if test="${(!(order.orderHistoryDTO.orderStatus.status == 'delivered' || order.orderHistoryDTO.orderStatus.status == 'cancelled') && (! empty nextOrderStatus))}">
                                                <button class="btn edit" id="editOrderStatusProfilePage"><img src="${contextPath}/assets/images/edit.png" alt="edit" style="width:15px"></button>
                                            </c:if>
                                        </div>
                                    <div class="col" id="orderStatusSelect" style="display: none">
                                        <c:if test="${(!(order.orderHistoryDTO.orderStatus.status == 'delivered' || order.orderHistoryDTO.orderStatus.status == 'cancelled') && (! empty nextOrderStatus))}">
                                            <select class="orderStatusSelect form-control" style="display: inline-block;width: 12vw; font-size: 12px;">
                                                <c:forEach items="${nextOrderStatus[order.orderHistoryDTO.orderStatus]}" var="cat">
                                                    <option value="${cat.id}" ${cat.status == c.orderStatus.status ? 'selected' : ''}>${cat.status}</option>
                                                </c:forEach>
                                            </select>
                                            <button class="btn edit" id="applyStatusChange"><span class="fas fa-check" style="float: left"></span></button>
                                            <button class="btn edit" id="cancelStatusChange"><span class="fas fa-times" style="float: left"></span></button>
                                        </c:if>
                                    </div>
                                </div>
                          </div>
                        <div class="nav-tabs nav"></div>
                          <div class="form-row">
                               <div class="form-group row">
                                   <div class="col-form-label col-sm-12">
                                       Amount of products: ${order.orderHistoryDTO.amount}
                                   </div>
                                </div>
                            </div>
                            <div class="form-row">
                                 <div class="form-group row">
                                    <span class="col-form-label col-sm-1 fas fa-dollar-sign" style="color: #1b1e21;"></span>
                                     <div class="col" style="margin:auto">
                                        ${order.orderHistoryDTO.totalPrice}
                                     </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <div class="col content" id="currentCart" style="margin-left: 28vw; width: 52.5vw;">
                <h5>Product List</h5>

                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Total Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${order.orderHistoryDTO.orderProducts}" var="c">
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
            <div class="col content" id="audit" style="margin-left: 28vw; width: 52.5vw; margin-top: 4vw;">
            <h5>Order status changes history</h5>

            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Previous state</th>
                    <th scope="col">Current State</th>
                    <th scope="col" style="display: none">Manager</th>
                    <th scope="col">Paid status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${order.orderAuditDTO}" var="c">
                    <tr class="">
                        <td class="" style="display: none">${c.id}</td>
                        <td class="">${c.date}</td>
                        <td class="">${c.prevOrderStatus}</td>
                        <td class="">${c.curOrderStatus}</td>
                        <td class="" hidden>${c.manager}</td>
                        <td class="">${c.paid}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-sm-3 navigation" style="position: fixed;">
    <div class="row filter content">
        <div class="col">
            <div class="form-row">
                <div class="form-group row">
                    <span class="col-form-label col-sm-1 fas fa-user-circle"></span>
                    <div class="col" style="margin:auto">
                        ${cartExtendedDTO.user.fullName}
                        <input readonly hidden type="text" class="col-2 form-control" id="userName"
                               aria-describedby="maxCostHelp" value="${cartExtendedDTO.user.fullName}">
                    </div>
                </div>
                <p id="userEmail" hidden>${cartExtendedDTO.user.email}</p>

                <div class="form-group row">
                    <span class="col-form-label col-sm-1 fas fa-map-marker-alt"></span>
                    <div class="col" style="margin:auto">
                        <c:if test="${! empty cartExtendedDTO.user.activeAddressId}">
                            <p>${cartExtendedDTO.user.activeAddressId.address}</p>
                            <input readonly hidden type="text" class="col-2 form-control" id="address"
                                   aria-describedby="maxCostHelp" value="${cartExtendedDTO.user.activeAddressId.address}">
                        </c:if>
                        <c:if test="${empty cartExtendedDTO.user.activeAddressId}">
                            <textarea required="required" type="text" class="col-12 form-control" id="address"
                                      aria-describedby="maxCostHelp"></textarea>
                        </c:if>
                    </div>
                </div>
                <div class="form-group row">
                    <span class="col-form-label col-sm-1 fas fa-shipping-fast"></span>
                    <div class="col" style="margin:auto">
                        <select id="shipping" class="form-control localSelect">
                            <c:forEach items="${cartExtendedDTO.shippingType}" var="cat">
                                <option value="${cat.id}">${cat.type}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <span class="col-form-label col-sm-1 fas fa-cash-register"></span>
                    <div class="col" style="margin:auto">
                        <select id="payment" class="form-control localSelect" >
                            <c:forEach items="${cartExtendedDTO.paymentType}" var="cat">
                                <option value="${cat.id}">${cat.type}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group col-12">
                    <button id="makeOrder" class="btn btn-primary btn-lg btn-block orderbutton">Make Order</button>
                </div>
            </div>
        </div>
    </div>
</div>

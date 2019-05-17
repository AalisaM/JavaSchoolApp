<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<c:url var="addAction" value="/account/edit" ></c:url>

<div class="notification">
    <c:if test="${! empty message.getErrors()}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message.getErrors()}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${! empty message.getConfirms()}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${message.getConfirms()[0]}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
</div>
<div class="admin-nav row" style="margin-left: 16vw; width: 72vw;">
    <div class="col">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link ${empty addressList ? "active" : ""} userinfoNav" href="#" >User info</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${!empty addressList ? "active" : ""} addresslistNav" href="#">Address List</a>
            </li>
            <li class="nav-item">
                <a class="nav-link  addressnewNav" href="#">Add address</a>
            </li>
        </ul>
    </div>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw; ${!empty addressList ? "display:none" : ""}" id="userInfo">

    <form:form action="${addAction}" commandName="user">
        <div class="form-group row" style="display: none">
            <label for="id" class="col-sm-2 col-form-label">ID</label>
            <div class="col-sm-3">
                <form:input type="text" class="form-control" id="id" name="id"
                       value="${user.id}" placeholder="id"  path="id"/>
                <form:errors path="id" cssClass="error"/>

            </div>
        </div>
        <div class="form-group row">
            <label for="fullName" class="col-sm-2 col-form-label">Full Name</label>
            <div class="col-sm-4">
                <form:input type="text" class="form-control" id="fullName" name="fullName"
                       value="${user.fullName}" placeholder="name" path="fullName"/>
                <form:errors path="fullName" cssClass="error"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">E-mail</label>
            <div class="col-sm-4">
                <form:input type="text" class="form-control" id="email" name="email"
                       value="${user.email}" placeholder="email" path="email" readonly="true"/>
                <form:errors path="email" cssClass="error"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="phone" class="col-sm-2 col-form-label">Phone</label>
            <div class="col-sm-4">
                <form:input type="tel" class="form-control" id="phone" name="phone"
                       value="${user.phone}" placeholder="+78005553535" pattern="\+[0-9]{11}" path="phone"/>
                <form:errors path="phone" cssClass="error"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="birth" class="col-sm-2 col-form-label">Birth</label>
            <div class="col-sm-4">
                <form:input type="date" class="form-control" id="birth" name="birth"
                       value="${user.birth}" placeholder="birth" path="birth"/>
                <form:errors path="birth" cssClass="error"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-6">
                <button id="updateInfoBtn" type="submit" class="col btn btn-primary btn-lg btn-block orderbutton">Save</button>
            </div>
        </div>
    </form:form>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw;${empty addressList ? "display:none" : ""}" id="userAccountAddressList">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th width="80">Address</th>
            <th width="80">Active</th>
            <th width="80">Edit</th>
            <th width="80">Delete</th>
        </tr>
        </thead>
        <c:forEach items="${user.addresses}" var="address">
            <tr>
                <td class="idAddr" style="display: none;">${address.id}</td>
                <td class="addrName"><p class="addrNameValue"> ${address.address}</p>
                    <p class="addrNameEdit" hidden>
                        <input type="text"  class="countryEd"  placeholder="Country">
                        <input type="text"  class="cityEd"  placeholder="City" >
                        <input type="text"  class="indexEd"  placeholder="Index" >
                        <input type="text"  class="streetEd" placeholder="Street"  >
                        <input type="text"  class="houseEd" placeholder="House"  >
                        <input type="text"  class="flatEd"  placeholder="Flat">
                    </p>
                <td><input type="checkbox" name="active" class="activeAddr" value="${user.activeAddressId.id}" ${user.activeAddressId.id == address.id ? 'checked' : ''}>
                </td>
                <td><button class="editAddress btn">
                    <span class="fas fa-pencil-alt"></span></button>
                    <button class="applyEditAddress btn" hidden><span class="fas fa-check-circle"></span></button></td>
                <td><button class="removeAddress btn">
                    <span class="fas fa-trash-alt"></span></button>
                    <button class="cancelEditAddress btn" hidden><span class="fas fa-times-circle"></span></button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw;display: none" id="newAddr">
    <form  id="theForm" method="post" acion="">
        <div class="form-group row">
            <label for="country" class="col-sm-2 col-form-label">Country</label>
            <div class="col-3">
                <input type="text" class="form-control" id="country" name="country"
                       placeholder="country" required>
            </div>
        </div>
        <div class="form-group row">
            <label for="city" class="col-sm-2 col-form-label">City</label>
            <div class="col-3">
                <input type="text" class="form-control" id="city" name="city"
                       placeholder="city" required>
            </div>
        </div>
        <div class="form-group row">
            <label for="index" class="col-sm-2 col-form-label">Index</label>
            <div class="col-3">
                <input type="text" class="form-control" id="index" name="index"
                      placeholder="index" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="street" class="col-sm-2 col-form-label">Street</label>
            <div class="col-3">
                <input type="text" class="form-control" id="street" name="street"
                       placeholder="street" required>
            </div>
        </div>
        <div class="form-group row">
            <label for="house" class="col-sm-2 col-form-label">House</label>
            <div class="col-3">
                <input type="text" class="form-control" id="house" name="house"
                       placeholder="house" required>
            </div>
        </div>
        <div class="form-group row">
            <label for="flat" class="col-sm-2 col-form-label">Flat</label>
            <div class="col-3">
                <input type="text" class="form-control" id="flat" name="flat"
                       placeholder="flat" required>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-2">
                <button id="cancelAddAddress" class="col btn btn-secondary btn-lg">Cancel</button>
            </div>
            <div class="col-3">
                <button id="addAddress" type="submit" class="col btn btn-primary btn-lg orderbutton">Add</button>
            </div>
        </div>
    </form>
</div>




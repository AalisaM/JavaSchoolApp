<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<c:set var="linkName" value="New"/>
<c:set var="listActive" value="active"/>
<c:set var="newActive" value=""/>

<c:if test="${(! empty shipping) && (shipping.id > 0)}">
	<c:set var="linkName" value="Edit"/>
	<c:set var="listActive" value=""/>
	<c:set var="newActive" value="active"/>
</c:if>
<jsp:include page="../notifications.jsp"></jsp:include>

<div class="admin-nav row" style="margin-left: 16vw; width: 72vw;">
	<div class="col">
		<ul class="nav nav-tabs">
			<li class="nav-item">
				<a class="nav-link ${listActive} listProduct" href="#" >List</a>
			</li>
			<li class="nav-item">
				<a class="nav-link ${newActive} newProduct" href="#">${linkName}</a>
			</li>
		</ul>
	</div>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw; display:  ${listActive.length() > 0 ? "" : 'none'}" id="productDiv">

	<h4>Shipping Type list</h4>
	<table class="table">
		<thead class="thead-dark">
		<tr>
			<th scope="col" hidden>Shipping Type ID</th>
			<th scope="col" width="30%">Shipping Type</th>
			<th scope="col" width="10%"></th>
			<th scope="col" width="10%"></th>
		</tr>
		</thead>
		<c:forEach items="${listShippingTypes}" var="shipping">
			<tr>
				<td class="sid" hidden>${shipping.id}</td>
				<td>${shipping.type}</td>
				<td>
					<button class="editShipping btn"> <span class="fas fa-pencil-alt"></span></button>
				</td>
				<td>
					<button class="removeShipping btn"> <span class="fas fa-trash-alt"></span></button>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>


<div class="col content" style="margin-left: 17vw; width: 70vw;display:  ${listActive.length() == 0 ? "" : 'none'}"  id="productNewDiv">
	<h4>Add shipping</h4>
	<div class="nav nav-tabs" style="margin-bottom: 4vh"></div>

	<c:if test="${(! empty shipping) && (shipping.id > 0)}">
		<c:url var="addAction" value="${contextPath}/admin/shipping/edit" ></c:url>
	</c:if>
	<c:if test="${(! empty shipping) && (shipping.id == 0)}">
		<c:url var="addAction" value="${contextPath}/admin/shipping/add" ></c:url>
	</c:if>
	<form:form action="${addAction}" commandName="shipping">
		<div class="form-group row" style="display: none">
			<label for="id" class="col-sm-2 col-form-label">ID</label>
			<div class="col">
				<form:input type="text" class="form-control" id="id" name="id"
					   value="${shipping.id}" placeholder="id" path="id"/>
			</div>
		</div>
		<div class="form-group row">
			<label for="type" class="col-sm-2 col-form-label">Type</label>
			<div class="col-sm-3">
				<form:input type="text" class="form-control" id="type" name="type"
					   value="${shipping.type}" placeholder="type" path="type" required="required" minlength="2"/>
				<form:errors path="type" cssClass="error"/>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-2">
				<button id="shippingAdmin" class="col btn btn-secondary btn-lg">Cancel</button>
			</div>
			<c:if test="${(!empty shipping.type) && (empty add)}">
			<div class="col-3">
				<button class="col updateShipping btn btn-primary btn-lg btn-block orderbutton">Edit shipping</button>
			</div>
			</c:if>
			<c:if test="${empty shipping.type || (! empty add)}">
			<div class="col-3">
				<button class="col addShipping btn btn-primary btn-lg btn-block orderbutton">Add shipping</button>
			</div>
			</c:if>
		</div>
	</form:form>
</div>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

<c:set var="linkName" value="New"/>
<c:set var="listActive" value="active"/>
<c:set var="newActive" value=""/>

<c:if test="${(! empty category) && (category.id > 0)}">
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

	<h4>Categories list</h4>
	<table class="table">
		<thead class="thead-dark">
		<tr>
			<th scope="col" hidden>Category ID</th>
			<th scope="col">Category Name</th>
			<th scope="col">Parent Category</th>
			<th scope="col">Product Amount</th>
			<th scope="col">Edit</th>
			<th scope="col">Delete</th>
		</tr>
		</thead>
		<c:forEach items="${listCategories}" var="category">
			<c:if test="${!((category.parentId == 0) || (empty category.parentId))}">
				<tr>
					<td class="cid" hidden>${category.id}</td>
					<td>${category.title}</td>
					<td>${categoryMap.get(category.parentId)}</td>
					<td>${category.productAmount}</td>
					<td>
						<button class="editCategory btn"><span class="fas fa-pencil-alt"></span></button>
					</td>
					<td>
						<button class="removeCategory btn"> <span class="fas fa-trash-alt"></span></button>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>

<div class="col content" style="margin-left: 17vw; width: 70vw;display:  ${listActive.length() == 0 ? "" : 'none'}"  id="productNewDiv">
	<h4>Add category</h4>
	<div class="nav nav-tabs" style="margin-bottom: 4vh"></div>
	<c:if test="${(! empty category) && (category.id > 0)}">
		<c:url var="addAction" value="${contextPath}/admin/categories/edit" ></c:url>
	</c:if>
	<c:if test="${(! empty category) && (category.id == 0)}">
		<c:url var="addAction" value="${contextPath}/admin/categories/add" ></c:url>
	</c:if>
	<form:form action="${addAction}" commandName="category">
		<div class="form-group row" style="display: none">
			<label for="id" class="col-sm-2 col-form-label">ID</label>
			<div class="col">
				<form:input type="text" class="col-sm-3 form-control" id="id" name="id"
					   value="${category.id}" placeholder="id" path="id"/>
			</div>
		</div>

		<div class="form-group row">
			<label for="name" class="col-sm-2 col-form-label">Name</label>
			<div class="col-3">
				<form:input type="text" class="form-control" id="name" name="name"
					   value="${category.name}" placeholder="name" required="required" minlength="2"
						path="name"/>
				<form:errors path="name" cssClass="error"/>
			</div>
		</div>

		<div class="form-group row">
			<label for="parentId" class="col-sm-2 col-form-label">Parent Category</label>
			<div class="col-3">
				<select id="parentId" class="form-control" name="parentId">
					<c:forEach items="${categoryMap}" var="cat">
						<option value="${cat.key}" ${cat.key == category.parentId ? 'selected' : ''}>${cat.value}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-2">
				<button id="categoriesAdmin" class="col btn btn-secondary btn-lg">Cancel</button>
			</div>
			<c:if test="${(!empty category.name) && (empty add)}">
				<div class="col-3">
					<button class="col updateCategory btn btn-primary btn-lg btn-block orderbutton">Edit Category</button>
				</div>
			</c:if>
			<c:if test="${empty category.name || (! empty add)}">
				<div class="col-3">
					<button class="col addCategory btn btn-primary btn-lg btn-block orderbutton">Add Category</button>
				</div>
			</c:if>
		</div>
	</form:form>
</div>

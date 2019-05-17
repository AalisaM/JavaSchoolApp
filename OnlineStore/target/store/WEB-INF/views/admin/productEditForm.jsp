<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>
<c:set var="currentPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="widthval" value="60"/>
<c:set var="marginl" value="20"/>
<c:if test="${(! empty product) && (product.id > 0)}">
    <c:url var="addAction" value="/admin/products/edit"></c:url>
</c:if>
<c:if test="${(! empty product) && (product.id == 0)}">
    <c:set var="widthval" value="68"/>
    <c:set var="marginl" value="17"/>
    <c:url var="addAction" value="/admin/products/add"></c:url>
</c:if>
<div class="col content" style=" margin-left: ${marginl}vw; width: ${widthval}vw;" id="productProfData">
<jsp:include page="../notifications.jsp"></jsp:include>
<form:form action="${addAction}" commandName="product" enctype="multipart/form-data"  id="productForm">
    <div class="form-group row">
        <label for="name" class="col-sm-1 col-form-label">Title</label>
        <div class="col">
            <form:input type="text" class="form-control" id="name" name="name"
                       value="${product.name}" placeholder="type" required="required" path="name" minlength="2"/>
            <form:errors path="name" cssClass="error"/>

            <form:input type="text" class="form-control" id="id" name="id"
                   value="${product.id}" placeholder="id" hidden="hidden" path="id" />
        </div>
    </div>
    <div class="nav nav-tabs" style="margin-bottom: 4vh"></div>

    <div class="form-group row">
        <label for="description" class="col-sm-2 col-form-label">Description</label>
        <div class="col">
            <form:textarea class="form-control" id="description" name="description" path="description" minlength="10" required="required"
            ></form:textarea>
            <form:errors path="description" cssClass="error"/>
        </div>
    </div>

    <div class="form-group row">
        <label for="rules" class="col-sm-2 col-form-label">Rules</label>
        <div class="col">
            <form:textarea class="form-control" id="rules" name="rule" path="rule"></form:textarea>
            <form:errors path="rule" cssClass="error"/>

        </div>
    </div>


    <div class="form-group row"  style="margin-top: 4vh;">
        <label class="col-sm-2 col-form-label">Measurements</label>
        <div class="form-group col-5">
            <form:input type="number" class="form-control" id="weight" name="weight"
                   value="${product.weight}" placeholder="weight" required="required" path="weight"/>
            <form:errors path="weight" cssClass="error"/>

            <small class="form-text text-muted">Weight</small>
        </div>
        <div class="form-group col-5">
            <form:input type="number" class="form-control" id="volume" name="volume"
                   value="${product.volume}" placeholder="volume" required="required" path="volume"/>
            <form:errors path="volume" cssClass="error"/>

            <small class="form-text text-muted">Volume</small>
        </div>
    </div>

    <div class="form-group row">
         <label  class="col-sm-2 col-form-label">Players amount</label>
        <div class="form-group col-5">
            <form:input type="number" class="form-control" id="minPlayerAmount" name="minPlayerAmount"
                     value="${product.minPlayerAmount}" placeholder="min player amount" required="required"
            path="minPlayerAmount" min="1"/>
            <form:errors path="minPlayerAmount" cssClass="error"/>

            <small class="form-text text-muted">Min</small>
        </div>
        <div class="form-group col-5">
            <form:input type="number" class="form-control" id="maxPlayerAmount" name="maxPlayerAmount" min="1"
                   value="${product.maxPlayerAmount}" placeholder="max player amount" required="required" path="maxPlayerAmount"/>
            <form:errors path="maxPlayerAmount" cssClass="error"/>

            <small class="form-text text-muted">Max</small>
        </div>
    </div>

      <div class="form-group row">
        <label  class="col-sm-2 col-form-label">Product info</label>
        <div class="form-group col-2">
            <form:input type="number" class="form-control" id="price" name="price"
                       value="${product.price}" placeholder="price" required="required" path="price" min="1"/>
            <form:errors path="price" cssClass="error"/>

            <small class="form-text text-muted">Price</small>
        </div>
          <div class="form-group col-3">
              <form:input type="number" class="form-control" id="amount" name="amount"
                     value="${product.amount}" placeholder="amount" required="required" path="amount"/>
              <form:errors path="amount" cssClass="error"/>

              <small class="form-text text-muted">Amount</small>
          </div>
          <div class="form-group col-3">
              <form:select id="category" class="form-control" path="category">
                  <c:forEach items="${listCategories}" var="cat">
                      <option value="${cat.id}"  ${cat.id == product.category.id ? 'selected' : ''}>${cat.title}</option>
                  </c:forEach>
              </form:select>
              <form:errors path="category" cssClass="error"/>
              <small class="form-text text-muted">Category</small>
          </div>
          <div class="form-group col-2">
              <button id="labelLoadButton" class="btn btn-primary orderbutton">Browse image</button>
              <input type="file" class="form-control" id="file" name="file"
                     value="${product.imageSource}" placeholder="type" style="display: none;"/>
          </div>


      </div>

    <div class="form-group row">
        <div class="form-group col-6">
            <button id="cancelProductEdit" class="btn btn-secondary" style="width: 100%" >Cancel</button>
        </div>
        <div class="form-group col-6">
            <c:if test="${(! empty product) && (product.id > 0)}">
                <button id="applyProductEdi" class="btn btn-primary orderbutton" style="width: 100%">Apply</button>
            </c:if>
            <c:if test="${( empty product) || (product.id == 0)}">
                <button id="addProduct" class="btn btn-primary orderbutton" style="width: 100%">Add</button>
            </c:if>
        </div>
    </div>
</form:form>
</div>

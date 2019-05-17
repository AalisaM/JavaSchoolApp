<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-nav row" style="margin-left: 16vw; width: 72vw;">
    <div class="col">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active listProduct" href="#" >List</a>
            </li>
            <li class="nav-item">
                <a class="nav-link newProduct" href="#" >New</a>
            </li>
        </ul>
    </div>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw; " id="productDiv">
    <jsp:include page="productList.jsp"/>
</div>
<div  id="productNewDiv" style="display: none;">
    <jsp:include page="productEditForm.jsp"/>
</div>

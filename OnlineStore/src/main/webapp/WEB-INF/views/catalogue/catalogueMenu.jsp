<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<div class="col-sm-3 navigation" style="position: absolute; overflow: auto; margin-top: 20vh">
    <ul class="nav nav-pills flex-column menu">
        <li class="nav-item">
            <a id="-1" class="nav-link categorylink active" href="#">Board games</a>
            <ul class="nav flex-column subMenu">
                    <c:forEach items="${listCategories}" var="cat">
                        <c:if test="${! ((empty cat.parentId) || (cat.parentId == 0))}">
                            <li class="nav-item">
                                <a id="${cat.id}" class="nav-link categorylink" href="#">${cat.title}</a>
                            </li>
                        </c:if>
                    </c:forEach>
            </ul>

        </li>
    </ul>
    <div class="row filter" style="margin-top: 4vh;">
        <div class="col">
            <form id="filterForm" action="${contextPath}/filter">
                <div class="form-row">
                    <%--<div class="form-group col-6">--%>
                        <%--<select id="categoryFilter" class="form-control">--%>
                            <%--<c:forEach items="${listCategories}" var="cat">--%>
                                <%--<option value="${cat.id}">${cat.title}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                    <div class="form-group col-6">
                        <input type="number" class="form-control filterInput" id="minPriceFilter" name="minCost"
                               aria-describedby="minCostHelp">
                        <small class="form-text text-muted">Min price</small>
                    </div>
                    <div class="form-group col-6">
                        <input type="number" class="form-control filterInput" id="priceFilter" name="maxCost"
                               aria-describedby="maxCostHelp">
                        <small class="form-text text-muted">Max price</small>
                    </div>

                    <div class="form-group col-6">
                        <input type="number" class="form-control filterInput" id="minPlayerAmountFilter" name="maxCost"
                               aria-describedby="maxCostHelp">
                        <small class="form-text text-muted">Min player amount</small>
                    </div>
                    <div class="form-group col-6">
                        <input type="number" class="form-control filterInput" id="maxPlayerAmountFilter" name="maxCost"
                               aria-describedby="maxCostHelp">
                        <small class="form-text text-muted">Max player amount</small>
                    </div>
                    <div class="col-6">
                        <button id="applyFilter" class="btn btn-lg btn-primary orderbutton">Filter</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

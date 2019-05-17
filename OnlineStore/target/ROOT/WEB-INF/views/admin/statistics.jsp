<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

<div class="row admin-nav" style="margin-left: 16vw; width: 72vw;">
    <div class="col">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active curStatNav" href="#" >Current</a>
            </li>
            <li class="nav-item">
                <a class="nav-link anotherStatNav" href="#" >Another</a>
            </li>
            <li class="nav-item">
                <a class="nav-link reportNav" href="#" >Report</a>
            </li>
        </ul>
    </div>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw;margin-bottom: 4vw;display: none"  id="periodForm">
    <h4>Select period</h4>
    <div class="form-group row">
        <div class="col-3">
            <input type="date" class="form-control" id="fromDate" name="id" required>
        </div>
            <p>-</p>
        <div class="col-3">
            <input type="date" class="form-control" id="toDate" name="id" required>
        </div>
        <div class="col">
            <button class="orderbutton btn-primary btn" id="applySpecStat">Apply</button>
        </div>
    </div>
</div>
<div class="col content" style="margin-left: 17vw; width: 70vw;margin-bottom: 4vw;display: none"  id="pdfForm">
    <h4>Select period</h4>
    <form action="${contextPath}/admin/statistics/formPdfReport" type="post" target="_blank">
        <div class="form-group row">
            <div class="col-3">
                <input type="date" class="form-control" id="from" name="from" required>
            </div>
            <p>-</p>
            <div class="col-3">
                <input type="date" class="form-control" id="to" name="to" required>
            </div>
            <div class="col">
                <button class="orderbutton btn-primary btn" type="submit">Apply</button>
            </div>
        </div>
    </form>
</div>
<div id="statsJSP">
    <jsp:include page="adminStatistics.jsp">
        <jsp:param name="revenueWeek" value="${revenueWeek}"/>
        <jsp:param name="userTop" value="${userTop}"/>
        <jsp:param name="productTop" value="${productTop}"/>
    </jsp:include>
</div>

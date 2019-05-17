<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="notification">
    <c:if test="${! empty message.getErrors()}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message.getErrors()[0]}
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

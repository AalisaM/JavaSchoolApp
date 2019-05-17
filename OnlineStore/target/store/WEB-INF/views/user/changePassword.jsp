<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col content" style="margin-left: 17vw; width: 70vw;">
    <div class="notification" id="error">
        <c:if test="${! empty message.getErrors()}">
            <c:forEach items="${message.getErrors()}" var="m">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${m}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:forEach>
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

    <div class="form-group row">
        <label for="oldpass" class="col-sm-2 col-form-label">Old password</label>
        <div class="col-3">
            <input type="password" class="form-control" id="oldpass" name="oldpassword"
                    placeholder="oldpass" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="pass" class="col-sm-2 col-form-label">New password</label>
        <div class="col-3">
            <input type="password" class="form-control" id="pass" name="password"
                    placeholder="password" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="passConfirm" class="col-sm-2 col-form-label">Confirm new password</label>
        <div class="col-3">
            <input type="password" class="form-control" id="passConfirm" name="passConfirm"
                   placeholder="passConfirm">
        </div>
    </div>
    <button id="updatePswd" type="submit" class="col-5 btn btn-primary btn-lg btn-block orderbutton">Update password</button>
</div>

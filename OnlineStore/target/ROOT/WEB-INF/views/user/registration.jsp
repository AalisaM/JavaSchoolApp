<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <c:set var="contextPath" value="${pageContext.request.getContextPath()}"/>

    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/assets/css/local.css">
    <link rel="stylesheet" href="${contextPath}/assets/css/all.css">

</head>
<body>
<div class="container-fluid" >
    <div class="site-content">
        <jsp:include page="../header.jsp"/>
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
        <div class="row site-padding" style="margin-top: 20vh">
            <div class="panel-body">
                <div class="content col-8" style="margin-left:25vw">
                    <h4 style="margin-bottom: 4vh;text-align:center;">Sign up</h4>
                    <c:url var="addAction" value="/registration/add" ></c:url>
                <form:form action="${addAction}" commandName="user">
                    <div class="form-group row">
                        <label for="fullName" class="col-sm-3 col-form-label">Full Name</label>
                        <div class="col">
                            <form:input type="text" class="form-control" id="fullName" name="fullName"
                                   value="${user.fullName}" placeholder="Full Name" path="fullName" required="required"/>
                            <form:errors path="fullName" cssClass="error"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="email" class="col-sm-3 col-form-label">E-mail</label>
                        <div class="col">
                            <form:input type="text" class="form-control" id="email" name="email"
                                   value="${user.email}" placeholder="E-mail" path="email" required="required"/>
                            <form:errors path="email" cssClass="error"/>

                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="phone" class="col-sm-3 col-form-label">Phone</label>
                        <div class="col">
                            <form:input type="tel" class="form-control" id="phone" name="phone"
                                   value="${user.phone}" placeholder="+78005553535" pattern="\+[0-9]{11}" path="phone" required="required"/>
                            <form:errors path="phone" cssClass="error" />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="birth" class="col-sm-3 col-form-label">Birth</label>
                        <div class="col">
                            <form:input type="date" class="form-control" id="birth" name="birth"
                                   value="${user.birth}" placeholder="Birthday" path="birth" required="required"/>
                            <form:errors path="birth" cssClass="error"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-sm-3 col-form-label">Password</label>
                        <div class="col">
                            <form:input type="password" class="form-control" id="password" name="password"
                                   placeholder="Password" path="password" required="required"/>
                            <form:errors path="password" cssClass="error"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="passConfirm" class="col-sm-3 col-form-label">Confirm password</label>
                        <div class="col">
                            <form:input type="password" class="form-control" id="passConfirm" name="passConfirm"
                                   placeholder="Password confirmation" path="confirmPassword" required="required"/>
                            <form:errors path="confirmPassword" cssClass="error"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col">
                            <button class="col btn-primary btn btn-lg orderbutton" type="submit" id="signup">Sign Up</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
        </div>
    </div>
</div>
</body>
</html>

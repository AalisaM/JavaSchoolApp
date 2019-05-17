<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="col content products-content" style="margin-top: 20vh; margin-left: 25vw; width: 60vw;">
    <div class="row justify-content-start product-list">
        <c:forEach items="${listProducts}" var="product">
            <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
                <div class="card">
                    <a href="${contextPath}/product/${product.id}">
                        <div class="card-body">
                            <c:if test="${product.imageSource.length() > 0}">
                                <div style="text-align: center">
                                    <div class="crop">
                                        <img alt="product_${product.id}" src=" http://${ip}:8190/icon${product.imageSource}"/>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${product.imageSource.length() == 0 || empty product.imageSource}">
                                <div style="text-align: center">
                                    <div class="crop">
                                        <img alt="product_${product.id}" src="${contextPath}/assets/images/product.png"/>
                                    </div>
                                </div>
                            </c:if>
                                <h3 class="card-title productname">${product.name}</h3>
                                <h6 class="cart-subTitle productname">&nbsp</h6>

                                <p class="card-text">
                                    <c:if test="${product.amount > 0}">
                                        <button class="btn btn-primary orderbutton addToCart"><span class="fas fa-cart-arrow-down"></span>
                                                ${product.price} <span class="fas fa-ruble-sign"></span>
                                        </button>
                                    </c:if>
                                    <c:if test="${product.amount <= 0}">
                                        <button class="btn btn-primary orderbutton addToCart" disabled><span class="fas fa-cart-arrow-down"></span>
                                                ${product.price} <span class="fas fa-ruble-sign"></span>
                                        </button>
                                    </c:if>
                                 </p>
                            <p hidden class="productprice">${product.price}</p>
                            <p hidden class="productid">${product.id}</p>
                            <div style="text-align: center">

                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

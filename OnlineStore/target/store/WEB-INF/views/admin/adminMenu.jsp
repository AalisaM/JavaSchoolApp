<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-sm-2 navigation" style="position: fixed;">
    <ul class="nav nav-pills flex-column menu">
        <li class="nav-item">
            <a class="nav-link disabled">Admin menu</a>
        </li>
            <li class="nav-item">
                <a id="accEdit" class="nav-link disabled" href="#">Orders</a>
                <ul class="nav flex-column subMenu">
                    <li class="nav-item">
                        <a class="nav-link" href="#" id="unpaid">Unpaid</a>
                        <a class="nav-link" href="#" id="paid">Waiting for delivery</a>
                        <a class="nav-link" href="#" id="delivered">Delivered</a>
                    </li>
                </ul>
                <a id="statistics" class="nav-link" href="#">Statistics</a>
                <a id="productsAdminList" class="nav-link" href="#">Products</a>
                <a id="shippingAdmin" class="nav-link" href="#">Shipping types</a>
                <a id="categoriesAdmin" class="nav-link" href="#">Categories</a>
                <a id="users" class="nav-link" href="#">Users</a>
            </li>
    </ul>
</div>
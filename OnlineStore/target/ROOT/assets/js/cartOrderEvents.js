
/**
 * saves cookie in btoa string for cookie visibility in controller:
 * as i need json,but backend doesnot see stringified json in cookies
 * decided to store it in btoa page
 * */
var createCookie = function(name, value, days) {
    var expires;
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toGMTString();
    }
    else {
        expires = "";
    }
    document.cookie = name + "=" + btoa(value) + expires + "; path=/";
};

/**
 * gets cookie item by name*/
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        var c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            var c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = document.cookie.length;
            }
            return atob(unescape(document.cookie.substring(c_start, c_end)));
        }
    }
    return "";
}

/***
 * sets new cart in cookie
 * */
function setCartForCookie(){
    var jsonCart = {};
    jsonCart["totalAmount"] = parseInt($("#lblCartCount").text().trim(),10);
    var price = parseInt($("#cartPrice").text().trim(),10);
    jsonCart["totalPrice"] = ((isNaN(price) || price  == null) ? 0 : price) ;

    jsonCart["cartItem"] = {};
    jsonCart["cartItemArr"] = [];
    createCookie("cartItem", JSON.stringify(jsonCart), 28)
}

/**
 * returns cart from cookie
 * */
function getCartFromCookie(){
    var cookieRaw = getCookie("cartItem");

    if (cookieRaw.length){
        return JSON.parse(getCookie("cartItem"));
    }else {
        var jsonCart = {};
        var amount =  parseInt($("#lblCartCount").text().trim(),10);
        jsonCart["totalAmount"] = ((isNaN(amount) || amount  == null) ? 0 : amount);
        var price = parseInt($("#cartPrice").text().trim(),10);
        jsonCart["totalPrice"] = ((isNaN(price) || price  == null) ? 0 : price) ;
        jsonCart["cartItem"] = {};
        jsonCart["cartItemArr"] = [];
        createCookie("cartItem", JSON.stringify(jsonCart), 28);
        return jsonCart;
    }
}

function initCartForAnon() {
    var jsonCart = getCartFromCookie();
    $("#lblCartCount").text( jsonCart["totalAmount"]);
    $("#cartPrice").text(jsonCart["totalPrice"]);
}

if (!$("#loggedUserName").length){
    initCartForAnon();
}

/**
 * creates cart or gets it from cookie for anonymus
 * */
function sendPostRequest(url, toreload, objectToInsert, data, toSendCartReq=false, toSendClearCart=false){
    $.ajax({
        url: $("#warPath").text() + url,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: data,
        success: function(response) {
            if (objectToInsert){
                $(objectToInsert).html(response);
            }
            if (toSendClearCart) {
                sendGetRequest('/clearCart',false, "#cartTemplate");
            }
            if (toSendCartReq){
                sendCartPageRequest();
            }
            if (toreload){
                document.location.reload(toreload);
            }
            if (response.redirect) {
                window.location.href = response.redirect;
            }
        },
        error: function (e) {
            console.log(e)
        }
    })
}

function sendGetRequest(url,ignoreResp=false,objectToInsert="body"){
    $.ajax({
        url: $("#warPath").text() + url,
        type: 'GET',
        success: function (response) {
            if (!ignoreResp) {
                if (response.redirect) {
                    window.location.href = response.redirect;
                } else {
                    $(objectToInsert).html(response);
                }
            }
        },error: function () {
        }
    });
}

/**
 * increments product item in cart
 * */
function incProductById(id,name,price, jsonCart){
    jsonCart["totalAmount"] +=1;
    jsonCart["totalPrice"] += price;
    var p = jsonCart["cartItem"][id]["price"];
    var a = jsonCart["cartItem"][id]["amount"];
    jsonCart["cartItem"][id] = {"product_id" : id, "product_name" : name, "amount" : a + 1, "price" : p + price};
    jsonCart["cartItemArr"] = [];
    $.each(jsonCart["cartItem"],function(k,v){jsonCart["cartItemArr"].push(v)});
    $("#lblCartCount").text( jsonCart["totalAmount"]);
    $("#cartPrice").text(jsonCart["totalPrice"]);
    return jsonCart;
}

/**
 * decriments product in cart
 * **/
function decProductById(id,name,price, jsonCart){
    console.log(id);

    console.log(name);
    console.log(price);
    console.log(jsonCart);

    var p = jsonCart["cartItem"][id]["price"];
    var a = jsonCart["cartItem"][id]["amount"];
    if (a == 1){
        removeProductAtAll(id,jsonCart);
        //delete jsonCart["cartItem"][id];
        return jsonCart;
    }else{
        jsonCart["totalAmount"] -=1;
        jsonCart["totalPrice"] -= price;

        jsonCart["cartItem"][id] = {"product_id" : id, "product_name" : name, "amount" : a - 1, "price" : p - price};
        jsonCart["cartItemArr"] = [];
        $.each(jsonCart["cartItem"],function(k,v){jsonCart["cartItemArr"].push(v)});
        $("#lblCartCount").text( jsonCart["totalAmount"]);
        $("#cartPrice").text(jsonCart["totalPrice"]);
        console.log(jsonCart);
        return jsonCart;
    }
}

/**
 * removes product from cart
 * */
function removeProductAtAll(id, jsonCart){
    console.log(id);
    console.log(jsonCart);

    var p = jsonCart["cartItem"][id]["price"];
    var a = jsonCart["cartItem"][id]["amount"];
    console.log(p);
    console.log(a);

    jsonCart["totalAmount"] -= a;
    jsonCart["totalPrice"] -= p;
    delete jsonCart["cartItem"][id];
    jsonCart["cartItemArr"] = [];
    $.each(jsonCart["cartItem"],function(k,v){jsonCart["cartItemArr"].push(v)});
    console.log(jsonCart);
    $("#lblCartCount").text( jsonCart["totalAmount"]);
    $("#cartPrice").text(jsonCart["totalPrice"]);
    return jsonCart;

}
/**
 * adds product to cart
 * */
function addNewProduct(id,name,price, jsonCart){
    jsonCart["totalAmount"] +=1;
    jsonCart["totalPrice"] += price;
    jsonCart["cartItem"][id] = {"product_id" : id, "product_name" : name, "amount" : 1, "price" : price};
    jsonCart["cartItemArr"] = [];
    $.each(jsonCart["cartItem"],function(k,v){jsonCart["cartItemArr"].push(v)});
    $("#lblCartCount").text( jsonCart["totalAmount"]);
    $("#cartPrice").text(jsonCart["totalPrice"]);
    return jsonCart;
}


/**
 * clears cookie
 * */
function clearecookie(){
    javascript:(function(){document.cookie.split(";").forEach(function(c) { document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); }); })();
}

/**
 * processes increment product event : adds or edit
 * */
function plusAnonProduct(obj){
    var id = $($(obj).closest("div.card-body").find(".productid")[0]).text();
    var pn = $($(obj).closest("div.card-body").find(".productname")[0]).text();
    var pp = parseInt($($(obj).closest("div.card-body").find(".productprice")[0]).text(),10);
    var jsonCart = getCartFromCookie();
    if (jsonCart["cartItem"][id] == null){
        jsonCart = addNewProduct(id,pn,pp,jsonCart);
    }else{
        jsonCart = incProductById(id,pn,pp,jsonCart);
    }

    createCookie("cartItem", JSON.stringify(jsonCart), 28);
}

/**
 * processes decriment product event:
 * */
function minusAnonProduct(obj){
    var id = $($(obj).closest("div.card-body").find(".productid")[0]).text();
    var pn = $($(obj).closest("div.card-body").find(".productname")[0]).text();
    var pp = parseInt($($(obj).closest("div.card-body").find(".productprice")[0]).text(),10);
    var jsonCart = getCartFromCookie();
    if (jsonCart["cartItem"][id] == null){
        return;
    }else{
        jsonCart = decProductById(id,pn,pp,jsonCart);
    }
    createCookie("cartItem", JSON.stringify(jsonCart), 28);
}

/**
 * sends cart html update request
 * */
function sendCartPageRequest(){
    sendPostRequest('/cart/processAnonymousCart', false,"#currentCart",getCookie("cartItem"));
}

/**
 * adds product to cart for user
 * */
$("body").on("click",".addToCart", function (e) {
    console.log("add to cart");
    e.preventDefault();
    if ($("#loggedUserName").length){
        console.log("logged");
        var data = JSON.stringify({
            "id": $($(this).closest("div.card-body").find(".productid")[0]).text()
        });
        sendPostRequest('/cart/addToCart/',false,"#cartTemplate",data);
    }else{
        console.log("try to add notlogged");
        console.log($(this)[0]);
        plusAnonProduct(this);
    }
});

/**
 * removes product from cart
 * */
$("body").on("click", ".removeFromCart",function (e) {
    e.preventDefault();
    //todo
    if ($("#loggedUserName").length){
        var data = JSON.stringify({
            "id": $($(this).closest("div.card-body").find(".productid")[0]).text(),
            "amount" : 1
        });

        sendPostRequest('/cart/removeFromCart',false,"#cartTemplate",data);

    }else{
        minusAnonProduct(this);
    }
});

/**
 * prccesses dec event in cart page
 * */
$("body").on("click",".cartDecItem",function(){
    if ($("#loggedUserName").length){
        var data = JSON.stringify({
            "id": $($(this).closest("tr").find(".productid")[0]).text(),
            "amount" : 1
        });

        sendPostRequest('/cart/removeFromCart',false,"#cartTemplate",data,true);
    }
});
/**
 * prccesses inc event in cart page
 * */
$("body").on("click",".cartIncItem",function(){
    if ($("#loggedUserName").length){
        console.log("logged");

        var data = JSON.stringify({
            "id": $($(this).closest("tr").find(".productid")[0]).text()
        });

        sendPostRequest('/cart/addToCart/',false,"#cartTemplate",data,true);
    }
});

/**
 * prccesses delete event in cart page
 * */

$("body").on("click",".deleteCartItem",function(){
    if ($("#loggedUserName").length){
        var data = JSON.stringify({
            "id": $($(this).closest("tr").find(".productid")[0]).text(),
            "amount" :  $($(this).closest("tr").find(".amount")[0]).text()
        });
        console.log(data);
        sendPostRequest('/cart/removeFromCart/',false,"#cartTemplate",data,true);
    }else {
        var jsonCart = removeProductAtAll($($(this).closest("tr").find(".productid")[0]).text(), getCartFromCookie());
        createCookie("cartItem", JSON.stringify(jsonCart), 28);
        sendCartPageRequest();
    }
});

/**
 * prccesses dec event in cart page for anon user
 * */

$("body").on("click",".cartDecItemAnon",function(){
    var id = $($(this).closest("tr").find(".productid")[0]).text();
    var pn = $($(this).closest("tr").find("td.productname")[0]).text();
    var amountObj = parseInt($($(this).closest("tr").find(".amount")[0]).text(),10);
    var pp = parseInt($($(this).closest("tr").find("td.productprice")[0]).text(),10) / amountObj;
    var jsonCart = getCartFromCookie();
    if (jsonCart["cartItem"][id] == null){
        return;
    }else{
        jsonCart = decProductById(id,pn,pp,jsonCart);
    }
    createCookie("cartItem", JSON.stringify(jsonCart), 28);
    sendCartPageRequest();
});

/**
 * prccesses inc event in cart page for anon
 * */
$("body").on("click",".cartIncItemAnon",function(){
    var id = $($(this).closest("tr").find(".productid")[0]).text();
    var pn = $($(this).closest("tr").find("td.productname")[0]).text();
    var amountObj = parseInt($($(this).closest("tr").find(".amount")[0]).text(),10);
    var pp = parseInt($($(this).closest("tr").find("td.productprice")[0]).text(),10) / amountObj;
    var jsonCart = getCartFromCookie();
    if (jsonCart["cartItem"][id] == null){
        jsonCart = addNewProduct(id,pn,pp,jsonCart);
    }else{
        jsonCart = incProductById(id,pn,pp,jsonCart);
    }
    createCookie("cartItem", JSON.stringify(jsonCart), 28);
    sendCartPageRequest();
});

/**
 * sends make order request with order dto
 * */
$("body").on("click", "#makeOrder",function (e) {
    showInvalidInputs();
    if ($(".is-invalid").length) {
        return;
    }
    var data ={
        "id": 0,
        "email": $("#userEmail").text(),
        "address": $("#address").val(),
        "amount" : parseInt($("#lblCartCount").text(),10),
        "paymentType": {
            "id" :  parseInt($("#payment").find("option:selected").val(),10),
            "type" : $("#payment").find("option:selected").text()
        },"paymentStatus": {
            "id": 1,
            "status": "not paid"
        },"shippingType": {
            "id" :  parseInt($("#shipping").find("option:selected").val(),10),
            "type" : $("#shipping").find("option:selected").text()
        },"orderStatus": {
            "id": 1,
            "status": "not paid"
        },"totalPrice":  parseInt($("#cartPrice").text().trim(),10),
        "orderProducts" : []
    };
    $("tr.productItem").each(function() {
        var productid = parseInt($($(this).find(".productid")[0]).text(),10);
        var amount = parseInt($($(this).find(".amount")[0]).text(),10);
        var price = parseInt($($(this).find(".productprice")[0]).text(),10);
        var productName = $($(this).find(".productname")[0]).text();
        var obj = {"productid" : productid, "amount" : amount, "price" : price, "productName" : productName}
        data["orderProducts"].push(obj);
    });
    sendPostRequest('/cart/makeOrder',false,"body",JSON.stringify(data));
});

function formOrderData(amount="#lblCartCountDTO", orderid=3, orderstatus="waiting for delivery",price="#cartPriceDTO"){
    var data ={
        "id": parseInt($("#orderID").text(),10),
        "email": $("#userEmail").text(),
        "address": $("#address").text(),
        "amount" : parseInt($(amount).text(),10),
        "paymentType": {
            "id" : parseInt($("#paymentid").text(),10),
            "type" : $("#payment").text()
        },"paymentStatus": {
            "id": parseInt($("#statusid").text(),10),
            "status": $("#status").text()
        },"shippingType": {
            "id" : parseInt($("#shippingid").text(),10),
            "type" : $("#shipping").text()
        },"orderStatus": {
            "id": orderid,
            "status": orderstatus
        },"totalPrice": $("#cartPrice").text(),
        "orderProducts" : []
    };
    $("tr.productItem").each(function( index ) {
        var productid = parseInt($($(this).find(".productid")[0]).text(),10);
        var amount = parseInt($($(this).find(".amount")[0]).text(),10);
        var price = parseInt($($(this).find(".productprice")[0]).text(),10);
        var productName = $($(this).find(".productname")[0]).text();
        var obj = {"productid" : productid, "amount" : amount, "price" : price, "productName" : productName}
        data["orderProducts"].push(obj);
    });
    return data;
}

/**
 * prccesses cancel order request
 * */
$("body").on("click", "#cancel",function (e) {
    var data = formOrderData("#lblCartCount",
                            parseInt($("#orderstatusid").text(),10),
                            $("#orderstatus").text(),
                            "#cartPrice");
    sendPostRequest('/cart/cancelOrder',false,"body",JSON.stringify(data));

});

if ($("#approve").length) {
    $(window).bind('beforeunload', function (e) {
        $("#cancel").trigger("click");
    });
}

/**
 * prccesses approve order event in cart page
 * */

$("body").on("click", "#approve",function (e) {
    sendPostRequest('/cart/editOrder',false,
        "body",JSON.stringify(formOrderData()),false,true);
});
/**
 * prccesses payment event in cart page
 * */
$("body").on("click", "#pay",function (e) {
    swal({
        title: "Do you want to pay for this order?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                $("#status").text("paid");
                $("#statusid").text(2);
                sendPostRequest('/cart/editOrder',false,
                    "body",JSON.stringify(formOrderData()),false,true);
            }
        })
});

/** products page*/
$("body").on("click",".ruleNav",function(){
    $("#disc").hide();
    $("#rule").show();
});

$("body").on("click",".descNav",function(){
    $("#rule").hide();
    $("#disc").show();
});


$("body").on("click",".productEdit",function(){
    $("#addProdFromProfile").hide();
    $("#loadIMG").show();
    $("#productDescription").hide();
    $("#productEditForm").show();

});

$("body").on("click",".productProfile",function(){
    $("#loadIMG").hide();
    $("#addProdFromProfile").show();
    $("#productEditForm").hide();
    $("#productDescription").show();
});

$("body").on("change","#file",function(){
    $("#labelLoadButton").addClass("btn-primary-blue");
    $("#labelLoadButton").text("Uploaded")
});

$("body").on("click", ".categorylink",function (e) {
    e.preventDefault();
    $(".active").removeClass("active");
    $(this).addClass("active");
    $(".filterInput").val("");
    $("#applyFilter").trigger("click");
})
/**
 * sends filter products request
 * */
$("body").on("click", "#applyFilter",function (e) {
    e.preventDefault();
    var data = JSON.stringify({
        "minPrice" : $("#minPriceFilter").val(),
        "price": $("#priceFilter").val(),
        "minPlayer": $("#minPlayerAmountFilter").val(),
        "maxPlayer": $("#maxPlayerAmountFilter").val(),
        "category_id":  $(".categorylink.active").attr('id')
    });

    sendPostRequest('/filter',false,"#productListDiv",data);
});

$("body").on("click","#addProdFromProfile", function (e) {
    e.preventDefault();
    //todo
    if ($("#loggedUserName").length){
        console.log("logged");

        var data = JSON.stringify({
            "id": $($(".productid")[0]).text()
        });

        sendPostRequest('/cart/addToCart/',false,"#cartTemplate",data)

    }else{
        console.log("try to add notlogged");
        console.log($(this)[0]);
        var id = $($(".productid")[0]).text();
        var pn = $($(".productname")[0]).text();
        var pp = parseInt($($(".productprice")[0]).text(),10);
        var jsonCart = getCartFromCookie();
        if (jsonCart["cartItem"][id] == null){
            jsonCart = addNewProduct(id,pn,pp,jsonCart);
        }else{
            jsonCart = incProductById(id,pn,pp,jsonCart);
        }
        createCookie("cartItem", JSON.stringify(jsonCart), 28);
    }
});

$("body").on("click","#editOrderStatusProfilePage",function(){
    $("#orderStatusProfile").hide();
    $("#orderStatusSelect").show();
});

$("body").on("click","#applyStatusChange",function(){
    var newText = $($(".orderStatusSelect")[0]).find("option:selected").text();
    var newRole =  $($(".orderStatusSelect")[0]).val();

    var id = parseInt($("#id").text(),10);

    var data ={
        "id": id,
        "paymentStatus": {
            "id": parseInt($("#pStatusId").text(),10),
            "status": $("#pStatusVal").text().trim()
        },"orderStatus": {
            "id": newRole,
            "status": newText
        },
        "email": "",
        "address": "",
        "amount" : 0,
        "paymentType": {
            "id" : 0,
            "type" : ""
        },"shippingType": {
            "id" : 0,
            "type" :""
        },"totalPrice":  0,
        "orderProducts" : []
    };

    console.log(data);
    var method ='/admin/editOrder';
    if (newRole == 6){
        method = '/cart/cancelOrder';
    }
    sendPostRequest(method,true,"",JSON.stringify(data));

});

$("body").on("click","#cancelStatusChange",function(){
    $("#orderStatusSelect").hide();
    $("#orderStatusProfile").show();
});

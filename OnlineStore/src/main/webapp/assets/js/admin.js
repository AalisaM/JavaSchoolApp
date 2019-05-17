/**
 * Calls updaid orders page for admin
 * */

$("body").on("click", "#labelLoadButton", function (e) {
    e.preventDefault();
    $("#file").trigger("click");
});


function sendAdminPost(url,data,href){
    $.ajax({
        url: $("#warPath").text() + url,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: data,
        success: function (s) {
            $("#curModule").html(s);
            if (href){

            }
        },
        error: function (e) {
            console.log(e)
        }
    })
}
$("body").on("click","#paid" , function(){
    sendAdminPost('/paidorders',"","");
});

/**
 * Calls paid orders page for admin
 * */

$("body").on("click","#unpaid" , function(){
    sendAdminPost('/unpaidorders',"","");
});
/**
 * Calls delivered orders page for admin
 * */
$("body").on("click","#delivered" , function(){
    sendAdminPost('/deliveredorders',"","");
});

/**
 * Calls shipping page for admin
 * */
function sendAdminGet(url, href){
    $.ajax({
        url: $("#warPath").text() + url,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function (s) {
            $("#curModule").html(s);
            if (href){
                window.location.hash = href;
            }
        },
        error: function (e) {
            console.log(e)
        }
    })
}
$("body").on("click","#shippingAdmin" , function(e){
    e.preventDefault();
    sendAdminGet('/admin/shipping',"");
});

/**
 * Sends remove shipping type request to server
 * */
$("body").on("click",".removeShipping" , function(){
    var id = parseInt($($(this).closest("tr").find(".sid")[0]).text(),10);
    sendAdminGet("/admin/shipping/remove/"+id,"");
});


/**
 * Calls edit shipping form request
 * */
$("body").on("click",".editShipping" , function(){
    showInvalidInputs();
    if ($(".is-invalid").length) {
        return;
    }
    var id = parseInt($($(this).closest("tr").find(".sid")[0]).text(),10);
    sendAdminGet("/admin/shipping/edit/"+id,"#type");
});


/**
 * Sends add shipping request
 * */
$("body").on("click",".addShipping" , function(e){
    showInvalidInputs();
    if ($(".is-invalid").length) {
        return;
    }
    e.preventDefault();

    var id = parseInt($("#id").val(),10);
    var type = $("#type").val();

    var data = JSON.stringify({"id" : id, "type" : type});
    sendAdminPost("/admin/shipping/add/",data,"");
});

/**
 * Sends shipping update request
 * */
$("body").on("click",".updateShipping" , function(e){
    e.preventDefault();
    var id = parseInt($("#id").val(),10);
    var type = $("#type").val();

    var data = JSON.stringify({"id" : id, "type" : type});
    sendAdminPost("/admin/shipping/edit/",data,"");
});


/**
 * Categories block of code
 * */
$("body").on("click","#categoriesAdmin, #cancelEditCategory" , function(e){
    e.preventDefault();
    sendAdminGet('/admin/categories',"");
});

/**
 * removes category request
 * */
$("body").on("click",".removeCategory" , function(){
    var id = parseInt($($(this).closest("tr").find(".cid")[0]).text(),10);
    sendAdminGet("/admin/categories/remove/"+id,"");
});

/**
 * edit category get form request
 * */
$("body").on("click",".editCategory" , function(){
    var id = parseInt($($(this).closest("tr").find(".cid")[0]).text(),10);
    sendAdminGet("/admin/categories/edit/"+id,"");
});

/**
 * add category request
 * */
$("body").on("click",".addCategory" , function(e){
    e.preventDefault();
    var id = parseInt($("#id").val(),10);
    var name = $("#name").val();
    var parentId = parseInt($("#parentId").find("option:selected").val(),10);

    var data = JSON.stringify({"id" : id, "name" : name, "parentId" : parentId});
    sendAdminPost("/admin/categories/add/",data,"");
});


/**
 * sends update category request
 * */
$("body").on("click",".updateCategory" , function(e){
    e.preventDefault();
    var id = parseInt($("#id").val(),10);
    var name = $("#name").val();
    var parentId = parseInt($("#parentId").find("option:selected").val(),10);

    var data = JSON.stringify({"id" : id, "name" : name, "parentId" : parentId});
    sendAdminPost("/admin/categories/edit/",data,"");
});


/**
 * Users panel in admin page
 * */
$("body").on("click","#users" , function(){
    sendAdminGet('/admin/users',"");
});


/**
 * Products panel in admin page
 * */
$("body").on("click","#productsAdminList" , function(){
    sendAdminGet('/admin/products',"");
});

/**
 * remove product request
* */
$("body").on("click",".removeProduct" , function(){
    var id = parseInt($($(this).closest("tr").find(".productid")[0]).text(),10);
    sendAdminGet("/admin/products/remove/"+id,"");
});


/** products page*/
$("body").on("click",".listProduct",function(){
    $(".listProduct").addClass("active");
    $(".newProduct").removeClass("active");

    $("#productNewDiv").hide();
    $("#productDiv").show();
});

$("body").on("click",".newProduct",function(){
    $(".newProduct").addClass("active");
    $(".listProduct").removeClass("active");

    $("#productDiv").hide();
    $("#productNewDiv").show();
});


/**
 * edit product request
 * */
$("body").on("click",".editProduct" , function(e){
    e.preventDefault();
    var id = parseInt($($(this).closest("tr").find(".productid")[0]).text(),10);
    sendAdminGet("/admin/products/edit/"+id,"#name");
});


/**
 * get statistics page
 * */
$("body").on("click","#statistics" , function(){
    sendAdminGet("/admin/statistics","");
});


/**
 * User admin status
 * */

$("body").on("change", ".isAdmin", function () {
        swal({
            title: "Do you want to change admin status for user?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((toUpdate) => {
                if (toUpdate) {
                    var data =  JSON.stringify({
                        "admin": $(this)[0].checked,
                        "id": parseInt($($(this).closest("tr").find(".userid")[0]).text(),10)
                    });
                    sendAdminPost("/admin/users/makeUserAdmin",data,"");
                }else {
                    $(this).prop('checked', !$(this).prop('checked'));
                }
        });
});


/**
 * sends add product request
 * */
$("body").on("keyup mouseup input", "input, textarea", function(e){
    if(this.validity.valid)
    {
        $(this).removeClass("is-invalid");
    }else {
        $(this).addClass("is-invalid");
    }
});
function showInvalidInputs(){
    $('input:visible[required="required"],textarea[required="required"]').each(function()
    {
        if(!this.validity.valid)
        {
            $(this).addClass("is-invalid");
        }
    });
    try {
        var image = $("#file")[0];
        if (image && image.files.length) {
            if (((image.files[0].size) / 1024) > 200) {
                $(".notification").append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                    'File size should be < 200 Kb' +
                    '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
                    '<span aria-hidden="true">&times;</span>' +
                    '</button></div>');
                $("#file").addClass("is-invalid");
            } else {
                $("#file").removeClass("is-invalid");
            }
        }
    }catch (e) {
        console.log(e);
    }
}

function sendAdminMultitypePost(url,formid){
    var formData = new FormData();

    formData.append("file", $("#file")[0].files[0]);
    formData.append('product', new Blob([JSON.stringify({
        "id": $("#id").val(),
        "name": $("#name").val(),
        "description": $("#description").val(),
        "rule":   $("#rules").val(),
        "price":  $("#price").val(),
        "weight": $("#weight").val(),
        "volume": $("#volume").val(),
        "amount": $("#amount").val(),
        "minPlayerAmount": $("#minPlayerAmount").val(),
        "maxPlayerAmount": $("#maxPlayerAmount").val(),
        "imageSource": $("#imageSource").val(),
        "category": {
            "id": $("#category").find("option:selected").val(),
            "name": $("#category").find("option:selected").text()
        }
    })], {
        type: "application/json"
    }));

    $.ajax({
        url: $("#warPath").text() +url,
        enctype: 'multipart/form-data',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        cache: false,
        success: function (response) {
            $(formid).html(response);
            if ($(".alert-success").length){
                document.location.reload(true);
            }
        },
        error: function (e) {
            console.log(e)
        }
    })
}

$("body").on("click", "#addProduct",function (e) {
    e.preventDefault();
    showInvalidInputs();
    if ($(".is-invalid").length) {
        return;
    }
     sendAdminMultitypePost('/admin/products/addProduct',"#productNewDiv");
});

/**
 * sends edit product request
 * */

$("body").on("click", "#cancelProductEdit", function (e) {
    e.preventDefault();
    document.location.reload(true);
});

$("body").on("click", "#applyProductEdi", function (e) {
    e.preventDefault();
    showInvalidInputs();
    if ($(".is-invalid").length) {
        return;
    }
    sendAdminMultitypePost('/admin/products/editWithImage',"#ef");
});
$("body").on("click","#applySpecStat",function() {
    $.ajax({
        url: $("#warPath").text() +'/admin/statistics/specificStats',
        type: 'POST',
        data: JSON.stringify({"to": $("#toDate").val(), "from": $("#fromDate").val(), "limit": 10}),
        accept: "text/html",
        contentType: "application/json; charset=utf-8",
        success: function (s) {
            $("#statsJSP").html(s);
        },
        error: function (e) {
            console.log(e)
        }
    })
});
/** products page*/
$("body").on("click",".anotherStatNav",function(){
    $("#periodForm").show();
    $(".nav-link").removeClass("active");
    $(this).addClass("active");
    $(".curStatNav").removeClass("active");
    $("#statsJSP").show();
    $("#pdfForm").hide();
});

/** products page*/
$("body").on("click",".reportNav",function(){
    $("#periodForm").hide();
    $("#pdfForm").show();
    $(".nav-link").removeClass("active");
    $(this).addClass("active");
    $("#statsJSP").hide();

});

$("body").on("click",".curStatNav",function(){
    $.ajax({
        url: $("#warPath").text() +'/admin/statistics',
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function (s) {
            $("#curModule").html(s);
        },
        error: function (e) {
            console.log(e)
        }
    })
});
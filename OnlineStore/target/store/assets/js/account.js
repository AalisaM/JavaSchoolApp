/**
 * adds address adding form on page
 * */
$("body").on("click","#add", function () {
    $("#newAddr").attr("hidden", false);

  //  $("input").trigger("focus");
});
$("body").on("click",".userinfoNav", function () {
    $("#userAccountAddressList").hide();
    $("#newAddr").hide();
    $("#userInfo").show();
    $(".userinfoNav").addClass("active");
    $(".addressnewNav").removeClass("active");
    $(".addresslistNav").removeClass("active");

});
$("body").on("click",".addresslistNav", function () {
    $("#userInfo").hide();
    $("#newAddr").hide();
    $("#userAccountAddressList").show();
    $(".addresslistNav").addClass("active");
    $(".userinfoNav").removeClass("active");
    $(".addressnewNav").removeClass("active");
});
$("body").on("click",".addressnewNav", function () {
    $("#userInfo").hide();
    $("#userAccountAddressList").hide();
    $("#newAddr").show();
    $(".addressnewNav").addClass("active");
    $(".userinfoNav").removeClass("active");
    $(".addresslistNav").removeClass("active");

});

/**
 * hides address form
 * **/



$("body").on("click","#cancelAddAddress", function(){

    $("#country").val();
    $("#index").val();
    $("#city").val();
    $("#house").val();
    $("#flat").val();
});

/**
 * Get data from form and send it to controller
 * */

function checkInputs(){
    return ($("#city").val().length &&
    $("#index").val().length  &&
    $("#street").val().length  &&
    $("#house").val().length)
}
$("body").on("click","#addAddress", function (e) {
    e.preventDefault();
    if (!checkInputs()){
        $(".notification").append('<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
            'Fill all fields please'+
            '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span>' +
            '</button></div>');
        return;
    }
    var fullAddr = $("#country").val() + ", " +
        $("#city").val() + ", " +
        $("#index").val() + ", " +
        $("#street").val() + ", " +
        $("#house").val() + ", " +
        $("#flat").val();
    var userId = $($("form").find("#id")[0]).val();
    $.ajax({
        url: $("#warPath").text() +'/account/addAddress',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"user_id": userId, "address_id": 0, "address_str": fullAddr}),
        success: function (s) {
            $("#curModule").html(s);
        }
    })
});

/**
 * Removes address for user.
 * */
$("body").on("click", ".removeAddress", function () {
    var addressId = $($($(this).closest("tr")).find(".idAddr")[0]).text();
    var userId = $($("form").find("#id")[0]).val();
    $.ajax({
        url: $("#warPath").text() +'/account/removeAddress',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"user_id": userId, "address_id": addressId}),
        success: function (s) {
            $("#curModule").html(s);
        }
    })
});

/**
 * Calls editing form for address
 * */
$("body").on("click",".editAddress", function () {
    $($($(this).closest("tr")).find(".addrNameValue")[0]).attr("hidden", true)
    $($($(this).closest("tr")).find(".addrNameEdit")[0]).attr("hidden", false)
    $($($(this).closest("tr")).find(".editAddress")[0]).attr("hidden", true)
    $($($(this).closest("tr")).find(".applyEditAddress")[0]).attr("hidden", false)
    $($($(this).closest("tr")).find(".removeAddress")[0]).attr("hidden", true)
    $($($(this).closest("tr")).find(".cancelEditAddress")[0]).attr("hidden", false)

    var fullAddr = $($($(this).closest("tr")).find(".addrNameValue")[0]).text().split(",");
    console.log(fullAddr);
    $($($(this).closest("tr")).find(".countryEd")[0]).val(fullAddr[0]);
    $($($(this).closest("tr")).find(".cityEd")[0]).val(fullAddr[1]);
    $($($(this).closest("tr")).find(".indexEd")[0]).val(fullAddr[2]);
    $($($(this).closest("tr")).find(".streetEd")[0]).val(fullAddr[3]);
    $($($(this).closest("tr")).find(".houseEd")[0]).val(fullAddr[4]);
    $($($(this).closest("tr")).find(".flatEd")[0]).val(fullAddr[5]);

});

/***
 * Sends update address request
 * */
$("body").on("click",".applyEditAddress", function () {
    var addressId = $($($(this).closest("tr")).find(".idAddr")[0]).text();
    var userId = $($("form").find("#id")[0]).val();
    var fullAddr =  $($($(this).closest("tr")).find(".countryEd")[0]).val() + ", " +
        $($($(this).closest("tr")).find(".cityEd")[0]).val() + ", " +
        $($($(this).closest("tr")).find(".indexEd")[0]).val() + ", " +
        $($($(this).closest("tr")).find(".streetEd")[0]).val() + ", " +
        $($($(this).closest("tr")).find(".houseEd")[0]).val() + ", " +
        $($($(this).closest("tr")).find(".flatEd")[0]).val();
    console.log("FA-----");

    console.log("FA:" + fullAddr);
    $.ajax({
        url: $("#warPath").text() + '/account/editAddress',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"user_id": userId, "address_id": addressId, "address_str": fullAddr}),
        success: function (s) {
            $("#curModule").html(s);
        },error : function (e) {
            console.log(e)
        }
    });	//check if active set active
    $($($(this).closest("tr")).find(".addrNameValue")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".addrNameEdit")[0]).attr("hidden", true);

    $($($(this).closest("tr")).find(".cityEd")[0]).val("");
    $($($(this).closest("tr")).find(".indexEd")[0]).val("");
    $($($(this).closest("tr")).find(".streetEd")[0]).val("");
    $($($(this).closest("tr")).find(".houseEd")[0]).val("");
    $($($(this).closest("tr")).find(".flatEd")[0]).val("");

    $($($(this).closest("tr")).find(".addrNameValue")[0]).text(fullAddr);
    $($($(this).closest("tr")).find(".editAddress")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".applyEditAddress")[0]).attr("hidden", true);
    $($($(this).closest("tr")).find(".removeAddress")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".cancelEditAddress")[0]).attr("hidden", true);

});

/**allows only one checkbox per page in case it not drugs*/
$("body").on("change", ".activeAddr", function () {
    if ((this.checked)) {
        $(".activeAddr").not(this).prop("checked", false);
        $.ajax({
            url: $("#warPath").text() + '/account/setActiveAddress',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            success: function (s) {
                $("#curModule").html(s);
            },
            data: JSON.stringify({
                "user_id": $($("form").find("#id")[0]).val(),
                "address_id": $($($(this).closest("tr")).find(".idAddr")[0]).text()
            })
        });
    }
});

/**
 * hides address editing form
 * **/
$("body").on("click", ".cancelEditAddress", function () {

    $($($(this).closest("tr")).find(".addrNameValue")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".addrNameEdit")[0]).attr("hidden", true);
    $($($(this).closest("tr")).find(".editAddress")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".applyEditAddress")[0]).attr("hidden", true);
    $($($(this).closest("tr")).find(".removeAddress")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".cancelEditAddress")[0]).attr("hidden", true);
    $("#countryEd").val("");
    $("#cityEd").val("");
    $("#indexEd").val("");
    $("#streetEd").val("");
    $("#houseEd").val("");
    $("#flatEd").val("");
    $($($(this).closest("tr")).find(".addrNameValue")[0]).attr("hidden", false);
    $($($(this).closest("tr")).find(".addrNameEdit")[0]).attr("hidden", true);
    ;
});

/**
 * Calls change password page
 * */
$("body").on("click","#cpswd" , function(e){
    e.preventDefault();
    $.ajax({
        url: $("#warPath").text() +'/account/changePassword',
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function (s) {
            $("#curModule").html(s);
        },
        error: function (e) {
            console.log(data);
            console.log(e)
        }
    })
});

/**
 * Tries to update password data
 * **/
$("body").on("click","#updatePswd" , function(){
    var pass = $("#pass").val();
    var valid = (pass == $("#passConfirm").val());
    if (valid) {
        $.ajax({
            url: $("#warPath").text() +'/account/updatePassword',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"password": pass, "oldPassword": $("#oldpass").val()}),
            success: function (s) {
                $("body").html(s);
            },
            error: function (e) {
                console.log(e)
            }
        });
    }else {
        $(".notification").append('<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
            'Password mismatch'+
        '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span>' +
        '</button></div>');
    }
});


$("body").on("click","#signup" , function(e){
    var pass = $("#password").val();
    var valid = (pass == $("#passConfirm").val());
    if(valid) {
        $("#error").hide();
        return;
    } else {
        $(".notification").append('<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
            'Password mismatch'+
            '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span>' +
            '</button></div>');
        e.preventDefault();
    }
});

/**
 * Calls user order history for account
 * */
$("body").on("click","#localOH" , function(){
    $.ajax({
        url: $("#warPath").text() +'/userOrder',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(""),
        success: function (s) {
            $("#curModule").html(s);
        },
        error: function (e) {
            console.log(data);
            console.log(e)
        }
    })
});

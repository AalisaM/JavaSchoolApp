/**
 * search through catalogue
 * */
window.onload = function() {
    if (!$("#loggedUserName").length) {
        var jsonCart = getCartFromCookie();
        $("#lblCartCount").text((isNaN(jsonCart["totalAmount"]) ? 0 : jsonCart["totalAmount"]));
        $("#cartPrice").text(jsonCart["totalPrice"]);
    }
    $("body").on("keyup", "#example-search-input", function (e) {
        if (e.keyCode == 13){
            $("#searchProd").trigger("click");
        }
    });

    $("body").on("click", "#searchProd", function (e) {
        e.preventDefault();
        var data = JSON.stringify({
            "request": $("#example-search-input").val()
        });

        $.ajax({
            url: $("#warPath").text() +'/search',
            type: 'POST',
            data: data,
            accept: "text/html",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                $(".row.site-padding.catalogue").html(response);
            },
            error: function (e) {
                console.log(e)
            }
        })
    });

    $('#example-search-input').on('focus.autocomplete', function () {
        var res = {};
        $.ajax({
            url: $("#warPath").text() +"/auto",
            success: function (data) {
                var transformed = $.map(data, function (k, v) {
                    return {
                        id: v, label: k
                    };
                });
                res = transformed;
                console.log(res);
                $("#example-search-input").autocomplete({
                    selectFirst: true,
                    minLength: 3,
                    source: res,
                    select: function (event, ui) {
                        var id = ui.item.id;
                        // window.location.href = "http://localhost:8086"+$("#warPath").text()+"/product/" + id;
                        var ip = $("#ip").text().trim();
                        window.location.href = "http://" + ip + ":8086"+$("#warPath").text()+"/product/" + id;
                    }
                })
            },
            error: function () {
            }
        });

    });

    if ($("#myImg").length) {
        var modal = document.getElementById('myModal');

        // Get the image and insert it inside the modal - use its "alt" text as a caption
        var img = document.getElementById('myImg');
        var modalImg = document.getElementById("img01");
        var captionText = document.getElementById("caption");
        img.onclick = function () {
            modal.style.display = "block";
            modalImg.src = this.src;
            captionText.innerHTML = this.alt;
        }

        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];

        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
        }
    }
};

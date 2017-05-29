'use strict';

/**
 * Ugly java script code for simple tests of shareit's REST interface.
 *  @author Axel Böttcher <axel.boettcher@hm.edu>
 */

/**
 * This function is used for transfer of new book info.
 */
var login = function() {
	var json = JSON.stringify({
        username: $("input[name=username]").val(),
        password: $("input[name=password]").val(),
	});
    var token;
    var errorText = $("#errormessage");
    var successText = $("#token");
    $.ajax({
        url: '/shareit/users/login',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json,
        success:function(response_data_json) {
            token = response_data_json.token;
            console.log(token); //Shows the correct piece of information
            displayJSON(token); // Pass data to a function
        }
        })
        .done(() => {
            $("input[name=username]").val("");
            $("input[name=password]").val("");

            errorText.removeClass("visible");
            errorText.addClass("hidden");
        })
        .fail((error) => {
            successText.removeClass("visible");
            successText.addClass("hidden");
            errorText.removeClass("visible");
            errorText.text(error.responseJSON.message);
        	errorText.removeClass("hidden");
        });

    function displayJSON(json_data)
    {
        successText.addClass("visible");
        successText.text("Your Token: " + json_data);
        successText.removeClass("hidden");
    }
}
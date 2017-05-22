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
	var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/users/login',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
        })
        .done(() => {
			$("input[name=username]").val("");
			$("input[name=password]").val("");

        	errorText.removeClass("visible");
        	errorText.addClass("hidden");

            var template = "<p>login was successful</p>";
            Mustache.parse(template);
            var output = Mustache.render(template);
            $("#content").html(output);
        })
        .fail((error) => {
        	errorText.addClass("visible");
        	errorText.text(error.responseJSON.message);
        	errorText.removeClass("hidden");
        });
}
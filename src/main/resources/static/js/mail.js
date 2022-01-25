$(document).ready(function () {
    $("#convertForm").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
    $("#historyForm").submit(function (e) {
        e.preventDefault();
        fire_history_submit();
    });
});

function fire_history_submit() {
    var history = {};
    history["firstCurrency"] = $('#firstCurrency').val();
    history["secondCurrency"] = $('#secondCurrency').val();
    history["date"] = $('#historyDate').val();
    $('#btn-search').prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/history",
        data: JSON.stringify(history),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
            $("#historyTable tbody tr").remove();
            //var historydata=$.parseJSON(data);
            //var json = JSON.stringify(data["conversions"], null, 4);
            var tr;
            for (var i = 0; i < data["conversions"].length; i++) {
                tr = $('<tr/>');
                tr.append("<td>" + data['conversions'][i]['firstCurrency'] + "</td>");
                tr.append("<td>" + data['conversions'][i]['secondCurrency'] + "</td>");
                tr.append("<td>" + data['conversions'][i]['firstValue'] + "</td>");
                tr.append("<td>" + data['conversions'][i]['secondValue'] + "</td>");
                tr.append("<td>" + data['conversions'][i]['date'] + "</td>");
                $('#tbody').append(tr);
            }
            //$('#history').html(json);
            $('#btn-search').prop("disabled", false);
        },
        error: function (e) {
            var json = "<h4>History Response</h4>"
                + e.responseText;
            $('#history').html(json);
            $('#btn-search').prop("disabled", false);

        }
    })
}

function fire_ajax_submit() {
    var data = {};
    data["firstCurrency"] = $('#currency1').val();
    data["secondCurrency"] = $('#currency2').val();
    data["amount"] = $("#amount").val();
    $("#btn-convert").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/convert",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
            var json = JSON.stringify(data['convertResult'], null, 4);
            $('#result').val(data['convertResult']);
            console.log("SUCCESS : ", data);
            $("#btn-convert").prop("disabled", false);
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                + e.responseText + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("ERROR: ", e);
            $("#btn-convert").prop("disabled", false);
        }
    });
}
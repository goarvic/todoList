/**
 * Created by vickop on 9/11/14.
 */

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************
function changeState(itemId, buttonId, url)
{

    var requestObj = {
        id: parseInt(itemId),
        description: $('#itemId' + itemId).text,
        isDone : $('#itemId' + itemId).attr('isDone')
    };

    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(requestObj),
        success:
            function(response)
            {
                if (response.statusCode == 0)
                {
                    if (response.toDoItem.isDone == true)
                    {
                        $('#itemId' + itemId).css("text-decoration", "line-through");
                        $('#'+buttonId).children("span").css("color", "red")
                        $('#'+buttonId).children("span").removeClass("glyphicon-ok")
                        $('#'+buttonId).children("span").addClass("glyphicon-remove")
                    }
                    else
                    {
                        $('#itemId' + itemId).css("text-decoration", "");
                        $('#'+buttonId).children("span").css("color", "green")
                        $('#'+buttonId).children("span").removeClass("glyphicon-remove")
                        $('#'+buttonId).children("span").addClass("glyphicon-ok")
                    }

                }
            }
    })
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function addItem(url)
{   var description = $('#inputText').val()
    var requestObj = {
        description: description
    };

    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(requestObj),
        success:
            function(response)
            {
                if (response.statusCode == 0)
                {
                    $('#listItems').append(

                            '<div class="row" style="margin-bottom:8px;">' +
                            '<div  class="col-sm-10">'+
                            '<li isDone="false" id="itemId' + response.toDoItem.id +'" class="list-group-item">'+
                            response.toDoItem.description +
                            '</li>'+
                            '</div>'+
                            '<div class="col-sm-2">' +
                            '<button type="button" id="buttonItemId'+ response.toDoItem.id +'" class="changeStateButton btn btn-default btn-lg">'+
                            '<span href="#" class="glyphicon glyphicon-ok" style="float:right;color:green"></span>'+
                            '</button>'+
                            '</div>'+
                            '</div>'
                    )
                    $('#inputText').val("")
                }
            }
    })
}
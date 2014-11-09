package com.todolist

import com.todolist.model.ResponseObject
import com.todolist.model.ToDoItemModel
import grails.converters.JSON

class HomeController {

    static allowedMethods = [index: 'GET',
                             addItem:'POST',
                             changeDoneState:'POST']

    ToDoItemService toDoItemService

    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************


    def index()
    {
        List<ToDoItemModel> todoItems = toDoItemService.getLastItems()
        render (view: "index", model:[todoItems : todoItems])
    }

    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************

    def addItem()
    {
        String description = request.JSON.description

        ResponseObject response

        if (description != null)
        {
            response = toDoItemService.addNewItem(description)
            render response as JSON
            return
        }

        response = new ResponseObject()
        response.statusCode = -1
        response.statusDescription = "Error parsing request object"
        response.toDoItem = null
        render response
    }

    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************

    def changeDoneState()
    {   ToDoItemModel toDoItem
        ResponseObject ajaxResponse

        try
        {
            toDoItem = new ToDoItemModel(request.JSON)
        }
        catch(Exception e)
        {
            ajaxResponse = new ResponseObject()
            log.warn "Error binding param object from AJAX request changeDoneState"
            ajaxResponse.statusCode = -1
            ajaxResponse.statusDescription = "Error parsing request object"
            ajaxResponse.toDoItem = toDoItem
            render ajaxResponse
            return
        }

        ajaxResponse = toDoItemService.changeDoneState(toDoItem)
        render ajaxResponse as JSON
    }

}

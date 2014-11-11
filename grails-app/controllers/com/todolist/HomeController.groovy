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
        String description
        ResponseObject response

        description = request.JSON.description

        if ((description != null) && (!description.equals("")))
        {
            response = toDoItemService.addNewItem(description)
            render response as JSON
            return
        }

        log.warn "Error binding param object from AJAX request addItem"
        response = new ResponseObject(statusCode: -1, statusDescription: "Error parsing request object", toDoItem: null)
        render response as JSON
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
            ajaxResponse = new ResponseObject(statusCode: -1, statusDescription: "Error parsing request object", toDoItem : toDoItem)
            log.warn "Error binding param object from AJAX request changeDoneState"
            render ajaxResponse as JSON
            return
        }

        ajaxResponse = toDoItemService.changeDoneState(toDoItem)
        render ajaxResponse as JSON
    }

}

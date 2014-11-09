package com.todolist

import com.todolist.domain.ToDoItem
import com.todolist.model.ResponseObject
import com.todolist.model.ToDoItemModel
import grails.transaction.Transactional

@Transactional
class ToDoItemService {

    List<ToDoItemModel> getLastItems() {
        List<ToDoItem> itemsList = ToDoItem.list(sort:"dateCreated", order:"desc", max:20)

        List<ToDoItemModel> itemsListModel = new ArrayList<ToDoItemModel>()
        for (int i=itemsList.size()-1; i>=0; i--)
        {
            ToDoItemModel toDoItemModel = new ToDoItemModel()
            toDoItemModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    toDoItemModel.setProperty(propertyName, itemsList.get(i).getProperty(propertyName))
            }
            itemsListModel.add(toDoItemModel)
        }

        return itemsListModel
    }

    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************

    ResponseObject addNewItem (String description)
    {
        ToDoItem toDoItem = new ToDoItem(description: description, isDone: false)
        ResponseObject response

        if (toDoItem.save(flush:true) == null)
        {
            log.error "Error saving ToDoItem: " + toDoItem.errors
            response = new ResponseObject(statusCode: -3, statusDescription: "Error saving ToDoItem: " + toDoItem.errors, toDoItem: toDoItem)
            return response
        }

        ToDoItemModel toDoItemModel = new ToDoItemModel()
        toDoItemModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class"))
                toDoItemModel.setProperty(propertyName, toDoItem.getProperty(propertyName))
        }
        response = new ResponseObject(statusCode: 0, statusDescription: "Success", toDoItem: toDoItemModel)
        return response
    }

    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************

    ResponseObject changeDoneState (ToDoItemModel itemModel)
    {
        ToDoItem toDoItem = ToDoItem.findById(itemModel.getId())
        ResponseObject response

        if (toDoItem == null)
        {
            log.warn "Item not found by id: " +itemModel.getId()
            response = new ResponseObject(statusCode: -2, statusDescription: "Item not found by id: " +itemModel.getId(), toDoItem: toDoItem)
            return response
        }
        toDoItem.setIsDone(!toDoItem.getIsDone())
        if (toDoItem.save(flush:true) == null)
        {
            log.error "Error saving ToDoItem: " + toDoItem.errors
            response = new ResponseObject(statusCode: -3, statusDescription: "Error saving ToDoItem: " + toDoItem.errors, toDoItem: toDoItem)
            return response
        }

        ToDoItemModel responseToDoItemModel = new ToDoItemModel()
        responseToDoItemModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class"))
                responseToDoItemModel.setProperty(propertyName, toDoItem.getProperty(propertyName))
        }
        response = new ResponseObject(statusCode: 0, statusDescription: "Success", toDoItem: responseToDoItemModel)
        return response
    }


}

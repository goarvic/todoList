package com.todolist.domain

class ToDoItem {

    Date dateCreated
    String description
    boolean isDone

    def beforeInsert()
    {
        dateCreated = new Date()
    }

    static constraints = {
        description nullable : false
        isDone nullable : false
    }
}

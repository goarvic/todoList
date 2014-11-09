<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ToDo List For Unience</title>

    <script type="text/javascript">
        $(document).on( 'click', '.changeStateButton',
                function(){
                    var itemId = $(this).attr('id').substr(12);
                    var url = '<g:createLink controller="home" action="changeDoneState"/>'
                    var buttonId = $(this).attr('id')
                    changeState(itemId, buttonId, url)
                }
        );

        $(document).on( 'click', '#addbtn',
                function(){
                    var url = '<g:createLink controller="home" action="addItem"/>'
                    addItem(url)
                }
        );
    </script>
</head>
<body>
    <div id="page-body" role="main" class="container">
        <div class="col-sm-11">
            <div class="panel panel-default">

                <div class="panel-heading">
                    <h3 class="panel-title">Items</h3>
                </div>
                <div class="panel-body">

                    <div class="col-sm-12">
                        <ul class="list-group" id="listItems">
                            <g:each in="${todoItems}" var="toDoItem">
                                <div class="row" style="margin-bottom:8px;">
                                    <div  class="col-sm-10">
                                        <g:if test="${toDoItem.getIsDone()}">
                                            <li isDone="${toDoItem.getIsDone()}" style="text-decoration: line-through;" id="itemId${toDoItem.getId()}"  class="list-group-item">
                                        </g:if>
                                        <g:else>
                                            <li isDone="${toDoItem.getIsDone()}" id="itemId${toDoItem.getId()}"  class="list-group-item">
                                        </g:else>
                                                ${toDoItem.getDescription()}
                                            </li>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" id="buttonItemId${toDoItem.getId()}" class="changeStateButton btn btn-default btn-lg">
                                            <g:if test="${toDoItem.getIsDone()}">
                                                    <span href="#" class=" glyphicon glyphicon-remove" style="float:right;color:red"></span>
                                            </g:if>
                                            <g:else>
                                                    <span href="#" class="glyphicon glyphicon-ok" style="float:right;color:green"></span>
                                            </g:else>
                                        </button>
                                    </div>
                                </div>
                            </g:each>
                        </ul>
                    </div>

                    <div class="row">
                        <div class="col-sm-12">
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon">-></span>
                                    <input id="inputText" type="text" class="form-control" placeholder="Add new item in list">
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <button type="button" id="addbtn" class="btn btn-default">
                                    <span class="glyphicon glyphicon-plus"></span> Add
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

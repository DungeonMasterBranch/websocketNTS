window.onload = init;
var socket = new WebSocket("ws://localhost:8080/demo_war/logger");

socket.onmessage = function(event) {
    //console.log(event.data)
    var json = JSON.parse(event.data)
    var command = json.command;
    var content = json.content;
    var logDescription = document.getElementById("logList");

    if (command === "logs"){
        logDescription.innerHTML="";
        logDescription.innerHTML = content;
        console.log("Listing all logs of this session: {"+content+"}");
    } else if (command === "addLog"){
        console.log("Log "+json.content+" added;");
    } else if (command === "comment"){
        console.log(json.content);
    }   else {
        console.log("Command not found");
    }

};

function addLog(contentMsg) {
    var Log = {
        command: "addLog",
        content: contentMsg
    };
    socket.send(JSON.stringify(Log));
}

function showLogList(message) {
    document.getElementById("logList").remove();
    element.parentNode.removeChild(element);
    var content = document.getElementById("logList");
    var allLogs = document.createElement("span");
    allLogs.innerHTML = message;
    content.appendChild(allLogs);
}

function onLogMessage(){
    var Log = {
        command: "logs"
    };
    socket.send(JSON.stringify(Log));
}


function showForm() {
    document.getElementById("addLogForm").style.display = '';
}

function hideForm() {
    document.getElementById("addLogForm").style.display = "none";
}

function hideLogList() {
    document.getElementById("logList").removeChild();
}


function formSubmit() {
    var form = document.getElementById("addLogForm");
    var description = form.elements["logContent"].value;
    hideForm();
    document.getElementById("addLogForm").reset();
    addLog(description);
}

function init() {
    hideForm();
}
# websocketNTS
- This project do not have .jsp or html files and do not connect from frontend using javascript
- Consequently you need make connection using postman or postman like soft on address "ws://localhost:8080/demo_war/logger" after deploying this project

## Syntax

- Message must have open and closed brackets "{ }"
- Brackets must contain "command", "content" types of data
- If in structure will be added another type of data, it will be ignored
- There only two commands: "addLog", "logs". If user will input another command name, then it will respond about not right command
- Content saved in ArrayList which save the history of logs on time of deploy and no more
- At the end of the message in bracket, no ',' symbol

## Recommendation
I would recommend you to use JetBrains IDE for java, since I've used this IDE at developing, exactly you can use any other IDE for java, but there can be some problems at deploying.

## Notice
I've used java Amazon correto SDK version 11.0.16 


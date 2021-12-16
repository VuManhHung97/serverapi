// Define dependences
const express = require('express');
const bodyparser = require('body-parser');
const logger = require('morgan');
const MySQLEvents = require('@rodrigogs/mysql-events');
const mysql = require('mysql');
var multer = require('multer');
var upload = multer();

const path = require('path');

// Content
const app = express();

//import socketio
const http = require('http');
const server = http.createServer(app);
const options = { cors: {
    origin: "http://103.166.183.129",
    credentials: true
} };
const io = require('socket.io')(server, options)

//socket io
io.on('connection', (socket) => {
   
    socket.on('send-notification', (notificationTitle,callback) => {
        console.log(notificationTitle)
          // notificationTitle: nội dung ví dụ :"Thông báo chuẩn bị"
        io.sockets.emit('send-notification-app', notificationTitle)
        callback(
             "ok"
          );
    });

    socket.on('send-number-table', (numberTable,callback) => {
        // numberTable: nội dung ví dụ :" Số bàn 10"
        io.sockets.emit('send-number-table-app', numberTable)

        callback(
            "ok"
         );
    });

    socket.on('send-command', (command) => {
        // io.sockets.emit('send-command-app', {title: "Đang phân tích ...", command: "Phân Tích"})
        // io.sockets.emit('send-command-app', {title: "Chẵn", command: "Chẵn"})
        // io.sockets.emit('send-command-app', {title: "Con", command: "Con"})
        // io.sockets.emit('send-command-app', {title: "Tài", command: "Tài"})
        // io.sockets.emit('send-command-app', {title: "Lẻ", command: "Lẻ"})
        // io.sockets.emit('send-command-app', {title: "Cái", command: "Cái"})
        // io.sockets.emit('send-command-app', {title: "Rồng", command: "Rồng"})
        // io.sockets.emit('send-command-app', {title: "Xỉu", command: "Xỉu"})
        // io.sockets.emit('send-command-app', {title: "Hổ", command: "Hổ"})
        io.sockets.emit('send-command-app', command)

    });


    socket.on('send-status-tool', (statusTool) => {
        // statusTool: On or Off
        io.sockets.emit('send-status-tool-app', statusTool)      
    })
});

// app.get('/login', (req, res) => {
//     res.sendFile(__dirname + '/index.html');
// });

// app.get("*", (req, res) => {
//   res.sendFile(
//     path.join(__dirname, "./client/build/index.html")
//   );
//   })

const program = async () => {
    const connection = mysql.createConnection({
      host: 'localhost',
      user: 'root',
      password: 'admin@12345',
    });
  
    const instance = new MySQLEvents(connection, {
      startAtEnd: true,
      excludedSchemas: {
        mysql: true,
      },
    });
  
    await instance.start();
  
    instance.addTrigger({
      name: 'kube8989',
      expression: 'kube8989.*',
      statement: MySQLEvents.STATEMENTS.ALL,
      onEvent: (event) => {
        io.sockets.emit("event-users", "Events")  
      },
    });
    
    instance.on(MySQLEvents.EVENTS.CONNECTION_ERROR, console.error);
    instance.on(MySQLEvents.EVENTS.ZONGJI_ERROR, console.error);

};  
program()
  .then(() => console.log('Waiting for database events...'))
  .catch(console.error)

// Import file routes config ./app/routes/*
const productProject = require('./api/routes/route.status');
const userProject = require('./api/routes/route.users');
const orderProject = require('./api/routes/route.link');

// Middlerwares
/*** set up mogan */
app.use(logger('dev'));
/*** set up bodyparser */
app.use(express.json())
app.use(upload.array());
app.use(bodyparser.urlencoded({ extended: true })); 

//** set up url for image localhost:3000/uploads/filename*/


// Routers
app.use('/v1/api', productProject);
app.use('/v1/api', userProject);
app.use('/v1/api', orderProject);


// Module exports
module.exports = server;
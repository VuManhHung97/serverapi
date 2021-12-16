// Define Dependences
const http = require('http');
const server = require('./app');

// Define PORT
const port = process.env.PORT || 5656;

// Listen a port
server.listen(port, console.log(`Server started on port ${port}`));

/**
 * Note: This app want run command: npm start
 */
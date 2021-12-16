const mysql = require('mysql');

const pool = mysql.createPool({
    connectionLimit: 10,
    password: 'admin@12345',
    user: 'root',
    database: 'kube8989',
    host: 'localhost',
    port: '3306',
});

module.exports = pool;
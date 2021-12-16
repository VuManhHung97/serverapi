const Sequelize = require('sequelize');
const UserModel = require('../models/models.users');
const UserWebModel = require('../models/models.usersweb');
const LinkUrlsModel = require('../models/models.linkurl');
const StatusModel = require('../models/models.status');

const sequelize = new Sequelize('KubeTool', 'sa', 'kube8989', {
    host: 'localhost',
    dialect: 'mysql',
    pool: {
        max: 5,
        min: 0,
        acquire: 30000,
        idle: 10000
    },
});

const Users = UserModel(sequelize, Sequelize);
const UsersWeb = UserWebModel(sequelize, Sequelize);
const LinkUrl = LinkUrlsModel(sequelize, Sequelize);
const Status = StatusModel(sequelize, Sequelize);

sequelize.sync({ force: false })
    .then(() => {
        console.log(`Database & tables created!`)
    })
    .catch((err) => {
        console.log(err);
        
    });

module.exports = {
    Users,
    UsersWeb,
    LinkUrl,
    Status
}
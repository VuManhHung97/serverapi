module.exports = (sequelize, type) => {
    return  sequelize.define('user_web', {
        id: {
            primaryKey: true,
            type: type.INTEGER,
            autoIncrement: true,
            required: true
        },
        username: {
            type: type.STRING(100),
            required: true
        },
        password: {
            type: type.STRING(100),
            required: true
        },
        fullname: {
            type: type.STRING(100),
            isEmail: true,
            required: true
        },
        create_date: {
            type: type.STRING(100),
            required: true
        }
    }, { timestamps: false })
}

module.exports = (sequelize, type) => {
    return  sequelize.define('users', {
        id: {
            primaryKey: true,
            type: type.INTEGER,
            autoIncrement: true,
            required: true
        },
        fullname: {
            type: type.STRING(100),
            required: true
        },
        phone_number: {
            type: type.STRING(100),
            required: true
        },
        password: {
            type: type.STRING(100),
            isEmail: true,
            required: true
        },
        username: {
            type: type.STRING(100),
            required: true
        },
        email: {
            type: type.STRING(100),
            required: true
        },
        code: {
            type: type.STRING(100),
            required: true
        },
        status: {
            type: type.STRING(100),
            required: true
        },
        lock_user: {
            type: type.STRING(100),
            required: true
        },
        create_date: {
            type: type.STRING(100),
            required: true
        }      
    }, { timestamps: false })
}
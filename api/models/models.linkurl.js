module.exports = (sequelize, type) => {
    return  sequelize.define('link_url', {
        id: {
            primaryKey: true,
            type: type.INTEGER,
            autoIncrement: true,
            required: true
        },
        name: {
            type: type.STRING(100),
            required: true
        },
        link_name: {
            type: type.STRING(200),
            required: true
        },
        role: {
            type: type.STRING(100),
            required: true
        },
        date_create: {
            type: type.STRING(100),
            required: true
        }
    }, { timestamps: false })
}

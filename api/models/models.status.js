module.exports = (sequelize, type) => {
    return  sequelize.define('status_tool', {
        id: {
            primaryKey: true,
            type: type.INTEGER,
            autoIncrement: true,
            required: true
        },
        status: {
            type: type.STRING(45),
            required: true
        }
    }, { timestamps: false })
}

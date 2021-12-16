const db = require('../config/database');

exports.status_tool = (req, res, next) => { 
    db.query(`SELECT * FROM status_tool`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        if (results.length > 0) {
            return res.status(200).json({
                message: 'Successfuly',
                status: results[0].status
            });
        }
    })
}

exports.change_status = (req, res, next) => { 
    console.log(req.body.status);
    db.query(`UPDATE status_tool SET status = '${req.body.status}'  WHERE (id = 1)`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly'
        });
    })
}

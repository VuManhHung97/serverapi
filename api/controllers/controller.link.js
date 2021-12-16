const db = require('../config/database');

exports.get_link = (req, res, next) => { 
    db.query(`SELECT * FROM link_url where role = '${req.body.name}'`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        if (results.length > 0) {
            return res.status(200).json({
                message: 'Successfuly',
                linkName: results[0].link_name
            });
        }
    })
}

exports.change_link = (req, res, next) => { 
    db.query(`UPDATE link_url SET link_name = '${req.body.link_name}', name = '${req.body.name}' WHERE (id = ${req.body.id});`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly'
        });
    })
}

exports.get_all_link = (req, res, next) => { 
    db.query(`SELECT * FROM link_url`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly',
            links: results
        });
    })
}

exports.insert_link = (req, res, next) => { 
    db.query(`INSERT INTO link_url (name, link_name, role, date_create) VALUES ('${req.body.name}', '${req.body.link_name}', '${req.body.role}', CURDATE())`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Link created'
        });
    })
}

exports.delete_link = (req, res, next) => {
    console.log(req.body);
    if (req.body.role == "Admin" || req.body.role == "Game" || req.body.role =="AdminGmail") {
        db.query(`select count(id) from link_url where role = '${req.body.role}'`, (err, results) => {
            if (err) {
                return res.status(500).json({ err: err });
            }
            console.log(results)
            if (results[0] >= 2){
                db.query(`DELETE FROM link_url WHERE (id = ${req.body.id})`, (err, results) => {
                    if (err) {
                        return res.status(500).json({ err: err });
                    }
                    return res.status(200).json({
                        message: 'Successfuly'
                    });
                })
            }
            return res.status(400).json({
                message: 'Cần ít nhất một Link quyền này'
            });
        })
    }else {
        db.query(`DELETE FROM link_url WHERE (id = ${req.body.id})`, (err, results) => {
            if (err) {
                return res.status(500).json({ err: err });
            }
            return res.status(200).json({
                message: 'Successfuly'
            });
        })
    }
}

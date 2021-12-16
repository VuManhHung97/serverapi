const db = require('../config/database');
// const { Users, UsersWeb, LinkUrl,  Status } = require('../config/databasetest');

// exports.user_login_app = async(req, res, next) => {
//     try {
//         const user = await Users.findAll({ phone_number: req.body.phone_number });
//         if (user < 1) {
//             return res.status(200).json({
//                 message: 'Auth failed'
//             });
//         }
  
//         if (req.body.password == user[0].password) {
//             if (user[0].status == "Chưa cấp quyền") {
//                 return  res.status(200).json({
//                     message: 'Not active'
//                 });
//             }else if (user[0].lock_user == "true") {
//                 return  res.status(200).json({
//                     message: 'User lock'
//                 });
//             }
//             delete user[0].password
//             return res.status(200).json({
//                 message: 'Auth successful',
//                 user: user
//             });
//         }
//         return res.status(200).json({
//             message: 'Auth failed'
//         });
    

//     } catch (error) {
//         console.log('Error: ', error);
//         res.status(500).json({ error: error });
//     }
// }

exports.user_signup = async(req, res, next) => {
    try {
        db.query(`SELECT * FROM users where phone_number = '${req.body.phone_number}'`, (err, results) => {
            if (err) {
                console.log('Error: ', err);
                return res.status(500).json({ err: err });
            }
            if (results.length > 0) {
                return res.status(200).json({
                    message: 'Account exists'
                });
            }else {
                db.query(`INSERT INTO users (fullname, phone_number, password, username, email, code, status, lock_user, create_date)
                    VALUES ('${req.body.fullname}', '${req.body.phone_number}', '${req.body.password}', "", "", "", "Chưa cấp quyền", "false", CURDATE())`,(err, results) => {
                    if (err) {
                        return res.status(500).json({ err: err });
                    }
                    return res.status(200).json({
                        message: 'User created'
                    });
                })

            }
        });
    } catch (error) {
        return res.status(500).json({ error: error });
    }
}

exports.users_login = (req, res, next) => {
    db.query(`SELECT * FROM users where phone_number = '${req.body.phone_number}'`, (err, results) => {
        if (err) {
            console.log('Error: ', err);
            return res.status(500).json({ err: err });
        }
        if (results.length > 0) {
            if (req.body.password == results[0].password) {
                if (results[0].status == "Chưa cấp quyền") {
                    return  res.status(200).json({
                        message: 'Not active'
                    });
                }else if (results[0].lock_user == "true") {
                    return  res.status(200).json({
                        message: 'User lock'
                    });
                }
                delete results[0].password
                return res.status(200).json({
                    message: 'Auth Successfuly',
                    user: results[0]
                });
            }
        }
        return res.status(200).json({
            message: 'Auth failed'
        });
    });

}

exports.get_users = (req, res, next) => {
    try {
        db.query(`SELECT * FROM users`, (err, users) => {
            if (err) {
                res.status(500).json({ err: err });
            }
            return res.status(200).json({
                message: 'Successfuly',
                results: users
            });

        })

    }catch (error) {
        res.status(500).json({ error: error });
    }
}

exports.sign_in_web = (req, res, next) => {
    console.log(req.body.username);
    db.query(`SELECT * FROM users_web where username = '${req.body.username}'`, (err, results) => {
        if (err) {
            return res.status(500).json({ err: err });
        }
        if (results.length > 0) {
        
            if (req.body.password == results[0].password) {
                return res.status(200).json({
                    message: 'Auth Successfuly',
                    user: results[0]
                });
            }
        }
        return res.status(401).json({
            message: 'Auth failed'
        });
    });
}

exports.change_lock = (req, res, next) => {
    console.log(req.body.lock_user + "/" + req.body.id);
    db.query(` UPDATE users SET lock_user = '${req.body.lock_user}' WHERE (id = ${req.body.id})`, (err, results) => {
        if (err) {
            console.log('Error: ', err);
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly'
        });
    });
}

exports.change_status = (req, res, next) => {
    db.query(`UPDATE users SET status = '${req.body.status}' WHERE (id = '${req.body.id}')`, (err, results) => {
        if (err) {
            console.log('Error: ', err);
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly'
        });
    });
}

exports.delete_user = (req, res, next) => {
    db.query(`DELETE FROM users WHERE (id = '${req.body.id}')`, (err, results) => {
        if (err) {
            console.log('Error: ', err);
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly'
        });
    });
}

exports.add_code = (req, res, next) => {
    db.query(`UPDATE users SET code = '${req.body.content}' WHERE (id = '${req.body.id}')`, (err, results) => {
        if (err) {
            console.log('Error: ', err);
            return res.status(500).json({ err: err });
        }
        return res.status(200).json({
            message: 'Successfuly'
        });
    });
}


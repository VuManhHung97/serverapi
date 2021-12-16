const express = require('express');
const router = express.Router();
const userController = require('../controllers/controller.users');

router.route('/users/signup').post(userController.user_signup);
router.route('/users/login').post(userController.users_login);
router.route('/getusers').get(userController.get_users);
router.route('/users/signinweb').post(userController.sign_in_web);
router.route('/users/change_lock').post(userController.change_lock);
router.route('/users/change_status').post(userController.change_status);
router.route('/users/delete_user').post(userController.delete_user);
router.route('/users/add_code').post(userController.add_code);
// router.route('/users/login_app').post(userController.user_login_app);
// Export module
module.exports = router;

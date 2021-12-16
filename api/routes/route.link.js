const express = require('express');
const router = express.Router();
const linkController = require('../controllers/controller.link');
const checkAuth = require('../middleware/check_auth');

router.route('/get_link').post(linkController.get_link);
router.route('/change_link').post(linkController.change_link);
router.route('/get_all_link').get(linkController.get_all_link);
router.route('/insert_link').post(linkController.insert_link);
router.route('/delete_link').post(linkController.delete_link);

// Export module
module.exports = router;
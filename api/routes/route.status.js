// Import depenndences 
const express = require('express');
const router = express.Router();
const statusController = require('../controllers/controller.status');

router.route('/get_status_tool').get(statusController.status_tool);
router.route('/changestatus').post(statusController.change_status);

// Export module
module.exports = router;

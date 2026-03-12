const express = require("express");
const drugController = require("../controllers/drugController");
const authMiddleware = require("../middleware/authMiddleware");
const roleMiddleware = require("../middleware/roleMiddleware");

const router = express.Router();

router.get("/", authMiddleware, drugController.getDrugs);
router.post("/", authMiddleware, roleMiddleware(["admin", "pharmacist"]), drugController.addDrug);
router.patch("/:id", authMiddleware, roleMiddleware(["admin", "pharmacist"]), drugController.updateDrug);

module.exports = router;
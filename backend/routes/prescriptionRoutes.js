const express = require("express");
const prescriptionController = require("../controllers/prescriptionController");
const documentController = require("../controllers/documentController");
const authMiddleware = require("../middleware/authMiddleware");
const roleMiddleware = require("../middleware/roleMiddleware");
const idempotencyMiddleware = require("../middleware/idempotencyMiddleware");

const router = express.Router();

// Create full prescription (atomic: prescription + items)
router.post(
  "/full",
  authMiddleware,
  roleMiddleware(["doctor"]),
  prescriptionController.createFullPrescription
);

// Dispense prescription with strict validation and stock protection
router.patch(
  "/:id/dispense",
  authMiddleware,
  roleMiddleware(["pharmacist"]),
  idempotencyMiddleware,
  prescriptionController.dispensePrescription
);

// Fetch all pending prescriptions (Pharmacist/Admin)
router.get(
  "/pending",
  authMiddleware,
  roleMiddleware(["pharmacist", "admin"]),
  prescriptionController.getPendingPrescriptions
);

// Fetch prescriptions by patient
router.get(
  "/patient/:id",
  authMiddleware,
  roleMiddleware(["doctor", "admin"]),
  prescriptionController.getPrescriptionsByPatient
);

// Fetch prescriptions by doctor
router.get(
  "/doctor/:id",
  authMiddleware,
  roleMiddleware(["doctor", "admin"]),
  prescriptionController.getPrescriptionsByDoctor
);

// Get plain-language / AI explanation for a single prescription
// Doctors (own), Admins (all), Patients (own)
router.get(
  "/:id/explained",
  authMiddleware,
  roleMiddleware(["doctor", "admin", "patient"]),
  documentController.getExplanation
);

module.exports = router;
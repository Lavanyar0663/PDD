const asyncHandler = require("express-async-handler");
const db = require("../db");
const prescriptionService = require("../services/prescriptionService");

exports.createFullPrescription = asyncHandler(async (req, res) => {
    const { patient_id, diagnosis, remarks, drugs } = req.body;

    // Assigning doctor from the injected JWT payload
    const doctor_id = req.user.id;

    if (!patient_id || !diagnosis || !drugs) {
        const err = new Error("Missing required fields: patient_id, diagnosis, drugs");
        err.status = 400;
        throw err;
    }

    const result = await prescriptionService.createPrescription({
        patient_id, doctor_id, diagnosis, remarks, drugs
    });

    res.status(201).json({
        message: "Prescription created successfully",
        prescription: result
    });
});

exports.dispensePrescription = asyncHandler(async (req, res) => {
    const { id } = req.params;
    const idempotencyKey = req.idempotencyKey; // From idempotencyMiddleware

    const result = await prescriptionService.dispensePrescription(id, idempotencyKey);
    res.status(200).json(result);
});

/* FETCH ENDPOINTS */
exports.getPrescriptionsByPatient = (req, res, next) => {
    const patientId = req.params.id;
    const { id: userId, role } = req.user;

    let sql = "SELECT * FROM prescription WHERE patient_id = ?";
    let params = [patientId];

    // Doctors can only view their own prescriptions for this patient
    if (role === "doctor") {
        sql += " AND doctor_id = ?";
        params.push(userId);
    }

    db.query(sql, params, (err, results) => {
        if (err) return next(err);
        res.status(200).json(results);
    });
};

exports.getPrescriptionsByDoctor = (req, res, next) => {
    const doctorId = req.params.id;
    const { id: userId, role } = req.user;

    // Strict check: Doctor can only fetch their own
    if (role === "doctor" && String(userId) !== String(doctorId)) {
        const err = new Error("Forbidden: You may only query your own prescriptions.");
        err.status = 403;
        return next(err);
    }

    const sql = "SELECT * FROM prescription WHERE doctor_id = ?";

    db.query(sql, [doctorId], (err, results) => {
        if (err) return next(err);
        res.status(200).json(results);
    });
};

exports.getPendingPrescriptions = (req, res, next) => {
    // Only Pharmacists and Admins should access the global pending queue
    const sql = "SELECT * FROM prescription WHERE status = 'pending' ORDER BY created_at DESC";

    db.query(sql, [], (err, results) => {
        if (err) return next(err);
        res.status(200).json(results);
    });
};

const db = require("../db");

exports.getPatients = (req, res, next) => {
    db.query("SELECT * FROM patients WHERE is_active = 1", (err, result) => {
        if (err) return next(err);
        res.json(result);
    });
};

exports.addPatient = (req, res, next) => {
    const { name, age, gender, phone, doctor_id } = req.body;

    if (!name || !age || !gender || !phone) {
        const err = new Error("Missing required fields: name, age, gender, phone");
        err.status = 400;
        return next(err);
    }

    const sql =
        "INSERT INTO patients (name, age, gender, phone, doctor_id) VALUES (?, ?, ?, ?, ?)";

    db.query(sql, [name, age, gender, phone, doctor_id], (err) => {
        if (err) return next(err);
        res.status(201).json({ message: "Patient added successfully" });
    });
};

exports.assignDoctor = (req, res, next) => {
    const patientId = req.params.id;
    const { doctor_id } = req.body;

    if (!doctor_id) {
        const err = new Error("Missing doctor_id in request body");
        err.status = 400;
        return next(err);
    }

    db.query("SELECT id FROM doctors WHERE id = ?", [doctor_id], (err, results) => {
        if (err) return next(err);

        if (results.length === 0) {
            const notFound = new Error("Doctor not found");
            notFound.status = 404;
            return next(notFound);
        }

        const sql = "UPDATE patients SET doctor_id = ? WHERE id = ? AND is_active = 1";
        db.query(sql, [doctor_id, patientId], (assignErr, updateResult) => {
            if (assignErr) return next(assignErr);

            if (updateResult.affectedRows === 0) {
                const notFound = new Error("Patient not found");
                notFound.status = 404;
                return next(notFound);
            }

            res.status(200).json({ message: "Doctor assigned to patient successfully" });
        });
    });
};

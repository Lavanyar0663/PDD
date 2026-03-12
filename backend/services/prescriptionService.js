const { withTransaction } = require("../utils/dbHelper");

exports.createPrescription = async (data) => {
    return withTransaction(async (connection) => {
        const { patient_id, doctor_id, diagnosis, remarks, drugs } = data;

        // 1. Insert prescription
        const [prescResult] = await connection.query(
            `INSERT INTO prescription (patient_id, doctor_id, diagnosis, remarks, status) 
       VALUES (?, ?, ?, ?, 'CREATED')`,
            [patient_id, doctor_id, diagnosis, remarks]
        );

        const prescriptionId = prescResult.insertId;

        // 2. Insert all drugs atomtically
        if (drugs && drugs.length > 0) {
            const drugValues = drugs.map((drug) => [
                prescriptionId,
                drug.drug_id,
                drug.quantity,
                drug.frequency
            ]);

            await connection.query(
                `INSERT INTO prescription_drugs (prescription_id, drug_id, quantity, frequency) 
         VALUES ?`,
                [drugValues]
            );
        }

        return { prescription_id: prescriptionId, status: "CREATED" };
    });
};

exports.dispensePrescription = async (prescriptionId, idempotencyKey) => {
    return withTransaction(async (connection) => {
        // 1. Fetch prescription and strictly check state 
        // SELECT ... FOR UPDATE locks the row against concurrent dispensers
        const [prescRows] = await connection.query(
            "SELECT status FROM prescription WHERE id = ? FOR UPDATE",
            [prescriptionId]
        );

        if (prescRows.length === 0) {
            throw { status: 404, message: "Prescription not found" };
        }

        if (prescRows[0].status !== "CREATED") {
            throw { status: 400, message: `Cannot dispense. Current status is ${prescRows[0].status}` };
        }

        // 2. Fetch required drugs
        const [drugRows] = await connection.query(
            "SELECT drug_id, quantity FROM prescription_drugs WHERE prescription_id = ?",
            [prescriptionId]
        );

        // 3. For each item: validate stock and deduct atomtically
        for (const item of drugRows) {
            const [stockRows] = await connection.query(
                "SELECT stock, drug_name FROM drug_master WHERE id = ? FOR UPDATE",
                [item.drug_id]
            );

            if (stockRows.length === 0) {
                throw { status: 400, message: `Drug ID ${item.drug_id} not found locally` };
            }

            const currentStock = stockRows[0].stock;

            if (currentStock < item.quantity) {
                throw {
                    status: 400,
                    message: `Insufficient stock for ${stockRows[0].drug_name}. Requested: ${item.quantity}, Available: ${currentStock}`
                };
            }

            // Deduct
            await connection.query(
                "UPDATE drug_master SET stock = stock - ? WHERE id = ?",
                [item.quantity, item.drug_id]
            );
        }

        // 4. Update prescription status
        await connection.query(
            "UPDATE prescription SET status = 'DISPENSED' WHERE id = ?",
            [prescriptionId]
        );

        // 5. Insert dispense log for idempotency (so future retries pass the middleware but hit this success check)
        await connection.query(
            "INSERT INTO dispense_logs (prescription_id, idempotency_key) VALUES (?, ?)",
            [prescriptionId, idempotencyKey]
        );

        return { message: "Prescription dispensed successfully" };
    });
};

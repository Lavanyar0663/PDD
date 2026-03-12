const asyncHandler = require("express-async-handler");
const db = require("../db");

exports.getDrugs = (req, res, next) => {
    db.query("SELECT * FROM drug_master WHERE is_active = 1", (err, result) => {
        if (err) return next(err);
        res.json(result);
    });
};

exports.addDrug = asyncHandler(async (req, res) => {
    const { name, stock } = req.body;

    if (!name) {
        const err = new Error("Drug name is required");
        err.status = 400;
        throw err;
    }

    const defaultStock = stock !== undefined ? stock : 100;

    await new Promise((resolve, reject) => {
        db.query(
            "INSERT INTO drug_master (drug_name, stock, is_active) VALUES (?, ?, TRUE)",
            [name, defaultStock],
            (err, dbRes) => {
                if (err) {
                    if (err.code === "ER_DUP_ENTRY") {
                        const dupErr = new Error("Drug already exists");
                        dupErr.status = 409;
                        return reject(dupErr);
                    }
                    return reject(err);
                }
                resolve(dbRes);
            }
        );
    });

    res.status(201).json({ message: "Drug added successfully" });
});

exports.updateDrug = asyncHandler(async (req, res) => {
    const { id } = req.params;
    const { stock, is_active } = req.body;

    if (stock === undefined && is_active === undefined) {
        const err = new Error("Provide at least stock or is_active to update");
        err.status = 400;
        throw err;
    }

    let updates = [];
    let params = [];

    if (stock !== undefined) {
        updates.push("stock = ?");
        params.push(stock);
    }

    if (is_active !== undefined) {
        updates.push("is_active = ?");
        params.push(is_active ? 1 : 0);
    }

    params.push(id);

    const updateSql = `UPDATE drug_master SET ${updates.join(", ")} WHERE id = ?`;

    const result = await new Promise((resolve, reject) => {
        db.query(updateSql, params, (err, result) => {
            if (err) return reject(err);
            resolve(result);
        });
    });

    if (result.affectedRows === 0) {
        const err = new Error("Drug not found");
        err.status = 404;
        throw err;
    }

    res.status(200).json({ message: "Drug updated successfully" });
});

const db = require("../db");

exports.getDoctors = (req, res, next) => {
  const sql = `
    SELECT 
      id,
      name,
      email,
      specialization,
      created_at
    FROM doctors
  `;

  db.query(sql, (err, result) => {
    if (err) return next(err);
    res.status(200).json(result);
  });
};

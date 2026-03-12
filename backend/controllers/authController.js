const asyncHandler = require("express-async-handler");
const db = require("../db");
const authService = require("../services/authService");

exports.login = asyncHandler(async (req, res) => {
    const { email, password, role } = req.body;

    if (!email || !password || !role) {
        const err = new Error("Email, password and role are required");
        err.status = 400;
        throw err;
    }

    // Query from the unified users table
    const result = await new Promise((resolve, reject) => {
        db.query(`SELECT * FROM users WHERE email=? AND role=?`, [email, role.toLowerCase()], (err, rows) => {
            if (err) return reject(err);
            resolve(rows);
        });
    });

    if (result.length === 0) {
        const err = new Error("User not found or role mismatch");
        err.status = 404;
        throw err;
    }

    const user = result[0];

    // Check if user is active
    if (user.is_active === 0) {
        const err = new Error("Account is inactive. Please contact administrator.");
        err.status = 403;
        throw err;
    }

    const match = await authService.comparePassword(password, user.password);
    if (!match) {
        const err = new Error("Invalid credentials");
        err.status = 401;
        throw err;
    }

    const token = authService.generateToken({
        id: user.id,
        email: user.email,
        role: user.role
    });

    res.json({
        message: "Login successful",
        token: token,
        user: {
            id: user.id,
            name: user.name,
            email: user.email,
            role: user.role
        }
    });
});

exports.forgotPassword = asyncHandler(async (req, res) => {
    console.log(`[AUTH] Forgot Password requested for: ${req.body.email}`);
    const { email } = req.body;
    if (!email) {
        console.log("[AUTH] Error: Email is missing from request body");
        const err = new Error("Email is required");
        err.status = 400;
        throw err;
    }

    const result = await new Promise((resolve, reject) => {
        db.query("SELECT * FROM users WHERE email = ?", [email], (err, rows) => {
            if (err) {
                console.error(`[AUTH] DB Query Error: ${err.message}`);
                return reject(err);
            }
            resolve(rows);
        });
    });

    if (result.length === 0) {
        console.log(`[AUTH] Error: User not found for email ${email}`);
        const err = new Error("User with this email does not exist");
        err.status = 404;
        throw err;
    }

    // Generate 6-digit OTP
    const otp = Math.floor(100000 + Math.random() * 900000).toString();
    const otpExpiry = new Date(Date.now() + 10 * 60 * 1000); // 10 minutes expiry

    await new Promise((resolve, reject) => {
        db.query("UPDATE users SET otp = ?, otp_expiry = ? WHERE email = ?", [otp, otpExpiry, email], (err) => {
            if (err) return reject(err);
            resolve();
        });
    });

    // In a real app, send actual email. For now, we log it.
    console.log(`OTP for ${email}: ${otp}`);
    res.json({ message: "OTP has been sent to your email.", otp: otp }); // Sending OTP in response for easier testing by user
});

exports.verifyOtp = asyncHandler(async (req, res) => {
    const { email, otp } = req.body;
    if (!email || !otp) {
        const err = new Error("Email and OTP are required");
        err.status = 400;
        throw err;
    }

    const result = await new Promise((resolve, reject) => {
        db.query("SELECT * FROM users WHERE email = ? AND otp = ? AND otp_expiry > NOW()", [email, otp], (err, rows) => {
            if (err) return reject(err);
            resolve(rows);
        });
    });

    if (result.length === 0) {
        const err = new Error("Invalid or expired OTP");
        err.status = 400;
        throw err;
    }

    res.json({ message: "OTP verified successfully." });
});

exports.resetPassword = asyncHandler(async (req, res) => {
    const { email, otp, newPassword } = req.body;
    if (!email || !otp || !newPassword) {
        const err = new Error("Email, OTP and new password are required");
        err.status = 400;
        throw err;
    }

    // Verify OTP again to be safe
    const result = await new Promise((resolve, reject) => {
        db.query("SELECT * FROM users WHERE email = ? AND otp = ? AND otp_expiry > NOW()", [email, otp], (err, rows) => {
            if (err) return reject(err);
            resolve(rows);
        });
    });

    if (result.length === 0) {
        const err = new Error("Invalid or expired OTP");
        err.status = 400;
        throw err;
    }

    const hashedPassword = await bcrypt.hash(newPassword, 10);

    await new Promise((resolve, reject) => {
        db.query("UPDATE users SET password = ?, otp = NULL, otp_expiry = NULL WHERE email = ?", [hashedPassword, email], (err) => {
            if (err) return reject(err);
            resolve();
        });
    });

    res.json({ message: "Password updated successfully." });
});

const bcrypt = require("bcrypt");

exports.register = asyncHandler(async (req, res) => {
    const { name, email, password, role } = req.body;

    if (!name || !email || !password || !role) {
        const err = new Error("Missing required fields: name, email, password, role");
        err.status = 400;
        throw err;
    }

    const validRoles = ["admin", "doctor", "pharmacist", "patient"];
    if (!validRoles.includes(role)) {
        const err = new Error("Invalid role specified");
        err.status = 400;
        throw err;
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const result = await new Promise((resolve, reject) => {
        db.query(
            "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
            [name, email, hashedPassword, role],
            (err, dbRes) => {
                if (err) {
                    if (err.code === "ER_DUP_ENTRY") {
                        const dupErr = new Error("Email already registered");
                        dupErr.status = 409;
                        return reject(dupErr);
                    }
                    return reject(err);
                }
                resolve(dbRes);
            }
        );
    });

    res.status(201).json({
        id: result.insertId,
        name,
        email,
        role
    });
});

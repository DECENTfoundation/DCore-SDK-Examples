import express from "express";
import { Result, validationResult } from "express-validator/check";

export const catchError = (fn: express.Handler) => {
    const handleToNext: express.Handler = (req, res, next) => fn(req, res, next).catch(next);
    return handleToNext;
};

export const handleValidationErrors: express.Handler = (req, res, next) => {
    const errors: Result<{ location: string }> = validationResult(req);
    if (!errors.isEmpty()) {
        const errorsRes = errors.array().map((err) => {
            switch (err.location) {
                case "query":
                    return { code: 10, ...err };
                case "params":
                    return { code: 11, ...err };
                default:
                    return err;
            }
        });
        return res.status(400).json({ errors: errorsRes });
    }
    next();
};

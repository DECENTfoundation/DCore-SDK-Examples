import express from "express";
import { body } from "express-validator/check";

import { isTransfer } from "lib/validators";
import { catchError, handleValidationErrors } from "server/middlewares";
import { transferService } from "services/dcore";

const router = express.Router();

router.post("/", [
    body("transfer").exists().custom(isTransfer),
    body("key").exists().isString(),
], handleValidationErrors, catchError(async (req: express.Request, res: express.Response) => {
    const { transactionSigned } = req.body;

    const transactionStatus = await transferService.submit(transactionSigned);
    res.json(transactionStatus);
}));

export default router;

import express from "express";
import { query } from "express-validator/check";
import _ from "lodash";
// import * as log4js from "log4js";
import { catchError, handleValidationErrors } from "server/middlewares";
import { addressService } from "services/dcore";

// const l = log4js.getLogger("Routes address");
const router = express.Router();

router.get("/", [
    query("name").exists().isString(),
], handleValidationErrors, catchError(async (req: express.Request, res: express.Response) => {
    const { name } = req.query;

    const data = await addressService.getAccount(name);

    res.json(data);
}));

export default router;

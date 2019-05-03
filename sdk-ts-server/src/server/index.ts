import * as express from "express";

import addressRouter from "server/routes/address";
import transferRouter from "server/routes/transfer";

const router = express.Router();

router.use("/address", addressRouter);
router.use("/transfer", transferRouter);

export default router;

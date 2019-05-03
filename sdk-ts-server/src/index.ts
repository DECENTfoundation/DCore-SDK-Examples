import * as log4js from "log4js";
const log = log4js.getLogger("server");

import app from "app";

export const server = app.listen(Number(process.env.PORT), () => {
    log.info("process %s listening at http://%s:%s", process.pid, process.env.HOST, process.env.PORT);
});

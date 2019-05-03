import "reflect-metadata";

import "env";
import express from "express";
import * as log4js from "log4js";
import logger from "morgan";
import * as path from "path";

import v1Router from "server";

log4js.configure({
    appenders: { out: { type: "stdout" } },
    categories: { default: { appenders: ["out"], level: "trace" } },
});
const l = log4js.getLogger("App");

l.info("Path to env file:", path.resolve(process.env.ENV_PATH || ".env"));

const app = express();

app.all("*", (req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By", "Decent");
    res.header("Content-Type", "application/json;charset=utf-8");
    next();
});

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use("/hck", (req, res) => res.status(200).send());

app.use(`/`, v1Router);

// catch 404 and forward to error handler
app.use((req, res, next) => {
    return res.sendStatus(404);
});

const errorHandler: express.ErrorRequestHandler = (err, req, res, next) => {
    if (!err.isCustom) {
        l.error(err);
    }
    const error = {
        code: err.isCustom ? err.code : 0,
        location: err.location,
        param: err.param,
        value: err.value,
        msg: err.msg,
        info: {},
    };

    if (req.app.get("env") === "development") {
        error.info = {
            stacktrace: err.data || err.stack,
            message: err.message,
        };
    }

    return res.status(err.status || 500).json({ errors: [error] });
};

app.use(errorHandler);

export default app;

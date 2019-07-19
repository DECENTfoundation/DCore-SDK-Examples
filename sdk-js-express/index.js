require("reflect-metadata");

const WebSocket = require("ws");
const express = require("express");
const { DCoreSdk, Credentials, ChainObject, Address } = require("dcorejs-sdk");

const httpApi = DCoreSdk.createForHttp({ baseUrl: "https://testnet.dcore.io/" });
const socketApi = DCoreSdk.createForWebSocket(() => new WebSocket("wss://testnet-socket.dcore.io/"));
const registrar = new Credentials(ChainObject.parse("1.2.27"), "5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt");

const app = express();
const port = 3000;

app.use(express.json());
app.get("/accounts", async (req, res) => {
    try {
        const result = await httpApi.accountApi.findAll(req.query.q || "").toPromise();
        res.json(result);
    } catch (error) {
        res.json([]);
    }
});

app.get("/accounts/:name", async (req, res) => {
    try {
        const result = await httpApi.accountApi.get(req.params.name).toPromise();
        res.json(result);
    } catch (error) {
        res.status(404).end();
    }
});

app.post("/accounts", async (req, res) => {
    try {
        const exist = await httpApi.accountApi.exist(req.body.name).toPromise();
        if (exist) {
            res.status(409).end();
        } else {
            const result = await socketApi.accountApi.create(registrar, req.body.name, Address.parse(req.body.address)).toPromise();
            res.json(result);
        }
    } catch (error) {
        res.status(400).json(error);
    }
});

app.listen(port, () => console.log(`DCore app listening on port ${port}!`));
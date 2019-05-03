import { DCoreSdk } from "dcorejs-sdk";
// import WebSocket from "ws";

const api =  DCoreSdk.createForHttp({ baseUrl: process.env.DCORE_HTTP_URL! });

export default api;

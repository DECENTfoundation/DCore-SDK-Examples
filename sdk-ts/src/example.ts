import "reflect-metadata";
import { serialize } from "class-transformer";
import { AssetAmount, ChainObject, Credentials, DCoreSdk, OperationHistory, TransactionConfirmation } from "dcorejs-sdk";
import { create } from "rxjs-spy";

const el = <T extends HTMLElement>(id: string) => document.getElementById(id) as T;
const getInputEl = (id: string) => document.getElementById(id) as HTMLInputElement;

const output = el('output');
const creds = new Credentials(ChainObject.parse("1.2.34"), "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn");
const api = DCoreSdk.createForWebSocket(() => new WebSocket("wss://stagesocket.decentgo.com:8090"));
const spy = create();
spy.log();

el("transferButton").onclick = () => {
    example(getInputEl("dct_receiver").value, getInputEl("amount").valueAsNumber, getInputEl("memo").value);
};
el("messageButton").onclick = () => sendMsg(getInputEl("msg_receiver").value, getInputEl("message").value);
el("getHistoryButton").onclick = () => getHistory(getInputEl("accountId").value);

function example(toAccount: string, amount: number, memo: string) {
    output.innerHTML = "Loading ...";
    api.accountApi.transfer(creds, toAccount, new AssetAmount(amount), memo)
        .subscribe((value: TransactionConfirmation) => output.innerHTML = serialize(value));
}

function getHistory(account: string,) {
    output.innerHTML = "Loading ...";
    api.historyApi.listOperations(ChainObject.parse(account))
        .subscribe((value: OperationHistory[]) => output.innerHTML = serialize(value));
}

function sendMsg(toAccount: string, memo: string) {
    output.innerHTML = "Loading ...";
    api.messageApi.sendMessages(creds, [[ChainObject.parse(toAccount), memo]])
        .subscribe((value: TransactionConfirmation) => output.innerHTML = serialize(value));
}

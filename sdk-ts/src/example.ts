import "reflect-metadata";
import { serialize } from "class-transformer";
import { AssetAmount, ChainObject, Credentials, OperationHistory, TransactionConfirmation } from "dcorejs-sdk";
import { DCoreSdk } from 'dcorejs-sdk/dist/DCoreSdk';
import { create } from "rxjs-spy";

const el = <T extends HTMLElement>(id: string) => document.getElementById(id) as T;
const getInputEl = (id: string) => document.getElementById(id) as HTMLInputElement;

const output = el('output');
const creds = new Credentials(ChainObject.parse("1.2.19"), "5KfatbpE1zVdnHgFydT7Cg9hJmUVLN7vQXJkBbzGrNSND3uFmAa");
const api = DCoreSdk.createApiRx(undefined, () => new WebSocket("wss://testnet-socket.dcore.io"));
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
    api.messageApi.send(creds, [[ChainObject.parse(toAccount), memo]])
        .subscribe((value: TransactionConfirmation) => output.innerHTML = serialize(value));
}

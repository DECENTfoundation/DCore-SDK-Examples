import "reflect-metadata";
import { serialize } from "class-transformer";
import { AssetAmount, ChainObject, Credentials, DCoreSdk, TransactionConfirmation } from "dcorejs-sdk";
import { create } from "rxjs-spy";

function el<T extends HTMLElement>(id: string) {
    return document.getElementById(id) as T;
}

const output = el('output');
const amountInput = el<HTMLInputElement>('amount');
const receiverInput = el<HTMLInputElement>('receiver');
const memoInput = el<HTMLInputElement>('memo');
const creds = new Credentials(ChainObject.parse("1.2.34"), "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn");

const api = DCoreSdk.createForWebSocket(() => new WebSocket("wss://stagesocket.decentgo.com:8090"));
const spy = create();
spy.log();

el('transferButton').onclick = () => {
    transfer(receiverInput.value, amountInput.valueAsNumber, memoInput.value);
};

function transfer(toAccount: string, amount: number, memo: string) {
    output.innerHTML = 'Loading ...';
    new TextEncoder().encode("hello");
    api.accountApi.transfer(creds, toAccount, new AssetAmount(amount), memo)
        .subscribe((value: TransactionConfirmation) => output.innerHTML = serialize(value));
}

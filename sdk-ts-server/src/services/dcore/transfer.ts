import {
    AssetAmount,
    Credentials,
    DCoreConstants,
    TransferOperation,
} from "dcorejs-sdk";
import _ from "lodash";
// import * as log4js from "log4js";

import { CustomError } from "lib/errors";
import dcoreApi from "services/dcore/dcore-sdk";
import { Transfer, TransferService } from "services/dcore/models/Transfer";
import createClass, { CreateOperation } from "services/dcore/utils/create-class";

// const l = log4js.getLogger("Dcore transfer");

export default class DCoreTransferService implements TransferService {
    public submit = async (transaction: any) => {
        try {
            const blockData = createClass.blockData(transaction.blockData);
            const ops = transaction.operations.map((op: CreateOperation) => createClass.operation(op));
            const trx = createClass.transaction({ blockData, operations: ops, signatures: transaction.signatures });

            await dcoreApi.broadcastApi.broadcastTrx(trx).toPromise();
            return { msg: "success" };
        } catch (err) {
            throw new CustomError({ msg: err.message || err.msg, value: err.value, status: 400 });
        }
    }

    public create = async ({ from, to, amount }: Transfer, key: string) => {
        try {
            const [fromObj, toObj] = await dcoreApi.accountApi.getAllByNames([from, to]).toPromise();
            if (!fromObj) {
                throw new CustomError({ msg: "From account does not exist", value: from, status: 400 });
            }
            if (!toObj) {
                throw new CustomError({ msg: "To account does not exist", value: to, status: 400 });
            }

            const coreAssetId = DCoreConstants.DCT_ASSET_ID;

            const transferOp = new TransferOperation(fromObj.id, toObj.id, new AssetAmount(amount, coreAssetId));

            const creds = new Credentials(fromObj.id, key);

            const transaction: any = await dcoreApi.transactionApi.createTransaction([transferOp]).toPromise();

            const signedTx = transaction.withSignature(creds.keyPair);

            return {
                operations: signedTx.operations.map((transferOperation: TransferOperation) => {
                    return {
                        ...transferOperation,
                        from: transferOperation.from.objectId,
                        to: transferOperation.to.objectId,
                        amount: { amount: transferOperation.amount.amount.toNumber(), assetId: transferOperation.amount.assetId.objectId },
                        fee: {
                            amount: transferOperation.fee ? transferOperation.fee.amount.toNumber() : process.env.TRANSFER_COST_DEFAULT,
                            assetId: transferOperation.fee ? transferOperation.fee.assetId.objectId : coreAssetId.objectId,
                        },
                    };
                }),
                blockData: {
                    expiration: signedTx.expiration,
                    refBlockNum: signedTx.refBlockNum,
                    refBlockPrefix: signedTx.refBlockPrefix,
                },
                signatures: signedTx.signatures,
            };
        } catch (err) {
            throw new CustomError({ msg: err.message || err.msg, value: err.value, status: 400 });
        }
    }
}

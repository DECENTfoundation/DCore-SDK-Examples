import { deserialize } from "class-transformer";
import {
    BaseOperation,
    BlockData,
    ChainObject,
    DCoreConstants,
    DynamicGlobalProperties,
    Transaction,
    TransferOperation,
} from "dcorejs-sdk";
import _ from "lodash";
import Long from "long";

import moment from "moment";

interface CreateTransactionClass {
    blockData: BlockData;
    operations: BaseOperation[];
    signatures: string[];
}

interface RawAmount {
    amount: number;
    assetId: string;
}

export interface CreateOperation {
    extensions: any[];
    type: number;
    from: string;
    to: string;
    amount: RawAmount;
    fee: RawAmount;
}

const createTransaction = ({ blockData, operations, signatures }: CreateTransactionClass) => {
    const trx = Transaction.create(blockData, operations, process.env.CHAIN_ID!);

    const txExpiration = blockData.expiration;

    trx.expiration = txExpiration;
    trx.signatures = signatures;
    return trx;
};

const createOperation = (operation: CreateOperation) => {
    const rawOp = JSON.stringify(operation);

    // not doing full deserialization - only makes class, that needs to be rewritten afterwards
    const op = deserialize(TransferOperation, rawOp);

    op.extensions = [];

    const fee = {
        assetId: ChainObject.parse(operation.fee.assetId),
        amount: Long.fromNumber(operation.fee.amount),
    };
    op.fee = fee;

    const amount = {
        assetId: ChainObject.parse(operation.amount.assetId),
        amount: Long.fromNumber(operation.amount.amount),
    };
    op.amount = amount;

    op.from = ChainObject.parse(operation.from);
    op.to = ChainObject.parse(operation.to);
    return op;
};

const createBlockData = (blockDataRaw: { refBlockNum: number, refBlockPrefix: Long, expiration: string }) => {
    const blockData = new BlockData({
        headBlockNumber: Long.fromInt(10),
        headBlockId: "1.2.324253",
        time: moment.utc(new Date()),
    } as DynamicGlobalProperties, DCoreConstants.EXPIRATION_DEFAULT);

    blockData.refBlockNum = blockDataRaw.refBlockNum;
    blockData.refBlockPrefix = new Long(blockDataRaw.refBlockPrefix.low, blockDataRaw.refBlockPrefix.high, true);
    blockData.expiration = moment.utc(blockDataRaw.expiration);
    return blockData;
};

export default {
    transaction: createTransaction,
    operation: createOperation,
    blockData: createBlockData,
};

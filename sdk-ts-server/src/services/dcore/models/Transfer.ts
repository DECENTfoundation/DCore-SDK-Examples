import { BaseOperation } from "dcorejs-sdk";

export interface Transfer {
    from: string;
    to: string;
    amount: number;
}

interface DCoreTransaction {
    operations: BaseOperation[];
    blockData: {
        expiration: string,
        refBlockNum: number,
        refBlockPrefix: {
            low: number,
            high: number,
            unsigned: boolean,
        },
    };
    signatures: string[];
}

export interface TransferService {
    submit: (transaction: DCoreTransaction) => Promise<{ msg: string}>;
    create: (tranfer: Transfer, privateKey: string) => Promise<DCoreTransaction>;
}

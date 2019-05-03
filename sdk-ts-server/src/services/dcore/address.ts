// import * as log4js from "log4js";
import { Address, ChainObject, Credentials, ECKeyPair } from "dcorejs-sdk";
import _ from "lodash";
// import * as log4js from "log4js";

import { classToPlain } from "class-transformer";
import { CustomError, NotFoundError } from "lib/errors";
import { isAccountName } from "lib/validators";
import dcoreApi from "services/dcore/dcore-sdk";
import { AddressService } from "services/dcore/models/Address";

// const l = log4js.getLogger("Dcore address");

export default class DCoreAddressService implements AddressService {
    public getAccount = async (address: string) => {
        try {
            if (!isAccountName(address)) {
                throw new CustomError({ msg: "Wrong dct account name format", value: address, status: 400 });
            }
            const accountExists = await dcoreApi.accountApi.exist(address).toPromise();

            if (!accountExists) {
                throw new NotFoundError({ value: address });
            }

            // const dynamicProperties = await dcoreApi.generalApi.getDynamicGlobalProperties().toPromise();
            const account = await dcoreApi.accountApi.getByName(address).toPromise();
            // const accountId = account.id;

            // const transferHistory = await dcoreApi.balanceApi.getAll(accountId).pipe(
            //     map(($) => $.map((amount) => amount.assetId)),
            //     concatMap(($) => {
            //         const u = undefined;
            //         return dcoreApi.historyApi.findAllOperations(accountId, $, u, u, u, u, 50);
            //     }),
            //     map(($) => $.filter(this.isTransferBalanceChange)),
            //     concatMap(($) => {
            //         return from($).pipe(
            //             concatMap((balance) => dcoreApi.blockApi.getHeader(balance.operation.blockNum)),
            //             map((header) => header.timestamp),
            //             toArray(),
            //             map((timestamps) => {
            //                 return $.map((change, i) => {
            //                     const { from: fromAddress, to, amount, memo, fee } =
            //                         change.operation.op as TransferOperation;
            //                     const confirmations = dynamicProperties
            //                         .lastIrreversibleBlockNum
            //                         .sub(change.operation.blockNum)
            //                         .toString();
            //                     return {
            //                         id: change.operation.id.objectId,
            //                         blockHeight: change.operation.blockNum.toNumber(),
            //                         from: fromAddress.objectId,
            //                         to: to.objectId,
            //                         amount: amount.amount.toString(),
            //                         fee: fee ? fee.amount.toString() : "0",
            //                         tokenId: amount.assetId.objectId,
            //                         timestamp: timestamps[i].toISOString(),
            //                         memo: memo && {
            //                             to: memo.to && memo.to.encoded,
            //                             from: memo.from && memo.from.encoded,
            //                             message: memo.message,
            //                             nonce: memo.nonce.toString(),
            //                         },
            //                         confirmations,
            //                     };
            //                 });
            //             }),
            //         );
            //     }),
            // ).toPromise();

            return classToPlain(account);
        } catch (err) {
            throw new CustomError({ value: address, msg: "unknown error", data: err });
        }
    }

    public create = async (name: string | null, publicKey: string | null) => {
        if (name && !isAccountName(name)) {
            throw new CustomError({ msg: "Wrong dct account name format", value: name, status: 400 });
        }

        const newName = await this.getName(name);

        const newKeyPair = this.getKeys(publicKey);

        const registrar = {
            privateKey: process.env.REGISTRAR_PRIV_KEY!,
            id: ChainObject.parse(process.env.REGISTRAR_ID!),
        };

        const key = ECKeyPair.parseWif(registrar.privateKey);
        const credentials =  new Credentials(registrar.id, key);

        await dcoreApi.accountApi.create(credentials, newName, newKeyPair.publicKey).toPromise();

        return {
            name: newName,
            privateKey: newKeyPair.privateKey,
            publicKey: newKeyPair.publicKey.encoded,
        };
    }

    // private isTransferBalanceChange = (change: BalanceChange) => {
    //     const transferTypes = [OperationType.Transfer, OperationType.Transfer2];
    //     return transferTypes.includes(change.operation.op.type);
    // }

    private getName = async (name: string | null) => {
        if (_.isNull(name)) {
            return this.generateName();
        }
        const nameExists = await dcoreApi.accountApi.exist(name).toPromise();

        if (nameExists) {
            throw new CustomError({ msg: "Name already exists", value: name, status: 400 });
        }

        return name;
    }

    private getKeys = (publicKey: string | null) => {
        if (_.isNull(publicKey)) {
            const newKeyPair = ECKeyPair.generate();
            return {
                publicKey: new Address(newKeyPair.publicKey),
                privateKey: newKeyPair.privateWif.encoded,
            };
        }

        const publicKeyAddress = this.getPublicKeyAddress(publicKey);

        return {
            publicKey: publicKeyAddress,
            privateKey: null,
        };
    }

    private generateName = async (): Promise<string> => {
        const randomString = this.generateRandomString();
        if (!isAccountName(randomString)) {
            return this.generateName();
        }
        const nameExists = await dcoreApi.accountApi.exist(randomString).toPromise();
        if (nameExists) {
            return this.generateName();
        }
        return randomString;
    }

    private generateRandomString = () => {
        return _.times(20, () => _.random(35).toString(36)).join("");
    }

    private getPublicKeyAddress = (publicKey: string) => {
        try {
            const publicKeyAddress = Address.parse(publicKey);
            return publicKeyAddress;
        } catch (err) {
            throw new CustomError({ msg: "Could not parse and verify public key", value: publicKey, status: 400 });
        }
    }
}

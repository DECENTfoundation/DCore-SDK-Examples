import _ from "lodash";

const accountRegex = /^(?=.{5,63}$)([a-z][a-z0-9-]+[a-z0-9])(\.[a-z][a-z0-9-]+[a-z0-9])*$/;
const objectIdRegex = /^[1-2]\.([0-9]|1[0-5])\.[0-9]{1,20}$/;
const assetNameRegex = /^(?=.{3,16}$)[A-Z]([A-Z0-9]*\.?[A-Z0-9]*)[A-Z]$/;

export const isAccountName = (stringToCheck: string) => {
    return stringToCheck.match(accountRegex);
};

export const isObjectId = (stringToCheck: string) => {
    return stringToCheck.match(objectIdRegex);
};

export const isAccountObjectId = (stringToCheck: string) => {
    return isObjectId(stringToCheck) && stringToCheck.startsWith("1.2.");
};

export const isAccountAddress = (stringToCheck: string) => {
    return isAccountName(stringToCheck) || isAccountObjectId(stringToCheck);
};

export const isAssetObjectId = (stringToCheck: string) => {
    return isObjectId(stringToCheck) && stringToCheck.startsWith("1.3.");
};

export const isAssetName = (stringToCheck: string) => {
    return stringToCheck.match(assetNameRegex);
};

export const isAssetIdentifier = (stringToCheck: string) => {
    return isAssetName(stringToCheck) || isAssetObjectId(stringToCheck);
};

export const isTransfer = (objectToCheck: any): boolean => {
    const { amount, from, to, ...rest } = objectToCheck;
    return _.isNumber(amount) && _.isString(from) && _.isString(to) && _.isEmpty(rest);
};

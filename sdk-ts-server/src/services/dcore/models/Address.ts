// interface TransferHistory {
//     from: string;
//     to: string;
//     amount: string;
//     confirmations: string;
//     timestamp: string;
//     fee: string;
//     blockHeight: number;
// }

// interface MemoRes { to: string | undefined; from: string | undefined; message: string; nonce: string; }

// interface DctTransferHistory extends TransferHistory {
//     id: string;
//     tokenId: string;
//     memo?: MemoRes;
// }

interface CreateAddress {
    name?: string;
    address?: string;
    privateKey: string | null;
    publicKey: string;
    wif?: string;
}

export interface AddressService {
    // getTransferHistory: (address: string) => Promise<DctTransferHistory[]>;
    create: (name: string | null, publicKey: string | null) => Promise<CreateAddress>;
}

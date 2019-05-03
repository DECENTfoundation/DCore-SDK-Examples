interface CustomErrorConstructor {
    location?: string;
    value: any;
    msg?: string;
    status?: number;
    data?: any;
}

export class CustomError extends Error {
    public location: string | undefined;
    public value: any;
    public msg: string | undefined;
    public status: number | undefined;
    public data: any;
    public isCustom: boolean;

    constructor({ location, value, msg, status, data }: CustomErrorConstructor ) {
        super();
        this.location = location;
        this.value = value;
        this.msg = msg;
        this.status = status;
        this.data = data;
        this.isCustom = true;
        Error.captureStackTrace(this, CustomError);
    }
}

// tslint:disable-next-line: max-classes-per-file
export class NotFoundError extends CustomError {
    constructor({ location, value }: CustomErrorConstructor) {
        super({
            location,
            value,
            msg: "Not found",
            status: 404,
        });
    }
}

export interface IDocument {
    id?: number;
    text?: string;
    resume?: string;
}

export class Document implements IDocument {
    constructor(public id?: number, public text?: string, public resume?: string) {}
}

export interface IDocumentEntity {
    id?: number;
    text?: string;
    resume?: string;
}

export class DocumentEntity implements IDocumentEntity {
    constructor(public id?: number, public text?: string, public resume?: string) {}
}

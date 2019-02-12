import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentEntity } from 'app/shared/model/document/document-entity.model';

type EntityResponseType = HttpResponse<IDocumentEntity>;
type EntityArrayResponseType = HttpResponse<IDocumentEntity[]>;

@Injectable({ providedIn: 'root' })
export class DocumentEntityService {
    public resourceUrl = SERVER_API_URL + 'document/api/document-entities';
    public resourceSearchUrl = SERVER_API_URL + 'document/api/_search/document-entities';

    constructor(protected http: HttpClient) {}

    create(documentEntity: IDocumentEntity): Observable<EntityResponseType> {
        return this.http.post<IDocumentEntity>(this.resourceUrl, documentEntity, { observe: 'response' });
    }

    update(documentEntity: IDocumentEntity): Observable<EntityResponseType> {
        return this.http.put<IDocumentEntity>(this.resourceUrl, documentEntity, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocumentEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

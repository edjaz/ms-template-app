import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocumentEntity } from 'app/shared/model/document/document-entity.model';
import { DocumentEntityService } from './document-entity.service';
import { DocumentEntityComponent } from './document-entity.component';
import { DocumentEntityDetailComponent } from './document-entity-detail.component';
import { DocumentEntityUpdateComponent } from './document-entity-update.component';
import { DocumentEntityDeletePopupComponent } from './document-entity-delete-dialog.component';
import { IDocumentEntity } from 'app/shared/model/document/document-entity.model';

@Injectable({ providedIn: 'root' })
export class DocumentEntityResolve implements Resolve<IDocumentEntity> {
    constructor(private service: DocumentEntityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDocumentEntity> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DocumentEntity>) => response.ok),
                map((documentEntity: HttpResponse<DocumentEntity>) => documentEntity.body)
            );
        }
        return of(new DocumentEntity());
    }
}

export const documentEntityRoute: Routes = [
    {
        path: '',
        component: DocumentEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backofficeApp.documentDocumentEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DocumentEntityDetailComponent,
        resolve: {
            documentEntity: DocumentEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backofficeApp.documentDocumentEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DocumentEntityUpdateComponent,
        resolve: {
            documentEntity: DocumentEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backofficeApp.documentDocumentEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DocumentEntityUpdateComponent,
        resolve: {
            documentEntity: DocumentEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backofficeApp.documentDocumentEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentEntityPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DocumentEntityDeletePopupComponent,
        resolve: {
            documentEntity: DocumentEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backofficeApp.documentDocumentEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

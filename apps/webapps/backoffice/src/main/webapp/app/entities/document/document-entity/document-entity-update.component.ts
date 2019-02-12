import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDocumentEntity } from 'app/shared/model/document/document-entity.model';
import { DocumentEntityService } from './document-entity.service';

@Component({
    selector: 'app-document-entity-update',
    templateUrl: './document-entity-update.component.html'
})
export class DocumentEntityUpdateComponent implements OnInit {
    documentEntity: IDocumentEntity;
    isSaving: boolean;

    constructor(protected documentEntityService: DocumentEntityService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentEntity }) => {
            this.documentEntity = documentEntity;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentEntity.id !== undefined) {
            this.subscribeToSaveResponse(this.documentEntityService.update(this.documentEntity));
        } else {
            this.subscribeToSaveResponse(this.documentEntityService.create(this.documentEntity));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentEntity>>) {
        result.subscribe((res: HttpResponse<IDocumentEntity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

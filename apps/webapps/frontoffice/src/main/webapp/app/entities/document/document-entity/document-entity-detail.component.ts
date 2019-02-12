import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentEntity } from 'app/shared/model/document/document-entity.model';

@Component({
    selector: 'app-document-entity-detail',
    templateUrl: './document-entity-detail.component.html'
})
export class DocumentEntityDetailComponent implements OnInit {
    documentEntity: IDocumentEntity;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentEntity }) => {
            this.documentEntity = documentEntity;
        });
    }

    previousState() {
        window.history.back();
    }
}

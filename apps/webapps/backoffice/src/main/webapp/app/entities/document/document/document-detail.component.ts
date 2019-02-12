import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocument } from 'app/shared/model/document/document.model';

@Component({
    selector: 'app-document-detail',
    templateUrl: './document-detail.component.html'
})
export class DocumentDetailComponent implements OnInit {
    document: IDocument;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ document }) => {
            this.document = document;
        });
    }

    previousState() {
        window.history.back();
    }
}

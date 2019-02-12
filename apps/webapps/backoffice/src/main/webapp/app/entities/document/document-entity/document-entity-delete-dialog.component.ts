import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentEntity } from 'app/shared/model/document/document-entity.model';
import { DocumentEntityService } from './document-entity.service';

@Component({
    selector: 'app-document-entity-delete-dialog',
    templateUrl: './document-entity-delete-dialog.component.html'
})
export class DocumentEntityDeleteDialogComponent {
    documentEntity: IDocumentEntity;

    constructor(
        protected documentEntityService: DocumentEntityService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentEntityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentEntityListModification',
                content: 'Deleted an documentEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'app-document-entity-delete-popup',
    template: ''
})
export class DocumentEntityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentEntity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DocumentEntityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.documentEntity = documentEntity;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/document-entity', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/document-entity', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

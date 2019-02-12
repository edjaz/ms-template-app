/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FrontofficeTestModule } from '../../../../test.module';
import { DocumentEntityDeleteDialogComponent } from 'app/entities/document/document-entity/document-entity-delete-dialog.component';
import { DocumentEntityService } from 'app/entities/document/document-entity/document-entity.service';

describe('Component Tests', () => {
    describe('DocumentEntity Management Delete Component', () => {
        let comp: DocumentEntityDeleteDialogComponent;
        let fixture: ComponentFixture<DocumentEntityDeleteDialogComponent>;
        let service: DocumentEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FrontofficeTestModule],
                declarations: [DocumentEntityDeleteDialogComponent]
            })
                .overrideTemplate(DocumentEntityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

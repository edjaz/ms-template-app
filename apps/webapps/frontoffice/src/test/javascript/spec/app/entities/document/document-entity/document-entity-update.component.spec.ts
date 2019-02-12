/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FrontofficeTestModule } from '../../../../test.module';
import { DocumentEntityUpdateComponent } from 'app/entities/document/document-entity/document-entity-update.component';
import { DocumentEntityService } from 'app/entities/document/document-entity/document-entity.service';
import { DocumentEntity } from 'app/shared/model/document/document-entity.model';

describe('Component Tests', () => {
    describe('DocumentEntity Management Update Component', () => {
        let comp: DocumentEntityUpdateComponent;
        let fixture: ComponentFixture<DocumentEntityUpdateComponent>;
        let service: DocumentEntityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FrontofficeTestModule],
                declarations: [DocumentEntityUpdateComponent]
            })
                .overrideTemplate(DocumentEntityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentEntityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentEntityService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DocumentEntity(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.documentEntity = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DocumentEntity();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.documentEntity = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

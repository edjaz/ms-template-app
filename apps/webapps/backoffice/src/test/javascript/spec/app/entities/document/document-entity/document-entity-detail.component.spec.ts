/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackofficeTestModule } from '../../../../test.module';
import { DocumentEntityDetailComponent } from 'app/entities/document/document-entity/document-entity-detail.component';
import { DocumentEntity } from 'app/shared/model/document/document-entity.model';

describe('Component Tests', () => {
    describe('DocumentEntity Management Detail Component', () => {
        let comp: DocumentEntityDetailComponent;
        let fixture: ComponentFixture<DocumentEntityDetailComponent>;
        const route = ({ data: of({ documentEntity: new DocumentEntity(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BackofficeTestModule],
                declarations: [DocumentEntityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentEntityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentEntityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentEntity).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BackofficeSharedModule } from 'app/shared';
import {
    DocumentEntityComponent,
    DocumentEntityDetailComponent,
    DocumentEntityUpdateComponent,
    DocumentEntityDeletePopupComponent,
    DocumentEntityDeleteDialogComponent,
    documentEntityRoute,
    documentEntityPopupRoute
} from './';

const ENTITY_STATES = [...documentEntityRoute, ...documentEntityPopupRoute];

@NgModule({
    imports: [BackofficeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentEntityComponent,
        DocumentEntityDetailComponent,
        DocumentEntityUpdateComponent,
        DocumentEntityDeleteDialogComponent,
        DocumentEntityDeletePopupComponent
    ],
    entryComponents: [
        DocumentEntityComponent,
        DocumentEntityUpdateComponent,
        DocumentEntityDeleteDialogComponent,
        DocumentEntityDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DocumentDocumentEntityModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

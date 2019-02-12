import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { FrontofficeSharedLibsModule, FrontofficeSharedCommonModule, AppLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [FrontofficeSharedLibsModule, FrontofficeSharedCommonModule],
    declarations: [AppLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [AppLoginModalComponent],
    exports: [FrontofficeSharedCommonModule, AppLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrontofficeSharedModule {
    static forRoot() {
        return {
            ngModule: FrontofficeSharedModule
        };
    }
}

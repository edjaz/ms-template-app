import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { BackofficeSharedLibsModule, BackofficeSharedCommonModule, AppLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [BackofficeSharedLibsModule, BackofficeSharedCommonModule],
    declarations: [AppLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [AppLoginModalComponent],
    exports: [BackofficeSharedCommonModule, AppLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackofficeSharedModule {
    static forRoot() {
        return {
            ngModule: BackofficeSharedModule
        };
    }
}

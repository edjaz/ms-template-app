import { NgModule } from '@angular/core';

import { BackofficeSharedLibsModule, FindLanguageFromKeyPipe, AppAlertComponent, AppAlertErrorComponent } from './';

@NgModule({
    imports: [BackofficeSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, AppAlertComponent, AppAlertErrorComponent],
    exports: [BackofficeSharedLibsModule, FindLanguageFromKeyPipe, AppAlertComponent, AppAlertErrorComponent]
})
export class BackofficeSharedCommonModule {}

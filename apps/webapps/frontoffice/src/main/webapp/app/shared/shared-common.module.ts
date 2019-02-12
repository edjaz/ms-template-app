import { NgModule } from '@angular/core';

import { FrontofficeSharedLibsModule, FindLanguageFromKeyPipe, AppAlertComponent, AppAlertErrorComponent } from './';

@NgModule({
    imports: [FrontofficeSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, AppAlertComponent, AppAlertErrorComponent],
    exports: [FrontofficeSharedLibsModule, FindLanguageFromKeyPipe, AppAlertComponent, AppAlertErrorComponent]
})
export class FrontofficeSharedCommonModule {}

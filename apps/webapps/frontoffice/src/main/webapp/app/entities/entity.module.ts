import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'document',
                loadChildren: './document/document/document.module#DocumentDocumentModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer/customer.module#CustomerCustomerModule'
            },
            {
                path: 'notification',
                loadChildren: './notification/notification/notification.module#NotificationNotificationModule'
            },
            {
                path: 'document-entity',
                loadChildren: './document/document-entity/document-entity.module#DocumentDocumentEntityModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrontofficeEntityModule {}

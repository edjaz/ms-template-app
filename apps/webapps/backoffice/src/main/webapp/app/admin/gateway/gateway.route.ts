import { Route } from '@angular/router';

import { AppGatewayComponent } from './gateway.component';

export const gatewayRoute: Route = {
    path: 'gateway',
    component: AppGatewayComponent,
    data: {
        pageTitle: 'gateway.title'
    }
};

import { Component, OnInit } from '@angular/core';

import { GatewayRoutesService } from './gateway-routes.service';
import { GatewayRoute } from './gateway-route.model';

@Component({
    selector: 'app-gateway',
    templateUrl: './gateway.component.html',
    providers: [GatewayRoutesService]
})
export class AppGatewayComponent implements OnInit {
    gatewayRoutes: GatewayRoute[];
    updatingRoutes: boolean;

    constructor(private gatewayRoutesService: GatewayRoutesService) {}

    ngOnInit() {
        this.refresh();
    }

    refresh() {
        this.updatingRoutes = true;
        this.gatewayRoutesService.findAll().subscribe(gatewayRoutes => {
            this.gatewayRoutes = gatewayRoutes;
            this.updatingRoutes = false;
        });
    }
}

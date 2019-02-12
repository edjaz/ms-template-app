import { Route } from '@angular/router';

import { AppTrackerComponent } from './tracker.component';

export const trackerRoute: Route = {
    path: 'app-tracker',
    component: AppTrackerComponent,
    data: {
        pageTitle: 'tracker.title'
    }
};

import { SpyObject } from './spyobject';
import { AppTrackerService } from 'app/core/tracker/tracker.service';

export class MockTrackerService extends SpyObject {
    constructor() {
        super(AppTrackerService);
    }

    connect() {}
}

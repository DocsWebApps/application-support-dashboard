import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import {
    IncidentUpdatesComponent,
    IncidentUpdatesUpdateComponent,
    IncidentUpdatesDeletePopupComponent,
    IncidentUpdatesDeleteDialogComponent,
    incidentUpdatesRoute,
    incidentUpdatesPopupRoute
} from './';

const ENTITY_STATES = [...incidentUpdatesRoute, ...incidentUpdatesPopupRoute];

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IncidentUpdatesComponent,
        IncidentUpdatesUpdateComponent,
        IncidentUpdatesDeleteDialogComponent,
        IncidentUpdatesDeletePopupComponent
    ],
    entryComponents: [
        IncidentUpdatesComponent,
        IncidentUpdatesUpdateComponent,
        IncidentUpdatesDeleteDialogComponent,
        IncidentUpdatesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardIncidentUpdatesModule {}

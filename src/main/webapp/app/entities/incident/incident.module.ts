import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import {
    IncidentComponent,
    IncidentDetailComponent,
    IncidentUpdateComponent,
    IncidentDeletePopupComponent,
    IncidentDeleteDialogComponent,
    incidentRoute,
    incidentPopupRoute
} from './';

const ENTITY_STATES = [...incidentRoute, ...incidentPopupRoute];

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IncidentComponent,
        IncidentDetailComponent,
        IncidentUpdateComponent,
        IncidentDeleteDialogComponent,
        IncidentDeletePopupComponent
    ],
    entryComponents: [IncidentComponent, IncidentUpdateComponent, IncidentDeleteDialogComponent, IncidentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardIncidentModule {}

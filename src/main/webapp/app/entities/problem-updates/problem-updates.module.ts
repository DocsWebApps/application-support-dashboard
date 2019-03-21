import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import {
    ProblemUpdatesComponent,
    ProblemUpdatesUpdateComponent,
    ProblemUpdatesDeletePopupComponent,
    ProblemUpdatesDeleteDialogComponent,
    problemUpdatesRoute,
    problemUpdatesPopupRoute
} from './';

const ENTITY_STATES = [...problemUpdatesRoute, ...problemUpdatesPopupRoute];

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProblemUpdatesComponent,
        ProblemUpdatesUpdateComponent,
        ProblemUpdatesDeleteDialogComponent,
        ProblemUpdatesDeletePopupComponent
    ],
    entryComponents: [
        ProblemUpdatesComponent,
        ProblemUpdatesUpdateComponent,
        ProblemUpdatesDeleteDialogComponent,
        ProblemUpdatesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardProblemUpdatesModule {}

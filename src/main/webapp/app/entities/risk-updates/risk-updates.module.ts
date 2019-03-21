import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import {
    RiskUpdatesComponent,
    RiskUpdatesUpdateComponent,
    RiskUpdatesDeletePopupComponent,
    RiskUpdatesDeleteDialogComponent,
    riskUpdatesRoute,
    riskUpdatesPopupRoute
} from './';

const ENTITY_STATES = [...riskUpdatesRoute, ...riskUpdatesPopupRoute];

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RiskUpdatesComponent, RiskUpdatesUpdateComponent, RiskUpdatesDeleteDialogComponent, RiskUpdatesDeletePopupComponent],
    entryComponents: [RiskUpdatesComponent, RiskUpdatesUpdateComponent, RiskUpdatesDeleteDialogComponent, RiskUpdatesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardRiskUpdatesModule {}

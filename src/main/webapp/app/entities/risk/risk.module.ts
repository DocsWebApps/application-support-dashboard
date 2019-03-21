import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import { RiskComponent, RiskUpdateComponent, RiskDeletePopupComponent, RiskDeleteDialogComponent, riskRoute, riskPopupRoute } from './';

const ENTITY_STATES = [...riskRoute, ...riskPopupRoute];

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RiskComponent, RiskUpdateComponent, RiskDeleteDialogComponent, RiskDeletePopupComponent],
    entryComponents: [RiskComponent, RiskUpdateComponent, RiskDeleteDialogComponent, RiskDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardRiskModule {}

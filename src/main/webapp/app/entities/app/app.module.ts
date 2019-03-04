import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import {
    AppComponent,
    AppDetailComponent,
    AppUpdateComponent,
    AppDeletePopupComponent,
    AppDeleteDialogComponent,
    appRoute,
    appPopupRoute
} from './';

const ENTITY_STATES = [...appRoute, ...appPopupRoute];

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AppComponent, AppDetailComponent, AppUpdateComponent, AppDeleteDialogComponent, AppDeletePopupComponent],
    entryComponents: [AppComponent, AppUpdateComponent, AppDeleteDialogComponent, AppDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardAppModule {}

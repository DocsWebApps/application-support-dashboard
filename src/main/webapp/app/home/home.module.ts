import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApplicationSupportDashboardSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { BannerComponent } from './banner/banner.component';
import { StatusComponent } from './status/status.component';
import { LogoComponent } from './logo/logo.component';

@NgModule({
    imports: [ApplicationSupportDashboardSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent, BannerComponent, StatusComponent, LogoComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardHomeModule {}

import { NgModule } from '@angular/core';

import { ApplicationSupportDashboardSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ApplicationSupportDashboardSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ApplicationSupportDashboardSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ApplicationSupportDashboardSharedCommonModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'app',
                loadChildren: './app/app.module#ApplicationSupportDashboardAppModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#ApplicationSupportDashboardIncidentModule'
            },
            {
                path: 'incident-updates',
                loadChildren: './incident-updates/incident-updates.module#ApplicationSupportDashboardIncidentUpdatesModule'
            },
            {
                path: 'problem',
                loadChildren: './problem/problem.module#ApplicationSupportDashboardProblemModule'
            },
            {
                path: 'problem-updates',
                loadChildren: './problem-updates/problem-updates.module#ApplicationSupportDashboardProblemUpdatesModule'
            },
            {
                path: 'risk',
                loadChildren: './risk/risk.module#ApplicationSupportDashboardRiskModule'
            },
            {
                path: 'risk-updates',
                loadChildren: './risk-updates/risk-updates.module#ApplicationSupportDashboardRiskUpdatesModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationSupportDashboardEntityModule {}

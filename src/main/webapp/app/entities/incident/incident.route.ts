import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Incident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { IncidentComponent } from './incident.component';
import { IncidentDetailComponent } from './incident-detail.component';
import { IncidentUpdateComponent } from './incident-update.component';
import { IncidentDeletePopupComponent } from './incident-delete-dialog.component';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentClosePopupComponent } from './incident-close-dialog.component';

@Injectable({ providedIn: 'root' })
export class IncidentResolve implements Resolve<IIncident> {
    constructor(private service: IncidentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncident> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Incident>) => response.ok),
                map((incident: HttpResponse<Incident>) => incident.body)
            );
        }
        return of(new Incident());
    }
}

export const incidentRoute: Routes = [
    {
        path: '',
        component: IncidentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IncidentDetailComponent,
        resolve: {
            incident: IncidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IncidentUpdateComponent,
        resolve: {
            incident: IncidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IncidentUpdateComponent,
        resolve: {
            incident: IncidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incidentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IncidentDeletePopupComponent,
        resolve: {
            incident: IncidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: ':id/close',
        component: IncidentClosePopupComponent,
        resolve: {
            incident: IncidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

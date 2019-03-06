import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IncidentUpdates } from 'app/shared/model/incident-updates.model';
import { IncidentUpdatesService } from './incident-updates.service';
import { IncidentUpdatesComponent } from './incident-updates.component';
import { IncidentUpdatesDetailComponent } from './incident-updates-detail.component';
import { IncidentUpdatesUpdateComponent } from './incident-updates-update.component';
import { IncidentUpdatesDeletePopupComponent } from './incident-updates-delete-dialog.component';
import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';

@Injectable({ providedIn: 'root' })
export class IncidentUpdatesResolve implements Resolve<IIncidentUpdates> {
    constructor(private service: IncidentUpdatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncidentUpdates> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IncidentUpdates>) => response.ok),
                map((incidentUpdates: HttpResponse<IncidentUpdates>) => incidentUpdates.body)
            );
        }
        return of(new IncidentUpdates());
    }
}

export const incidentUpdatesRoute: Routes = [
    {
        path: ':id',
        component: IncidentUpdatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IncidentUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IncidentUpdatesDetailComponent,
        resolve: {
            incidentUpdates: IncidentUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IncidentUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IncidentUpdatesUpdateComponent,
        resolve: {
            incidentUpdates: IncidentUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IncidentUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IncidentUpdatesUpdateComponent,
        resolve: {
            incidentUpdates: IncidentUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IncidentUpdates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incidentUpdatesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IncidentUpdatesDeletePopupComponent,
        resolve: {
            incidentUpdates: IncidentUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IncidentUpdates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

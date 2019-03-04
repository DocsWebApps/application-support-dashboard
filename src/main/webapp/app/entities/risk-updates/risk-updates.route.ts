import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RiskUpdates } from 'app/shared/model/risk-updates.model';
import { RiskUpdatesService } from './risk-updates.service';
import { RiskUpdatesComponent } from './risk-updates.component';
import { RiskUpdatesDetailComponent } from './risk-updates-detail.component';
import { RiskUpdatesUpdateComponent } from './risk-updates-update.component';
import { RiskUpdatesDeletePopupComponent } from './risk-updates-delete-dialog.component';
import { IRiskUpdates } from 'app/shared/model/risk-updates.model';

@Injectable({ providedIn: 'root' })
export class RiskUpdatesResolve implements Resolve<IRiskUpdates> {
    constructor(private service: RiskUpdatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRiskUpdates> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RiskUpdates>) => response.ok),
                map((riskUpdates: HttpResponse<RiskUpdates>) => riskUpdates.body)
            );
        }
        return of(new RiskUpdates());
    }
}

export const riskUpdatesRoute: Routes = [
    {
        path: '',
        component: RiskUpdatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RiskUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RiskUpdatesDetailComponent,
        resolve: {
            riskUpdates: RiskUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RiskUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RiskUpdatesUpdateComponent,
        resolve: {
            riskUpdates: RiskUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RiskUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RiskUpdatesUpdateComponent,
        resolve: {
            riskUpdates: RiskUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RiskUpdates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const riskUpdatesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RiskUpdatesDeletePopupComponent,
        resolve: {
            riskUpdates: RiskUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RiskUpdates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

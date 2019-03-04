import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Risk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';
import { RiskComponent } from './risk.component';
import { RiskDetailComponent } from './risk-detail.component';
import { RiskUpdateComponent } from './risk-update.component';
import { RiskDeletePopupComponent } from './risk-delete-dialog.component';
import { IRisk } from 'app/shared/model/risk.model';

@Injectable({ providedIn: 'root' })
export class RiskResolve implements Resolve<IRisk> {
    constructor(private service: RiskService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRisk> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Risk>) => response.ok),
                map((risk: HttpResponse<Risk>) => risk.body)
            );
        }
        return of(new Risk());
    }
}

export const riskRoute: Routes = [
    {
        path: '',
        component: RiskComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Risks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RiskDetailComponent,
        resolve: {
            risk: RiskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Risks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RiskUpdateComponent,
        resolve: {
            risk: RiskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Risks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RiskUpdateComponent,
        resolve: {
            risk: RiskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Risks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const riskPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RiskDeletePopupComponent,
        resolve: {
            risk: RiskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Risks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

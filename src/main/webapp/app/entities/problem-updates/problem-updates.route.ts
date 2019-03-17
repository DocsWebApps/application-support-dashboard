import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProblemUpdates } from 'app/shared/model/problem-updates.model';
import { ProblemUpdatesService } from './problem-updates.service';
import { ProblemUpdatesComponent } from './problem-updates.component';
import { ProblemUpdatesDetailComponent } from './problem-updates-detail.component';
import { ProblemUpdatesUpdateComponent } from './problem-updates-update.component';
import { ProblemUpdatesDeletePopupComponent } from './problem-updates-delete-dialog.component';
import { IProblemUpdates } from 'app/shared/model/problem-updates.model';

@Injectable({ providedIn: 'root' })
export class ProblemUpdatesResolve implements Resolve<IProblemUpdates> {
    constructor(private service: ProblemUpdatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProblemUpdates> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProblemUpdates>) => response.ok),
                map((problemUpdates: HttpResponse<ProblemUpdates>) => problemUpdates.body)
            );
        }
        return of(new ProblemUpdates());
    }
}

export const problemUpdatesRoute: Routes = [
    {
        path: ':problemID',
        component: ProblemUpdatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProblemUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProblemUpdatesDetailComponent,
        resolve: {
            problemUpdates: ProblemUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProblemUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':problemID/new',
        component: ProblemUpdatesUpdateComponent,
        resolve: {
            problemUpdates: ProblemUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProblemUpdates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProblemUpdatesUpdateComponent,
        resolve: {
            problemUpdates: ProblemUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProblemUpdates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const problemUpdatesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProblemUpdatesDeletePopupComponent,
        resolve: {
            problemUpdates: ProblemUpdatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProblemUpdates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

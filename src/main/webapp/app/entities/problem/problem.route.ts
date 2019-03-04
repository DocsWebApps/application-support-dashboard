import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Problem } from 'app/shared/model/problem.model';
import { ProblemService } from './problem.service';
import { ProblemComponent } from './problem.component';
import { ProblemDetailComponent } from './problem-detail.component';
import { ProblemUpdateComponent } from './problem-update.component';
import { ProblemDeletePopupComponent } from './problem-delete-dialog.component';
import { IProblem } from 'app/shared/model/problem.model';

@Injectable({ providedIn: 'root' })
export class ProblemResolve implements Resolve<IProblem> {
    constructor(private service: ProblemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProblem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Problem>) => response.ok),
                map((problem: HttpResponse<Problem>) => problem.body)
            );
        }
        return of(new Problem());
    }
}

export const problemRoute: Routes = [
    {
        path: '',
        component: ProblemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Problems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProblemDetailComponent,
        resolve: {
            problem: ProblemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Problems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProblemUpdateComponent,
        resolve: {
            problem: ProblemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Problems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProblemUpdateComponent,
        resolve: {
            problem: ProblemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Problems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const problemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProblemDeletePopupComponent,
        resolve: {
            problem: ProblemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Problems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

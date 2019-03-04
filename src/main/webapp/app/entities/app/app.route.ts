import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { App } from 'app/shared/model/app.model';
import { AppService } from './app.service';
import { AppComponent } from './app.component';
import { AppDetailComponent } from './app-detail.component';
import { AppUpdateComponent } from './app-update.component';
import { AppDeletePopupComponent } from './app-delete-dialog.component';
import { IApp } from 'app/shared/model/app.model';

@Injectable({ providedIn: 'root' })
export class AppResolve implements Resolve<IApp> {
    constructor(private service: AppService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApp> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<App>) => response.ok),
                map((app: HttpResponse<App>) => app.body)
            );
        }
        return of(new App());
    }
}

export const appRoute: Routes = [
    {
        path: '',
        component: AppComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AppDetailComponent,
        resolve: {
            app: AppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AppUpdateComponent,
        resolve: {
            app: AppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AppUpdateComponent,
        resolve: {
            app: AppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AppDeletePopupComponent,
        resolve: {
            app: AppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApp } from 'app/shared/model/app.model';
import { AccountService } from 'app/core';
import { AppService } from './app.service';

@Component({
    selector: 'jhi-app',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit, OnDestroy {
    apps: IApp[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected appService: AppService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.appService
            .query()
            .pipe(
                filter((res: HttpResponse<IApp[]>) => res.ok),
                map((res: HttpResponse<IApp[]>) => res.body)
            )
            .subscribe(
                (res: IApp[]) => {
                    this.apps = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApps();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApp) {
        return item.id;
    }

    registerChangeInApps() {
        this.eventSubscriber = this.eventManager.subscribe('appListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

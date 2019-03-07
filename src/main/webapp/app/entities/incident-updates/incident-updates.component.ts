import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { IncidentUpdatesService } from './incident-updates.service';
import { IncidentService } from 'app/entities/incident';
import { ActivatedRoute } from '@angular/router';
import { IIncident } from 'app/shared/model/incident.model';

@Component({
    selector: 'jhi-incident-updates',
    templateUrl: './incident-updates.component.html'
})
export class IncidentUpdatesComponent implements OnInit, OnDestroy {
    incidentUpdates: IIncidentUpdates[];
    incidentID: number;
    incident: IIncident;
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        protected incidentUpdatesService: IncidentUpdatesService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        private route: ActivatedRoute,
        private incidentService: IncidentService
    ) {
        this.incidentUpdates = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    previousState() {
        window.history.back();
    }

    loadAll() {
        this.incidentUpdatesService.incidentQuery(this.incidentID).subscribe(
            (res: HttpResponse<IIncidentUpdates[]>) => {
                this.incidentUpdates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getIncidentDetails() {
        this.incidentService.find(this.incidentID).subscribe(
            (res: HttpResponse<IIncident>) => {
                this.incident = res.body;
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    ngOnInit() {
        this.incidentID = this.route.snapshot.params['incidentID'];
        this.incidentUpdatesService.incidentID = this.incidentID;
        this.getIncidentDetails();
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIncidentUpdates();
    }

    reset() {
        this.page = 0;
        this.incidentUpdates = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIncidentUpdates) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInIncidentUpdates() {
        this.eventSubscriber = this.eventManager.subscribe('incidentUpdatesListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateIncidentUpdates(data: IIncidentUpdates[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.incidentUpdates.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

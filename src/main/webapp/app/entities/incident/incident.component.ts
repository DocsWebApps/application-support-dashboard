import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IIncident } from 'app/shared/model/incident.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { IncidentService } from './incident.service';

@Component({
    selector: 'jhi-incident',
    templateUrl: './incident.component.html',
    styleUrls: ['./incident.component.scss']
})
export class IncidentComponent implements OnInit, OnDestroy {
    incidents: IIncident[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    selectedStatus;
    selectedSeverity;
    clearFilter = false;

    constructor(
        protected incidentService: IncidentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService
    ) {
        this.incidents = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.incidentService
            .query(this.selectedStatus, this.selectedSeverity, {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IIncident[]>) => this.paginateIncidents(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.incidents = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIncidents();
        this.selectedStatus = this.incidentService.selectedStatus;
        this.selectedSeverity = this.incidentService.selectedSeverity;
        this.setFilterButton();
        this.loadAll();
    }

    onClearFilter() {
        this.selectedStatus = 'ALL';
        this.selectedSeverity = 'ALL';
        this.onFilter();
    }

    onFilter() {
        this.page = 0;
        this.incidents = [];
        this.setFilterValues();
        this.setFilterButton();
        this.loadAll();
    }

    setFilterButton() {
        this.clearFilter = this.selectedStatus !== 'ALL' || this.selectedSeverity !== 'ALL';
    }

    setFilterValues() {
        this.incidentService.selectedStatus = this.selectedStatus;
        this.incidentService.selectedSeverity = this.selectedSeverity;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIncident) {
        return item.id;
    }

    registerChangeInIncidents() {
        this.eventSubscriber = this.eventManager.subscribe('incidentListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateIncidents(data: IIncident[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.incidents.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

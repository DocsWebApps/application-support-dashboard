import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRisk } from 'app/shared/model/risk.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { RiskService } from './risk.service';
import { RiskUpdatesService } from 'app/entities/risk-updates';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-risk',
    templateUrl: './risk.component.html',
    styleUrls: ['./risk.component.scss']
})
export class RiskComponent implements OnInit, OnDestroy {
    risks: IRisk[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    selectedStatus;
    selectedPriority;
    clearFilter = false;

    constructor(
        protected riskService: RiskService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        private riskUpdatesService: RiskUpdatesService,
        private router: Router
    ) {
        this.risks = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.riskService
            .query(this.selectedStatus, this.selectedPriority, {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IRisk[]>) => this.paginateRisks(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    setRiskUpdatesReturnPage(riskID) {
        this.riskUpdatesService.returnRoute = '/risk';
        this.router.navigate(['risk-updates', riskID]);
    }

    onClearFilter() {
        this.selectedStatus = 'ALL';
        this.selectedPriority = 'ALL';
        this.onFilter();
    }

    onFilter() {
        this.page = 0;
        this.risks = [];
        this.setFilterValues();
        this.setFilterButton();
        this.loadAll();
    }

    setFilterButton() {
        this.clearFilter = this.selectedStatus !== 'ALL' || this.selectedPriority !== 'ALL';
    }

    setFilterValues() {
        this.riskService.selectedStatus = this.selectedStatus;
        this.riskService.selectedPriority = this.selectedPriority;
    }

    reset() {
        this.page = 0;
        this.risks = [];
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
        this.registerChangeInRisks();
        this.selectedStatus = this.riskService.selectedStatus;
        this.selectedPriority = this.riskService.selectedPriority;
        this.setFilterButton();
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRisk) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInRisks() {
        this.eventSubscriber = this.eventManager.subscribe('riskListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateRisks(data: IRisk[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.risks.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

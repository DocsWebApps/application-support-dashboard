import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRiskUpdates } from 'app/shared/model/risk-updates.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { RiskUpdatesService } from './risk-updates.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RiskService } from 'app/entities/risk';
import { IRisk } from 'app/shared/model/risk.model';
import { IProblem } from 'app/shared/model/problem.model';
import { ProblemService } from 'app/entities/problem';
import { ProblemUpdatesService } from 'app/entities/problem-updates';

@Component({
    selector: 'jhi-risk-updates',
    templateUrl: './risk-updates.component.html',
    styleUrls: ['./risk-updates.component.scss']
})
export class RiskUpdatesComponent implements OnInit, OnDestroy {
    riskUpdates: IRiskUpdates[];
    riskProblems: IProblem[];
    riskID: number;
    risk: IRisk;
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        protected riskUpdatesService: RiskUpdatesService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        private route: ActivatedRoute,
        private riskService: RiskService,
        private router: Router,
        private problemService: ProblemService,
        private problemUpdatesService: ProblemUpdatesService
    ) {
        this.riskProblems = [];
        this.riskUpdates = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    previousState() {
        this.router.navigate([this.riskUpdatesService.returnRoute]);
    }

    setProblemUpdatesReturnPage(problemID) {
        this.problemUpdatesService.returnRoute = '/risk-updates/' + this.risk.id;
        this.router.navigate(['problem-updates', problemID]);
    }

    loadAll() {
        this.riskUpdatesService
            .query(this.riskID, {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IRiskUpdates[]>) => this.paginateRiskUpdates(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.problemService
            .riskProblems(this.riskID, {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProblem[]>) => {
                    this.paginateRiskIncidents(res.body, res.headers);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.riskUpdates = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.riskID = this.route.snapshot.params['riskID'];
        this.riskUpdatesService.riskID = this.riskID;
        this.getRiskDetails();
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRiskUpdates();
    }

    getRiskDetails() {
        this.riskService.find(this.riskID).subscribe(
            (res: HttpResponse<IRisk>) => {
                this.risk = res.body;
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRiskUpdates) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInRiskUpdates() {
        this.eventSubscriber = this.eventManager.subscribe('riskUpdatesListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateRiskUpdates(data: IRiskUpdates[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.riskUpdates.push(data[i]);
        }
    }

    protected paginateRiskIncidents(data: IProblem[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.riskProblems.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

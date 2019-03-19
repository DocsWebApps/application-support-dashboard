import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProblem } from 'app/shared/model/problem.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { ProblemService } from './problem.service';
import { ProblemUpdatesService } from 'app/entities/problem-updates';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-problem',
    templateUrl: './problem.component.html',
    styleUrls: ['./problem.component.scss']
})
export class ProblemComponent implements OnInit, OnDestroy {
    problems: IProblem[];
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
        protected problemService: ProblemService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        private problemUpdatesService: ProblemUpdatesService,
        private router: Router
    ) {
        this.problems = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.problemService
            .query(this.selectedStatus, this.selectedPriority, {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProblem[]>) => this.paginateProblems(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    setProblemUpdatesReturnPage(problemID) {
        this.problemUpdatesService.returnRoute = '/problem';
        this.router.navigate(['problem-updates', problemID]);
    }

    onClearFilter() {
        this.selectedStatus = 'ALL';
        this.selectedPriority = 'ALL';
        this.onFilter();
    }

    onFilter() {
        this.page = 0;
        this.problems = [];
        this.setFilterValues();
        this.setFilterButton();
        this.loadAll();
    }

    setFilterButton() {
        this.clearFilter = this.selectedStatus !== 'ALL' || this.selectedPriority !== 'ALL';
    }

    setFilterValues() {
        this.problemService.selectedStatus = this.selectedStatus;
        this.problemService.selectedPriority = this.selectedPriority;
    }

    reset() {
        this.page = 0;
        this.problems = [];
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
        this.registerChangeInProblems();
        this.selectedStatus = this.problemService.selectedStatus;
        this.selectedPriority = this.problemService.selectedPriority;
        this.setFilterButton();
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProblem) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInProblems() {
        this.eventSubscriber = this.eventManager.subscribe('problemListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateProblems(data: IProblem[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.problems.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

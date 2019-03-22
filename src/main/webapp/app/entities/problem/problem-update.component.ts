import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProblem } from 'app/shared/model/problem.model';
import { ProblemService } from './problem.service';
import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from 'app/entities/risk';

@Component({
    selector: 'jhi-problem-update',
    templateUrl: './problem-update.component.html'
})
export class ProblemUpdateComponent implements OnInit {
    problem: IProblem;
    isSaving: boolean;
    risks: IRisk[];
    openedAtDp: any;
    closedAtDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected problemService: ProblemService,
        protected riskService: RiskService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ problem }) => {
            this.problem = problem;
        });
        this.riskService
            .query('ALL', 'ALL')
            .subscribe((res: HttpResponse<IRisk[]>) => (this.risks = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.problem.id !== undefined) {
            this.subscribeToSaveResponse(this.problemService.update(this.problem));
        } else {
            this.subscribeToSaveResponse(this.problemService.create(this.problem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProblem>>) {
        result.subscribe((res: HttpResponse<IProblem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRiskById(index: number, item: IRisk) {
        return item.id;
    }
}

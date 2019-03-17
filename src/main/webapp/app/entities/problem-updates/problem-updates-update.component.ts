import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProblemUpdates } from 'app/shared/model/problem-updates.model';
import { ProblemUpdatesService } from './problem-updates.service';
import { IProblem } from 'app/shared/model/problem.model';

@Component({
    selector: 'jhi-problem-updates-update',
    templateUrl: './problem-updates-update.component.html'
})
export class ProblemUpdatesUpdateComponent implements OnInit {
    problemUpdates: IProblemUpdates;
    isSaving: boolean;
    problemID: number;
    updatedAtDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected problemUpdatesService: ProblemUpdatesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.problemID = this.problemUpdatesService.problemID;
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ problemUpdates }) => {
            this.problemUpdates = problemUpdates;
        });
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
        this.problemUpdates.probUpdateId = this.problemID;
        this.isSaving = true;
        if (this.problemUpdates.id !== undefined) {
            this.subscribeToSaveResponse(this.problemUpdatesService.update(this.problemUpdates));
        } else {
            this.subscribeToSaveResponse(this.problemUpdatesService.create(this.problemUpdates));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProblemUpdates>>) {
        result.subscribe((res: HttpResponse<IProblemUpdates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProblemById(index: number, item: IProblem) {
        return item.id;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { IProblem } from 'app/shared/model/problem.model';
import { ProblemService } from 'app/entities/problem';

@Component({
    selector: 'jhi-incident-update',
    templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
    incident: IIncident;
    problems: IProblem[];
    isSaving: boolean;
    openedAtDp: any;
    closedAtDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected incidentService: IncidentService,
        protected activatedRoute: ActivatedRoute,
        private problemService: ProblemService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incident }) => {
            this.incident = incident;
        });
        this.problemService
            .query('ALL', 'ALL')
            .subscribe(
                (res: HttpResponse<IProblem[]>) => (this.problems = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.incident.id !== undefined) {
            this.subscribeToSaveResponse(this.incidentService.update(this.incident));
        } else {
            this.subscribeToSaveResponse(this.incidentService.create(this.incident));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe((res: HttpResponse<IIncident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';
import { IncidentUpdatesService } from './incident-updates.service';

@Component({
    selector: 'jhi-incident-updates-update',
    templateUrl: './incident-updates-update.component.html'
})
export class IncidentUpdatesUpdateComponent implements OnInit {
    incidentUpdates: IIncidentUpdates;
    isSaving: boolean;
    incidentID: number;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected incidentUpdatesService: IncidentUpdatesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.incidentID = this.incidentUpdatesService.incidentID;
        this.activatedRoute.data.subscribe(({ incidentUpdates }) => {
            this.incidentUpdates = incidentUpdates;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.incidentUpdates.inUpdateId = this.incidentID;
        if (this.incidentUpdates.id !== undefined) {
            this.subscribeToSaveResponse(this.incidentUpdatesService.update(this.incidentUpdates));
        } else {
            this.subscribeToSaveResponse(this.incidentUpdatesService.create(this.incidentUpdates));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncidentUpdates>>) {
        result.subscribe((res: HttpResponse<IIncidentUpdates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}

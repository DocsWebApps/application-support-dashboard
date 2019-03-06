import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';
import { IncidentUpdatesService } from './incident-updates.service';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/entities/incident';

@Component({
    selector: 'jhi-incident-updates-update',
    templateUrl: './incident-updates-update.component.html'
})
export class IncidentUpdatesUpdateComponent implements OnInit {
    incidentUpdates: IIncidentUpdates;
    isSaving: boolean;

    incidents: IIncident[];
    updatedAtDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected incidentUpdatesService: IncidentUpdatesService,
        protected incidentService: IncidentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incidentUpdates }) => {
            this.incidentUpdates = incidentUpdates;
        });
        // this.incidentService
        //     .query()
        //     .pipe(
        //         filter((mayBeOk: HttpResponse<IIncident[]>) => mayBeOk.ok),
        //         map((response: HttpResponse<IIncident[]>) => response.body)
        //     )
        //     .subscribe((res: IIncident[]) => (this.incidents = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackIncidentById(index: number, item: IIncident) {
        return item.id;
    }
}

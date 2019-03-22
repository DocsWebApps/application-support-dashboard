import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRiskUpdates } from 'app/shared/model/risk-updates.model';
import { RiskUpdatesService } from './risk-updates.service';
import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from 'app/entities/risk';

@Component({
    selector: 'jhi-risk-updates-update',
    templateUrl: './risk-updates-update.component.html'
})
export class RiskUpdatesUpdateComponent implements OnInit {
    riskUpdates: IRiskUpdates;
    isSaving: boolean;
    risks: IRisk[];
    updatedAtDp: any;
    riskID: number;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected riskUpdatesService: RiskUpdatesService,
        protected riskService: RiskService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.riskID = this.riskUpdatesService.riskID;
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ riskUpdates }) => {
            this.riskUpdates = riskUpdates;
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
        this.riskUpdates.riskkUpdateId = this.riskID;
        this.isSaving = true;
        if (this.riskUpdates.id !== undefined) {
            this.subscribeToSaveResponse(this.riskUpdatesService.update(this.riskUpdates));
        } else {
            this.subscribeToSaveResponse(this.riskUpdatesService.create(this.riskUpdates));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRiskUpdates>>) {
        result.subscribe((res: HttpResponse<IRiskUpdates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

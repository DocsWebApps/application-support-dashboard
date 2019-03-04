import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
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

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected riskUpdatesService: RiskUpdatesService,
        protected riskService: RiskService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ riskUpdates }) => {
            this.riskUpdates = riskUpdates;
        });
        this.riskService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRisk[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRisk[]>) => response.body)
            )
            .subscribe((res: IRisk[]) => (this.risks = res), (res: HttpErrorResponse) => this.onError(res.message));
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

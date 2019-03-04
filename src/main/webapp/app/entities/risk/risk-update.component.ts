import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils } from 'ng-jhipster';
import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';

@Component({
    selector: 'jhi-risk-update',
    templateUrl: './risk-update.component.html'
})
export class RiskUpdateComponent implements OnInit {
    risk: IRisk;
    isSaving: boolean;
    openedAtDp: any;
    closedAtDp: any;

    constructor(protected dataUtils: JhiDataUtils, protected riskService: RiskService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ risk }) => {
            this.risk = risk;
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
        this.isSaving = true;
        if (this.risk.id !== undefined) {
            this.subscribeToSaveResponse(this.riskService.update(this.risk));
        } else {
            this.subscribeToSaveResponse(this.riskService.create(this.risk));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRisk>>) {
        result.subscribe((res: HttpResponse<IRisk>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

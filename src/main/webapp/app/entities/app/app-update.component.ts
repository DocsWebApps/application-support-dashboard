import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IApp } from 'app/shared/model/app.model';
import { AppService } from './app.service';

@Component({
    selector: 'jhi-app-update',
    templateUrl: './app-update.component.html'
})
export class AppUpdateComponent implements OnInit {
    app: IApp;
    isSaving: boolean;
    lastProblemDateDp: any;

    constructor(protected appService: AppService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ app }) => {
            this.app = app;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.app.id !== undefined) {
            this.subscribeToSaveResponse(this.appService.update(this.app));
        } else {
            this.subscribeToSaveResponse(this.appService.create(this.app));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IApp>>) {
        result.subscribe((res: HttpResponse<IApp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

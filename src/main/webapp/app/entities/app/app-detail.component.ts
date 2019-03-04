import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApp } from 'app/shared/model/app.model';

@Component({
    selector: 'jhi-app-detail',
    templateUrl: './app-detail.component.html'
})
export class AppDetailComponent implements OnInit {
    app: IApp;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ app }) => {
            this.app = app;
        });
    }

    previousState() {
        window.history.back();
    }
}

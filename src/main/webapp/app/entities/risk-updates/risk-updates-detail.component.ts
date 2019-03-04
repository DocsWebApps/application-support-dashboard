import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRiskUpdates } from 'app/shared/model/risk-updates.model';

@Component({
    selector: 'jhi-risk-updates-detail',
    templateUrl: './risk-updates-detail.component.html'
})
export class RiskUpdatesDetailComponent implements OnInit {
    riskUpdates: IRiskUpdates;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRisk } from 'app/shared/model/risk.model';

@Component({
    selector: 'jhi-risk-detail',
    templateUrl: './risk-detail.component.html'
})
export class RiskDetailComponent implements OnInit {
    risk: IRisk;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}

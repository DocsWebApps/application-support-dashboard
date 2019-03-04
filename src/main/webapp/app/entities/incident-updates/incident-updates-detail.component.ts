import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';

@Component({
    selector: 'jhi-incident-updates-detail',
    templateUrl: './incident-updates-detail.component.html'
})
export class IncidentUpdatesDetailComponent implements OnInit {
    incidentUpdates: IIncidentUpdates;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
}

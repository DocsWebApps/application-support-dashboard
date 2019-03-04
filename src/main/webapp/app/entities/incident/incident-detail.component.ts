import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncident } from 'app/shared/model/incident.model';

@Component({
    selector: 'jhi-incident-detail',
    templateUrl: './incident-detail.component.html'
})
export class IncidentDetailComponent implements OnInit {
    incident: IIncident;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incident }) => {
            this.incident = incident;
        });
    }

    previousState() {
        window.history.back();
    }
}

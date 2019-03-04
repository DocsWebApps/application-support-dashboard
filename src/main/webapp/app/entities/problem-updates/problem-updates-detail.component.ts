import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProblemUpdates } from 'app/shared/model/problem-updates.model';

@Component({
    selector: 'jhi-problem-updates-detail',
    templateUrl: './problem-updates-detail.component.html'
})
export class ProblemUpdatesDetailComponent implements OnInit {
    problemUpdates: IProblemUpdates;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ problemUpdates }) => {
            this.problemUpdates = problemUpdates;
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

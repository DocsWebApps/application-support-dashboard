import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProblem } from 'app/shared/model/problem.model';

@Component({
    selector: 'jhi-problem-detail',
    templateUrl: './problem-detail.component.html'
})
export class ProblemDetailComponent implements OnInit {
    problem: IProblem;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ problem }) => {
            this.problem = problem;
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProblemUpdates } from 'app/shared/model/problem-updates.model';
import { ProblemUpdatesService } from './problem-updates.service';

@Component({
    selector: 'jhi-problem-updates-delete-dialog',
    templateUrl: './problem-updates-delete-dialog.component.html'
})
export class ProblemUpdatesDeleteDialogComponent {
    problemUpdates: IProblemUpdates;

    constructor(
        protected problemUpdatesService: ProblemUpdatesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.problemUpdatesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'problemUpdatesListModification',
                content: 'Deleted an problemUpdates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-problem-updates-delete-popup',
    template: ''
})
export class ProblemUpdatesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ problemUpdates }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProblemUpdatesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.problemUpdates = problemUpdates;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/problem-updates', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/problem-updates', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

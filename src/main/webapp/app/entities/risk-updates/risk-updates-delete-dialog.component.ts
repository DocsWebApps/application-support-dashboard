import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRiskUpdates } from 'app/shared/model/risk-updates.model';
import { RiskUpdatesService } from './risk-updates.service';

@Component({
    selector: 'jhi-risk-updates-delete-dialog',
    templateUrl: './risk-updates-delete-dialog.component.html'
})
export class RiskUpdatesDeleteDialogComponent {
    riskUpdates: IRiskUpdates;

    constructor(
        protected riskUpdatesService: RiskUpdatesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.riskUpdatesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'riskUpdatesListModification',
                content: 'Deleted an riskUpdates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-risk-updates-delete-popup',
    template: ''
})
export class RiskUpdatesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ riskUpdates }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RiskUpdatesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.riskUpdates = riskUpdates;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/risk-updates', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/risk-updates', { outlets: { popup: null } }]);
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

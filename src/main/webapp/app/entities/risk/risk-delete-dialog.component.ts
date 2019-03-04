import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';

@Component({
    selector: 'jhi-risk-delete-dialog',
    templateUrl: './risk-delete-dialog.component.html'
})
export class RiskDeleteDialogComponent {
    risk: IRisk;

    constructor(protected riskService: RiskService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.riskService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'riskListModification',
                content: 'Deleted an risk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-risk-delete-popup',
    template: ''
})
export class RiskDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ risk }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RiskDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.risk = risk;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/risk', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/risk', { outlets: { popup: null } }]);
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

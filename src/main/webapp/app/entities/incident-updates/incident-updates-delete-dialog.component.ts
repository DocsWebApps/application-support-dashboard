import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';
import { IncidentUpdatesService } from './incident-updates.service';

@Component({
    selector: 'jhi-incident-updates-delete-dialog',
    templateUrl: './incident-updates-delete-dialog.component.html'
})
export class IncidentUpdatesDeleteDialogComponent {
    incidentUpdates: IIncidentUpdates;

    constructor(
        protected incidentUpdatesService: IncidentUpdatesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incidentUpdatesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'incidentUpdatesListModification',
                content: 'Deleted an incidentUpdates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-incident-updates-delete-popup',
    template: ''
})
export class IncidentUpdatesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incidentUpdates }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncidentUpdatesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.incidentUpdates = incidentUpdates;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/incident-updates', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/incident-updates', { outlets: { popup: null } }]);
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

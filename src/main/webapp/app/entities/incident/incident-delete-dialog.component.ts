import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';

@Component({
    selector: 'jhi-incident-delete-dialog',
    templateUrl: './incident-delete-dialog.component.html'
})
export class IncidentDeleteDialogComponent {
    incident: IIncident;

    constructor(protected incidentService: IncidentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incidentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'incidentListModification',
                content: 'Deleted an incident'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-incident-delete-popup',
    template: ''
})
export class IncidentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incident }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncidentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.incident = incident;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/incident', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/incident', { outlets: { popup: null } }]);
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

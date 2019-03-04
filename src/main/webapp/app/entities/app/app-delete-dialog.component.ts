import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApp } from 'app/shared/model/app.model';
import { AppService } from './app.service';

@Component({
    selector: 'jhi-app-delete-dialog',
    templateUrl: './app-delete-dialog.component.html'
})
export class AppDeleteDialogComponent {
    app: IApp;

    constructor(protected appService: AppService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appListModification',
                content: 'Deleted an app'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-delete-popup',
    template: ''
})
export class AppDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ app }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AppDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.app = app;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/app', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/app', { outlets: { popup: null } }]);
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentUpdatesDeleteDialogComponent } from 'app/entities/incident-updates/incident-updates-delete-dialog.component';
import { IncidentUpdatesService } from 'app/entities/incident-updates/incident-updates.service';

describe('Component Tests', () => {
    describe('IncidentUpdates Management Delete Component', () => {
        let comp: IncidentUpdatesDeleteDialogComponent;
        let fixture: ComponentFixture<IncidentUpdatesDeleteDialogComponent>;
        let service: IncidentUpdatesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentUpdatesDeleteDialogComponent]
            })
                .overrideTemplate(IncidentUpdatesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncidentUpdatesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentUpdatesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

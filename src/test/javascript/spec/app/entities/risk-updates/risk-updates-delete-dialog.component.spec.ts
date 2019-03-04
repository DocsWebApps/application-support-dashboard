/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { RiskUpdatesDeleteDialogComponent } from 'app/entities/risk-updates/risk-updates-delete-dialog.component';
import { RiskUpdatesService } from 'app/entities/risk-updates/risk-updates.service';

describe('Component Tests', () => {
    describe('RiskUpdates Management Delete Component', () => {
        let comp: RiskUpdatesDeleteDialogComponent;
        let fixture: ComponentFixture<RiskUpdatesDeleteDialogComponent>;
        let service: RiskUpdatesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [RiskUpdatesDeleteDialogComponent]
            })
                .overrideTemplate(RiskUpdatesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RiskUpdatesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiskUpdatesService);
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { RiskDeleteDialogComponent } from 'app/entities/risk/risk-delete-dialog.component';
import { RiskService } from 'app/entities/risk/risk.service';

describe('Component Tests', () => {
    describe('Risk Management Delete Component', () => {
        let comp: RiskDeleteDialogComponent;
        let fixture: ComponentFixture<RiskDeleteDialogComponent>;
        let service: RiskService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [RiskDeleteDialogComponent]
            })
                .overrideTemplate(RiskDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RiskDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiskService);
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

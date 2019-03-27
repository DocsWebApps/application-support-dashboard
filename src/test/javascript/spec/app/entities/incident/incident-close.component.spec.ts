/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentService } from 'app/entities/incident/incident.service';
import { IncidentCloseDialogComponent } from 'app/entities/incident';

describe('Component Tests', () => {
    describe('Incident Management Close Component', () => {
        let comp: IncidentCloseDialogComponent;
        let fixture: ComponentFixture<IncidentCloseDialogComponent>;
        let service: IncidentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentCloseDialogComponent]
            })
                .overrideTemplate(IncidentCloseDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncidentCloseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmClose', () => {
            it('Should call close service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'close').and.returnValue(of({}));

                    // WHEN
                    comp.confirmClose(123);
                    tick();

                    // THEN
                    expect(service.close).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

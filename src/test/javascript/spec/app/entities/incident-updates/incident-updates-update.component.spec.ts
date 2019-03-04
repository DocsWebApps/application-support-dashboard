/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentUpdatesUpdateComponent } from 'app/entities/incident-updates/incident-updates-update.component';
import { IncidentUpdatesService } from 'app/entities/incident-updates/incident-updates.service';
import { IncidentUpdates } from 'app/shared/model/incident-updates.model';

describe('Component Tests', () => {
    describe('IncidentUpdates Management Update Component', () => {
        let comp: IncidentUpdatesUpdateComponent;
        let fixture: ComponentFixture<IncidentUpdatesUpdateComponent>;
        let service: IncidentUpdatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentUpdatesUpdateComponent]
            })
                .overrideTemplate(IncidentUpdatesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncidentUpdatesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentUpdatesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new IncidentUpdates(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incidentUpdates = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new IncidentUpdates();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incidentUpdates = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

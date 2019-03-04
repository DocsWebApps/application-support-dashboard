/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentUpdateComponent } from 'app/entities/incident/incident-update.component';
import { IncidentService } from 'app/entities/incident/incident.service';
import { Incident } from 'app/shared/model/incident.model';

describe('Component Tests', () => {
    describe('Incident Management Update Component', () => {
        let comp: IncidentUpdateComponent;
        let fixture: ComponentFixture<IncidentUpdateComponent>;
        let service: IncidentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentUpdateComponent]
            })
                .overrideTemplate(IncidentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncidentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Incident(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incident = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Incident();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incident = entity;
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

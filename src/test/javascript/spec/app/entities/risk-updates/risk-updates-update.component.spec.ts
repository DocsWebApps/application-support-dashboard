/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { RiskUpdatesUpdateComponent } from 'app/entities/risk-updates/risk-updates-update.component';
import { RiskUpdatesService } from 'app/entities/risk-updates/risk-updates.service';
import { RiskUpdates } from 'app/shared/model/risk-updates.model';

describe('Component Tests', () => {
    describe('RiskUpdates Management Update Component', () => {
        let comp: RiskUpdatesUpdateComponent;
        let fixture: ComponentFixture<RiskUpdatesUpdateComponent>;
        let service: RiskUpdatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [RiskUpdatesUpdateComponent]
            })
                .overrideTemplate(RiskUpdatesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RiskUpdatesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiskUpdatesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RiskUpdates(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.riskUpdates = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RiskUpdates();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.riskUpdates = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { ProblemUpdatesUpdateComponent } from 'app/entities/problem-updates/problem-updates-update.component';
import { ProblemUpdatesService } from 'app/entities/problem-updates/problem-updates.service';
import { ProblemUpdates } from 'app/shared/model/problem-updates.model';

describe('Component Tests', () => {
    describe('ProblemUpdates Management Update Component', () => {
        let comp: ProblemUpdatesUpdateComponent;
        let fixture: ComponentFixture<ProblemUpdatesUpdateComponent>;
        let service: ProblemUpdatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [ProblemUpdatesUpdateComponent]
            })
                .overrideTemplate(ProblemUpdatesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProblemUpdatesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProblemUpdatesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProblemUpdates(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.problemUpdates = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProblemUpdates();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.problemUpdates = entity;
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

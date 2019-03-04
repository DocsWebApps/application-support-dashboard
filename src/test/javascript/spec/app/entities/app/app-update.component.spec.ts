/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { AppUpdateComponent } from 'app/entities/app/app-update.component';
import { AppService } from 'app/entities/app/app.service';
import { App } from 'app/shared/model/app.model';

describe('Component Tests', () => {
    describe('App Management Update Component', () => {
        let comp: AppUpdateComponent;
        let fixture: ComponentFixture<AppUpdateComponent>;
        let service: AppService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [AppUpdateComponent]
            })
                .overrideTemplate(AppUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AppUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new App(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.app = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new App();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.app = entity;
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

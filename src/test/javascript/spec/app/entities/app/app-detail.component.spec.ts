/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { AppDetailComponent } from 'app/entities/app/app-detail.component';
import { App } from 'app/shared/model/app.model';

describe('Component Tests', () => {
    describe('App Management Detail Component', () => {
        let comp: AppDetailComponent;
        let fixture: ComponentFixture<AppDetailComponent>;
        const route = ({ data: of({ app: new App(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [AppDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AppDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.app).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

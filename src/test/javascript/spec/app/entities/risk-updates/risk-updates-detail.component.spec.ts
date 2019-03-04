/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { RiskUpdatesDetailComponent } from 'app/entities/risk-updates/risk-updates-detail.component';
import { RiskUpdates } from 'app/shared/model/risk-updates.model';

describe('Component Tests', () => {
    describe('RiskUpdates Management Detail Component', () => {
        let comp: RiskUpdatesDetailComponent;
        let fixture: ComponentFixture<RiskUpdatesDetailComponent>;
        const route = ({ data: of({ riskUpdates: new RiskUpdates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [RiskUpdatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RiskUpdatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RiskUpdatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.riskUpdates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

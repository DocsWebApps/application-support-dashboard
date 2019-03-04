/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { RiskDetailComponent } from 'app/entities/risk/risk-detail.component';
import { Risk } from 'app/shared/model/risk.model';

describe('Component Tests', () => {
    describe('Risk Management Detail Component', () => {
        let comp: RiskDetailComponent;
        let fixture: ComponentFixture<RiskDetailComponent>;
        const route = ({ data: of({ risk: new Risk(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [RiskDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RiskDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RiskDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.risk).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

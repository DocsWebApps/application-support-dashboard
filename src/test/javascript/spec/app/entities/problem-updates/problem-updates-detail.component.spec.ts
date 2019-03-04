/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { ProblemUpdatesDetailComponent } from 'app/entities/problem-updates/problem-updates-detail.component';
import { ProblemUpdates } from 'app/shared/model/problem-updates.model';

describe('Component Tests', () => {
    describe('ProblemUpdates Management Detail Component', () => {
        let comp: ProblemUpdatesDetailComponent;
        let fixture: ComponentFixture<ProblemUpdatesDetailComponent>;
        const route = ({ data: of({ problemUpdates: new ProblemUpdates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [ProblemUpdatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProblemUpdatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProblemUpdatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.problemUpdates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentUpdatesDetailComponent } from 'app/entities/incident-updates/incident-updates-detail.component';
import { IncidentUpdates } from 'app/shared/model/incident-updates.model';

describe('Component Tests', () => {
    describe('IncidentUpdates Management Detail Component', () => {
        let comp: IncidentUpdatesDetailComponent;
        let fixture: ComponentFixture<IncidentUpdatesDetailComponent>;
        const route = ({ data: of({ incidentUpdates: new IncidentUpdates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentUpdatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IncidentUpdatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncidentUpdatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.incidentUpdates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

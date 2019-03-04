/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentDetailComponent } from 'app/entities/incident/incident-detail.component';
import { Incident } from 'app/shared/model/incident.model';

describe('Component Tests', () => {
    describe('Incident Management Detail Component', () => {
        let comp: IncidentDetailComponent;
        let fixture: ComponentFixture<IncidentDetailComponent>;
        const route = ({ data: of({ incident: new Incident(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IncidentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncidentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.incident).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

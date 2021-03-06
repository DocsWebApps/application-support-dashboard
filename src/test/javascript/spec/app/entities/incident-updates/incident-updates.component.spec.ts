/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, Route } from '@angular/router';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentUpdatesComponent } from 'app/entities/incident-updates/incident-updates.component';
import { IncidentUpdatesService } from 'app/entities/incident-updates/incident-updates.service';
import { IncidentUpdates } from 'app/shared/model/incident-updates.model';
import { IncidentService } from 'app/entities/incident';
import { Incident } from 'app/shared/model/incident.model';
import { ProblemUpdatesService } from 'app/entities/problem-updates';

describe('Component Tests', () => {
    describe('IncidentUpdates Management Component', () => {
        let comp: IncidentUpdatesComponent;
        let fixture: ComponentFixture<IncidentUpdatesComponent>;
        let service: IncidentUpdatesService;
        let service1: IncidentService;
        // let service2: ProblemUpdatesService;
        // let incident: Incident;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentUpdatesComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            snapshot: {
                                params: { incidentID: 123 }
                            },
                            data: {
                                subscribe: (fn: (value: Data) => void) =>
                                    fn({
                                        pagingParams: {
                                            predicate: 'id',
                                            reverse: false,
                                            page: 0
                                        }
                                    })
                            }
                        }
                    }
                ]
            })
                .overrideTemplate(IncidentUpdatesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncidentUpdatesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentUpdatesService);
            service1 = fixture.debugElement.injector.get(IncidentService);
            // service2 = fixture.debugElement.injector.get(ProblemUpdatesService);
            // incident = new Incident(123);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IncidentUpdates(123)],
                        headers
                    })
                )
            );

            spyOn(service1, 'find').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Incident(345)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(service1.find).toHaveBeenCalled();
            expect(comp.incidentUpdates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
            expect(comp.incident[0]).toEqual(jasmine.objectContaining({ id: 345 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IncidentUpdates(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.incidentUpdates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IncidentUpdates(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);
            comp.reset();

            // THEN
            expect(comp.page).toEqual(0);
            expect(service.query).toHaveBeenCalledTimes(2);
            expect(comp.incidentUpdates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        // it('should give return routes', () => {
        //   // WHEN
        //   comp.setProblemUpdatesReturnPage(1);
        //
        //   // THEN
        //   expect(service2.returnRoute).toEqual('/incident-updates/' + incident.id);
        // });

        it('should calculate the sort attribute for an id', () => {
            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['id,asc']);
        });

        it('should calculate the sort attribute for a non-id attribute', () => {
            // GIVEN
            comp.predicate = 'name';

            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['name,asc', 'id']);
        });
    });
});

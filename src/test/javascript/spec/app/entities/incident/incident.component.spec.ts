/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { IncidentComponent } from 'app/entities/incident/incident.component';
import { IncidentService } from 'app/entities/incident/incident.service';
import { Incident } from 'app/shared/model/incident.model';
import { IncidentUpdatesService } from 'app/entities/incident-updates';
import { ProblemUpdatesService } from 'app/entities/problem-updates';

describe('Component Tests', () => {
    describe('Incident Management Component', () => {
        let comp: IncidentComponent;
        let fixture: ComponentFixture<IncidentComponent>;
        let service: IncidentService;
        let service1: IncidentUpdatesService;
        let service2: ProblemUpdatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [IncidentComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
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
                .overrideTemplate(IncidentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncidentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidentService);
            service1 = fixture.debugElement.injector.get(IncidentUpdatesService);
            service2 = fixture.debugElement.injector.get(ProblemUpdatesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Incident(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.incidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should clear the filter values', () => {
            // GIVEN
            comp.selectedSeverity = 'P1';
            comp.selectedStatus = 'OPEN';

            // WHEN
            comp.onClearFilter();

            // THEN
            expect(comp.selectedStatus).toEqual('ALL');
            expect(comp.selectedSeverity).toEqual('ALL');
        });

        it('should give return routes', () => {
            // WHEN
            comp.setIncidentUpdatesReturnPage(1);
            comp.setProblemUpdatesReturnPage(1);

            // THEN
            expect(service1.returnRoute).toEqual('/incident');
            expect(service2.returnRoute).toEqual('/incident');
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Incident(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.incidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Incident(123)],
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
            expect(comp.incidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

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

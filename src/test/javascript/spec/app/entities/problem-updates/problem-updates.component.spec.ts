/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { ProblemUpdatesComponent } from 'app/entities/problem-updates/problem-updates.component';
import { ProblemUpdatesService } from 'app/entities/problem-updates/problem-updates.service';
import { ProblemUpdates } from 'app/shared/model/problem-updates.model';

describe('Component Tests', () => {
    describe('ProblemUpdates Management Component', () => {
        let comp: ProblemUpdatesComponent;
        let fixture: ComponentFixture<ProblemUpdatesComponent>;
        let service: ProblemUpdatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [ProblemUpdatesComponent],
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
                .overrideTemplate(ProblemUpdatesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProblemUpdatesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProblemUpdatesService);
        });

        // it('Should call load all on init', () => {
        //     // GIVEN
        //     const headers = new HttpHeaders().append('link', 'link;link');
        //     spyOn(service, 'query').and.returnValue(
        //         of(
        //             new HttpResponse({
        //                 body: [new ProblemUpdates(123)],
        //                 headers
        //             })
        //         )
        //     );
        //
        //     // WHEN
        //     comp.ngOnInit();
        //
        //     // THEN
        //     expect(service.query).toHaveBeenCalled();
        //     expect(comp.problemUpdates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        // });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProblemUpdates(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.problemUpdates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProblemUpdates(123)],
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
            expect(comp.problemUpdates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

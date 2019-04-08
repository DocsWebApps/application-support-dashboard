/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Data } from '@angular/router';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { StatusComponent } from 'app/home/status/status.component';
import { StatusService } from 'app/home/status/status.service';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

describe('Component Tests', () => {
    describe('Status Component', () => {
        let comp: StatusComponent;
        let fixture: ComponentFixture<StatusComponent>;
        let service: StatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [StatusComponent],
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
                .overrideTemplate(StatusComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatusService);
        });

        it('Should call getAppStatus all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'getAppStatus').and.returnValue(
                of(
                    new HttpResponse({
                        body: [{ test: 'TEST' }],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.getAppStatus).toHaveBeenCalled();
        });
    });
});

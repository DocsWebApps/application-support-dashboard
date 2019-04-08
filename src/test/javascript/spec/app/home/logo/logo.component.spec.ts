/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Data } from '@angular/router';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { LogoComponent } from 'app/home/logo/logo.component';
import { NameService } from 'app/shared/services/name.service';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Name } from 'app/shared/model/name.model';

describe('Component Tests', () => {
    describe('Logo Component', () => {
        let comp: LogoComponent;
        let fixture: ComponentFixture<LogoComponent>;
        let service: NameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [LogoComponent],
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
                .overrideTemplate(LogoComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LogoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NameService);
        });

        it('Should call getName() on init', () => {
            // GIVEN
            let name: Name;
            name = new Name();
            name.name = 'Server';
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'getName').and.returnValue(
                of(
                    new HttpResponse({
                        body: [this.name],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.getName).toHaveBeenCalled();
        });
    });
});

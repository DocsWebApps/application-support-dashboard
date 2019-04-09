/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Data } from '@angular/router';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { NameService } from 'app/shared/services/name.service';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Name } from 'app/shared/model/name.model';
import { FooterComponent } from 'app/layouts';

describe('Component Tests', () => {
    describe('Footer Component', () => {
        let comp: FooterComponent;
        let fixture: ComponentFixture<FooterComponent>;
        let service: NameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [FooterComponent],
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
                .overrideTemplate(FooterComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FooterComponent);
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

        it('Should return the current year', () => {
            // GIVEN
            let year1: String;
            let year2: String;
            year1 = new Date().getFullYear().toString();

            // WHEN
            year2 = comp.getYear().toString();

            // THEN
            expect(year1).toEqual(year2);
        });
    });
});

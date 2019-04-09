/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { NameService } from 'app/shared/services/name.service';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Name } from 'app/shared/model/name.model';
import { NavbarComponent } from 'app/layouts';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

describe('Component Tests', () => {
    describe('Navbar Component', () => {
        let comp: NavbarComponent;
        let fixture: ComponentFixture<NavbarComponent>;
        let service: NameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [NavbarComponent],
                providers: [LocalStorageService, SessionStorageService]
            })
                .overrideTemplate(NavbarComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NavbarComponent);
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
            comp.getImageUrl();
            comp.isAuthenticated();
            comp.collapseNavbar();
            comp.logout();
            comp.toggleNavbar();

            // THEN
            expect(service.getName).toHaveBeenCalled();
        });
    });
});
